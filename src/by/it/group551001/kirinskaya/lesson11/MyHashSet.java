package by.it.group551001.kirinskaya.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {
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
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    private static class Node<E>{
        E value;
        Node<E> next;
        Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<E>[] table;
    private int size;
    private int threshold;
    @SuppressWarnings("unchecked")
    public MyHashSet(){
        table = (Node<E>[]) new Node[16];
        threshold = 12;
    }

    private int hash(Object o){
        int h = o ==null? 0: o.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        h ^= (h >>> 7) ^ (h >>>4);
        return h;
    }

    private int index(Object o){
        return hash(o) & (table.length-1);
    }

    @SuppressWarnings("unchecked")
    private void resize(){
        Node<E>[] old = table;
        table = (Node<E>[]) new Node[old.length*2];
        threshold = (int) (table.length *0.75);
        size = 0;
        for (Node<E> head:old){
            while(head!=null){
                add(head.value);
                head = head.next;
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear(){
        for (int i =0; i<table.length; i++){
            table[i] = null;
        }
        size=0;
    }

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public boolean add(E e){
        int idx = index(e);
        Node<E> cur = table[idx];
        while(cur!=null) {
            if (cur.value == null ? e ==null: cur.value.equals(e)){
                return false;
            }
            cur = cur.next;
        }
        table[idx] = new Node<>(e, table[idx]);
        size++;
        if (size > threshold) {
            resize();

        }
        return true;
    }

    @Override
    public boolean remove(Object o){
        int idx = index(o);
        Node<E> cur = table[idx];
        Node<E> prev = null;
        while(cur!=null){
            if (cur.value==null ? o ==null: cur.value.equals(o)) {
                if (prev == null){
                    table[idx] = cur.next;
                } else {
                    prev.next = cur.next;
                }
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
        int idx = index(o);
        Node<E> cur = table[idx];
        while(cur!=null){
            if (cur.value==null ? o==null: cur.value.equals(o)){
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (Node<E> head:table){
            while(head!=null) {
                if (!first) sb.append(", ");
                sb.append(head.value);
                first = false;
                head = head.next;
            }
        }
        return sb.append("]").toString();
    }




















}
