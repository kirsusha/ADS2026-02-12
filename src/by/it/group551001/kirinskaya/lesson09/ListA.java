package by.it.group551001.kirinskaya.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class ListA<E> implements List<E> {
    private Object[] elements;
    private int size;
    private static final int default_capacity = 10;

    public ListA(){
        this.elements = new Object[default_capacity];
        this.size =0;
    }

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i =0; i<size; i++) {
            sb.append(elements[i]);
            if ( i==size -1) {
                sb.append(']');
            } else {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        ensureCapacity(size+1);
        elements[size++] = e;
        return true;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        @SuppressWarnings("unchecked")

        E oldValue = (E) elements[index];
        int numMoved = size - index -1;
        if (numMoved>0){
            System.arraycopy(elements, index+1, elements, index, numMoved);
        }
        elements[--size] = null;
        return oldValue;

    }

    @Override
    public int size() {
        return size;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public void add(int index, E element) {
    checkIndexForAdd(index);
    ensureCapacity(size+1);
    int numMoved = size-index;
    if (numMoved>0) {
        System.arraycopy(elements, index, elements, index+1, numMoved);
    }
    elements[index] = element;
    size++;
    }

    @Override
    public boolean remove(Object o) {
        int index= indexOf(o);
        if (index>=0) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        @SuppressWarnings("unchecked")
        E oldValue = (E) elements[index];
        elements[index] = element;
        return oldValue;
    }


    @Override
    public boolean isEmpty() {
        return size==0;
    }


    @Override
    public void clear() {
for (int i =0; i< size; i++){
    elements[i] = null;
}
size =0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i =0; i<size; i++){
            if (o ==null ? elements[i] == null : o.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        @SuppressWarnings("unchecked")
        E value = (E) elements[index];
        return value;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >=0;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size-1; i>=0; i--) {
            if (o == null ? elements[i] == null : o.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        int num = a.length;
        ensureCapacity(size+num);
        System.arraycopy(a, 0, elements, size, num);
        size +=num;
        return num!=0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndexForAdd(index);
        Object[] a = c.toArray();
        int num = a.length;
        ensureCapacity(size+num);
        int numMoved = size - index;
        if (numMoved >0) {
            System.arraycopy(elements, index, elements, index + num, numMoved);
        }
        System.arraycopy(a, 0, elements, index, num);
        size+=num;
        return num!=0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (int i =0; i < size; i++ ){
            if (c.contains(elements[i])) {
                remove(i);
                i--;
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i =0; i < size; i++) {
            if (!c.contains(elements[i])) {
                remove(i);
                i--;
                modified = true;
            }
        }
        return modified;
    }


    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + ", toIndex: "+ toIndex);
        }
        ListA<E> sub = new ListA<>();
        for (int i = fromIndex; i < toIndex; i++) {
            sub.add((E) elements[i]);
        }
        return sub;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index <0 || index>size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        return new ListItr(index);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            T[] result = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
            System.arraycopy(elements, 0, result, 0, size);
            return result;
        }
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(elements, 0, result, 0, size);
        return result;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////        Эти методы имплементировать необязательно    ////////////
    ////////        но они будут нужны для корректной отладки    ////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private void ensureCapacity(int mincap){
        if (mincap > elements.length){
            int newcap = elements.length + (elements.length >> 1) +1;
            if (newcap < mincap) {
                newcap = mincap;
            }
            Object[] newElements = new Object[newcap];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }

    private void checkIndex(int index){
        if (index < 0 || index >=size) {
            throw new IndexOutOfBoundsException( "Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index <0 || index>size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
    private class Itr implements Iterator<E> {
        int cursor = 0;
        int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            lastRet = cursor;
            cursor++;
            return (E) elements[lastRet];
        }

        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            ListA.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
        }
    }

    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E previous() {
            if (cursor <= 0) {
                throw new NoSuchElementException();
            }
            cursor--;
            lastRet = cursor;
            return (E) elements[lastRet];
        }

        @Override
        public void set(E e) {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            ListA.this.set(lastRet, e);
        }

        @Override
        public void add(E e) {
            int i = cursor;
            ListA.this.add(i, e);
            cursor = i + 1;
            lastRet = -1;
        }
    }
}


