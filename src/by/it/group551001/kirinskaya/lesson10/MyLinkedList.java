package by.it.group551001.kirinskaya.lesson10;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements Deque<E> {
    private static class Node<E> {
        E item;
        Node<E> prev;
        Node<E> next;
        Node(E item) { this.item = item; }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public MyLinkedList(){
        head = null;
        tail = null;
        size =0;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = head;
        while (current!=null) {
            sb.append(current.item);
            if (current.next!= null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
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
    public Iterator<E> descendingIterator() {
        return null;
    }

    public boolean add(E element) {
        addLast(element);
        return true;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public void addFirst(E element){
        if (element ==null) throw new NullPointerException();
        Node<E> newnode = new Node<>(element);
        if (head==null) {
            head = tail = newnode;
        } else {
            newnode.next = head;;
            head.prev = newnode;
            head= newnode;
        }
        size++;

    }

    @Override
    public void addLast(E element){
        if (element == null) throw new NullPointerException();
        Node<E> newnode = new Node<>(element);
        if (tail == null) {
            head= tail = newnode;
        } else {
            newnode.prev = tail;
            tail.next = newnode;
            tail = newnode;
        }
        size++;
    }

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }


    public E remove(int index){
        if (index <0 || index>=size) throw new IndexOutOfBoundsException();
        Node<E> current;
        if (index < size/2) {
            current = head;
            for (int i =0; i< index; i++) current= current.next;
        } else {
            current = tail;
            for (int i = size-1; i> index; i--) current= current.prev;
        }
        return unlink(current);
    }

    @Override
    public boolean remove(Object o) {
        Node<E> current = head;
        while(current!=null){
            if (current.item.equals(o)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    private E unlink(Node<E> node){
        E element = node.item;
        Node<E> prev = node.prev;
        Node<E> next = node.next;
        if (prev == null) head =next;
        else { prev.next = next; node.prev = null; }

        if (next==null) tail = prev;
        else {
            next.prev = prev;
            node.next = null;
        }
        node.item = null;
        size--;
        return element;
    }

    @Override
    public E element(){
        if (size == 0) throw new NoSuchElementException();
        return head.item;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public E getFirst(){
        if (size == 0) throw new NoSuchElementException();
        return head.item;
    }

    @Override
    public E getLast(){
        if (size == 0) throw new NoSuchElementException();
        return tail.item;
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public E poll(){
        return pollFirst();
    }

    @Override
    public E pollFirst(){
        if (size ==0 ) return null;
        return unlink(head);
    }

    @Override
    public E pollLast() {
        if (size==0) return null;
        return unlink(tail);
    }





}
