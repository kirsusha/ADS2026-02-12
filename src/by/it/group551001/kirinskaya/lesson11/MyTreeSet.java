package by.it.group551001.kirinskaya.lesson11;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
public class MyTreeSet<E> implements Set<E> {
    @SuppressWarnings("unchecked")
    private E[] elements = (E[]) new Object[16];
    private int size = 0;

    @SuppressWarnings("unchecked")
    private int compare(E a, E b){
        return ((Comparable<? super E>) a).compareTo(b);
    }

    private int binarysearch(E e){
        int low = 0;
        int high = size-1;
        while (low<=high) {
            int mid =(low+high) >>>1;
            int cmp = compare(elements[mid], e);
            if (cmp<0) low=mid+1;
            else if (cmp > 0) high = mid-1;
            else return mid;
        }
        return -(low+1);
    }

    @SuppressWarnings("unchecked")
    private void ensurecapacity(){
        if (size == elements.length) {
            E[] newarr = (E[]) new Object[elements.length*2];
            System.arraycopy(elements, 0, newarr, 0, size);
            elements= newarr;
        }
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void clear(){
        for (int i =0; i<size; i++) elements[i] = null;
        size =0;
    }

    @Override
    public boolean isEmpty(){
        return size==0;
    }

    @Override
    public boolean add(E e){
        int pos = binarysearch(e);
        if (pos>=0) return false;
        int ins= -pos-1;
        ensurecapacity();
        System.arraycopy(elements, ins, elements, ins+1, size-ins);
        elements[ins] = e;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o){
        @SuppressWarnings("unchecked")
        int pos = binarysearch((E) o);
        if (pos<0) return false;
        System.arraycopy(elements, pos+1, elements, pos, size-pos-1);
        elements[--size] = null;
        return true;
    }

    @Override
    public boolean contains(Object o){
        @SuppressWarnings("unchecked")
                int pos = binarysearch((E) o);
        return pos>=0;
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
    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        for (int i=0; i<size; i++){
            if (i>0) sb.append(", ");
            sb.append(elements[i]);
        }
        return sb.append("]").toString();
    }

    @Override
    public boolean containsAll(Collection<?> c){
        for (Object o:c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c){
        boolean changed = false;
        for (E e:c){
            if (add(e)) changed = true;
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c){
        boolean changed = false;
        for (Object o: c){
            while (remove(o)) changed =true;
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c){
        boolean changed= false;
        int i =0;
        while(i<size) {
            if (!c.contains(elements[i])){
                System.arraycopy(elements, i+1, elements, i, size -i-1);
                elements[--size] = null;
                changed = true;
            } else {
                i++;
            }
        }
        return changed;
    }







}
