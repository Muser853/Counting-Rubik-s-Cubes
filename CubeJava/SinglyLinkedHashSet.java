import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.Iterable;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class SinglyLinkedHashSet<E> implements Iterable<E> {
    private static final float LOAD_FACTOR = 0.75f;
    public Entry<E>[] table;
    public Entry<E> head, tail;
    public int size;

    @SuppressWarnings("unchecked")
    public SinglyLinkedHashSet() {
        this.table = new Entry[256]; // Initial capacity
        this.size = 0;
    }

    public static class Entry<E> {
        E element;
        Entry<E> next, after; // after for maintaining singly linked list
        int hash; // next for resolving hash collision

        Entry(E element, int hash, Entry<E> next) {
            this.element = element;
            this.hash = hash;
            this.next = next;
        }
    }

    public int size() {return size;}
    public boolean isEmpty() {return size == 0;}
    public boolean contains(E element) {return getEntry(element) != null;}

    public boolean add(E element) {
        if (contains(element)) return false;
        if ((float) size >= table.length * LOAD_FACTOR) resize();

        int hashVal = hash(element);
        Entry<E> entry = new Entry<>(element, hashVal, table[hashVal]);
        table[hashVal] = entry;
        linkAtEnd(entry);
        size++;
        return true;
    }

    public Entry<E> getEntry(E element) {
        int hash = hash(element);
        for (Entry<E> e = table[hash]; e != null; e = e.next) {
            if (e.hash == hash && e.element.equals(element)) return e;
        }
        return null;
    }
    private int hash(E element) {return element.hashCode() & (table.length - 1);}

    private void linkAtEnd(Entry<E> entry) {
        if (head == null) head = tail = entry;
        else tail.after = tail = entry;
    }

    private void resize() {
        @SuppressWarnings("unchecked")
        Entry<E>[] newTable = new Entry[table.length * 2];
    
        for (Entry<E> entry : table) {
            while (entry != null) {
                Entry<E> next = entry.next;
                entry.hash = entry.element.hashCode() & (newTable.length - 1);
                entry.next = newTable[entry.hash % newTable.length];
                newTable[entry.hash % newTable.length] = entry;
                entry = next;
            }
        }
        this.table = newTable;
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            public Entry<E> current = head;
            private final ReentrantLock lock = new ReentrantLock();
            @Override
            public boolean hasNext() {
                lock.lock();
                try {
                    return current != null;
                } finally {
                    lock.unlock();
                }
            }
            @Override
            public E next() {
                lock.lock();
                try {
                    if (current == null) throw new NoSuchElementException();
                    E element = current.element;
                    current = current.after;
                    return element;
                } finally {
                    lock.unlock();
                }
            }
        };
    }
}