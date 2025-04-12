import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.Iterable;

public class DoublyLinkedHashSet<E> implements Iterable<E> {
    private static final float LOAD_FACTOR = 0.75f;
    public Entry<E>[] table;
    public Entry<E> head, tail;
    public int size;

    @SuppressWarnings("unchecked")
    public DoublyLinkedHashSet() {
        this.table = new Entry[256]; // Initial capacity
        this.size = 0;
    }

    public static class Entry<E> {
        E element;
        Entry<E> next, prev, after, before; // after and before for maintaing doubly linked list
        int hash; // next and prev for resolving hash collision

        Entry(E element, int hash, Entry<E> next) {
            this.element = element;
            this.hash = hash;
            this.next = next;
        }
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public boolean contains(E element) {
        return getEntry(element) != null;
    }

    public boolean add(E element) {
        if (element == null) throw new NullPointerException("Element cannot be null");
        if ((float) size >= table.length * LOAD_FACTOR) resize();
        Entry<E> entry = getEntry(element);
        if (entry != null) return false;
        int hashVal = hash(element);
        entry = new Entry<>(element, hashVal, table[hashVal % table.length]);
        table[hashVal % table.length] = entry;
        linkAtEnd(entry);
        size++;
        return true;
    }

    public boolean remove(E element) {
        Entry<E> entry = getEntry(element);
        if (entry == null) return false;
        removeEntry(entry);
        size--;
        return true;
    }

    public void clear() {
        for (int i = 0; i < table.length; i++) table[i] = null;

        head = null;
        tail = null;
        size = 0;
    }

    public Entry<E> getEntry(E element) {
        int hash = hash(element);
        for (Entry<E> e = table[hash % table.length]; e != null; e = e.next) {
            if (e.hash == hash && e.element.equals(element)) return e;
        }
        return null;
    }

    private int hash(E element) {
        return element.hashCode() & (table.length - 1);
    }

    private void linkAtEnd(Entry<E> entry) {
        if (head == null) head = tail = entry;
        else {
            tail.after = entry;
            entry.before = tail;
            tail = entry;
        }
    }

    private void removeEntry(Entry<E> entry) {
        int index = entry.hash % table.length;
        Entry<E> e = table[index];
        Entry<E> prev = null;
        while (e != null) {
            if (e == entry) {
                if (prev == null) table[index] = e.next;
                else prev.next = e.next;

                if (e.next != null) e.next.prev = prev;
                if (entry == head) head = entry.after;
                if (entry == tail) tail = entry.before;
                if (entry.before != null) entry.before.after = entry.after;
                if (entry.after != null) entry.after.before = entry.before;
                return;
            }
            prev = e;
            e = e.next;
        }
    }

    private void resize() {
        @SuppressWarnings("unchecked")
        Entry<E>[] newTable = new Entry[table.length * 2];
        for (Entry<E> entry : table) {
            while (entry != null) {
                Entry<E> next = entry.next;
                int newIndex = hash(entry.element) % newTable.length;
                entry.next = newTable[newIndex];
                newTable[newIndex] = entry;
                entry = next;
            }
        }
        this.table = newTable;
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Entry<E> current = head;
            @Override
            public boolean hasNext() {
                return current != null;
            }
            @Override
            public E next() {
                if (current == null) throw new NoSuchElementException();
                E element = current.element;
                current = current.after;
                return element;
            }
        };
    }
}