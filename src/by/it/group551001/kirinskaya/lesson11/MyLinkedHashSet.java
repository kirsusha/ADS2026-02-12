package by.it.group551001.kirinskaya.lesson11;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
public class MyLinkedHashSet<E> implements Set<E> {
    private static class Node<E>{
        E value;
        Node<E> next;
        Node<E> before;
        Node<E> after;
        Node(E value, Node<E> next){
            this.value = value;
            this.next= next;
        }
    }

    private Node<E>[] table;
    private Node<E> head;
    private Node<E> tail;
    private int size;
    private int threshold;

    @SuppressWarnings("unchecked")
    public MyLinkedHashSet(){
        table = (Node<E>[]) new Node[16];
        threshold = 12;
    }

    private int hash(Object o){
        int h =o ==null ? 0: o.hashCode();
        h ^= (h>>>20)^(h>>>12);
        h^=(h>>>7)^(h>>>4);
        return h;
    }

    private int index(Object o){
        return hash(o) & (table.length-1);
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<E>[] old = table;
        table = (Node<E>[]) new Node[old.length * 2];
        threshold = (int) (table.length * 0.75);
        Node<E> cur = head;
        while (cur != null) {
            Node<E> next = cur.after;
            int idx = index(cur.value);
            cur.next = table[idx];
            table[idx] = cur;
            cur = next;
        }

    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void clear(){
        for (int i = 0; i < table.length; i++) table[i] = null;
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean isEmpty(){
        return size ==0;
    }

    @Override
    public boolean add(E e){
        int idx = index(e);
        Node<E> cur = table[idx];
        while(cur !=null){
            if (cur.value == null? e==null : cur.value.equals(e)){
                return false;
            }
            cur = cur.next;
        }
        Node<E> node = new Node<>(e, table[idx]);
        table[idx] = node;
        if (tail ==null) {
            head = tail =node;
        } else {
            tail.after = node;
            node.before = tail;
            tail = node;
        }
        size++;
        if (size>threshold) {
            resize();
        }
        return true;
    }

    @Override
    public boolean remove(Object o){
        int idx = index(o);
        Node<E> cur = table[idx];
        Node<E> prev =null;
        while(cur!=null) {
            if (cur.value ==null? o ==null: cur.value.equals(o)){
                if (prev == null){
                    table[idx] = cur.next;
                } else {
                    prev.next = cur.next;
                }

                if (cur.before!=null) cur.before.after = cur.after;
                else head= cur.after;

                if (cur.after!=null) cur.after.before = cur.before;
                else tail = cur.before;
                size--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        return false;
    }

    @Override
    public boolean contains(Object o){
        int idx= index(o);
        Node<E> cur = table[idx];
        while(cur!=null) {
            if (cur.value==null? o==null: cur.value.equals(o)){
                return true;
            }
            cur = cur.next;
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
    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        Node<E> cur = head;
        boolean first = true;
        while(cur!=null){
            if (!first) sb.append(", ");
            sb.append(cur.value);
            first = false;
            cur = cur.after;
        }
        return sb.append("]").toString();
    }

    @Override
    public boolean containsAll(Collection<?> c){
        for (Object o: c){
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c){
        boolean changed= false;
        for (E e:c){
            if (add(e)) changed = true;
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c){
        boolean changed=false;
        for (Object o:c) {
            while(remove(o)) changed = true;
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c){
        boolean changed = false;
        Node<E> cur = head;
        while(cur!=null){
            Node<E> next = cur.after;
            if (!c.contains(cur.value)){
                remove(cur.value);
                changed = true;
            }
            cur = next;
        }
        return changed;
    }











}
