package by.it.group551001.kirinskaya.lesson10;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.NoSuchElementException;

public class MyPriorityQueue<E> implements Queue<E> {
    private E[] heap;
    private int size;
    private static final int defaultcapacity = 10;

    @SuppressWarnings("unchecked")
    public MyPriorityQueue() {
        heap = (E[]) new Object[defaultcapacity];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private void ensurecapacity() {
        if (size == heap.length) {
            E[] newheap = (E[]) new Object[heap.length * 2];
            System.arraycopy(heap, 0, newheap,0, size);
            heap = newheap;
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(E a, E b) {
        return ((Comparable<? super E>) a).compareTo(b);
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(heap[index], heap[parent]) < 0) {
                swap(index, parent);
                index = parent;
            } else break;
        }
    }

    private void siftDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && compare(heap[left], heap[smallest]) < 0) smallest = left;
            if (right < size && compare(heap[right], heap[smallest]) < 0) smallest = right;
            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else break;
        }
    }

    private void swap(int i, int j) {
        E tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(heap[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) heap[i] = null;
        size = 0;
    }

    @Override
    public boolean add(E element) {
        return offer(element);
    }

    @Override
    public boolean offer(E element) {
        if (element == null) throw new NullPointerException();
        ensurecapacity();
        heap[size] = element;
        siftUp(size);
        size++;
        return true;
    }

    @Override
    public E remove() {
        if (size == 0) throw new NoSuchElementException();
        return poll();
    }

    @Override
    public E poll() {
        if (size == 0) return null;
        E result = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        if (size > 0) siftDown(0);
        return result;
    }

    @Override
    public E peek() {
        return size == 0 ? null : heap[0];
    }

    @Override
    public E element() {
        if (size == 0) throw new NoSuchElementException();
        return heap[0];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o){
        if (o == null) return false;
        for (int i =0; i< size; i++){
            if (o.equals(heap[i])) return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o:c){
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c){
        boolean modified = false;
        for(E e : c) {
            if (offer(e)) modified = true;
        }
        return modified;
    }


    @Override
    public boolean removeAll(Collection<?> c){
        if (c ==null) throw new NullPointerException();
        boolean modified = false;
        int newsize = 0;
        for (int i =0; i<size; i++){
            if (!c.contains(heap[i])) {
                heap[newsize++] = heap[i];
            } else {
                modified = true;
            }
        }
        for (int i = newsize; i<size; i++) heap[i] = null;
        size= newsize;
        for (int i =size/2 -1; i>=0; i--) siftDown(i);
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c==null) throw new NullPointerException();
        boolean modified = false;
        int newsize = 0;
        for (int i =0; i<size; i++){
            if (c.contains(heap[i])) {
                heap[newsize++] = heap[i];
            } else {
                modified= true;
            }
        }
        for (int i = newsize; i<size; i++) heap[i] = null;
        size= newsize;
        for (int i = size/2 -1; i>=0; i--) siftDown(i);
        return modified;
    }

    @Override
    public boolean remove(Object o){
        for (int i=0; i<size; i++){
            if (o.equals(heap[i])) {
                heap[i] = heap[size-1];
                heap[size-1] = null;
                size--;
                if (i<size) {
                    siftUp(i);
                    siftDown(i);
                }
                return true;
            }

        }
        return false;
    }






}
