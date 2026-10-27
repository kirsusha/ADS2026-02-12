package by.it.group551001.kirinskaya.lesson12;

import java.util.Map;
import java.util.Objects;

public class MyAvlMap implements Map<Integer, String> {

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left, right;
        int height;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 1;
        }
    }

    private Node<Integer, String> root;
    private int size;


    private int height(Node<Integer, String> n) {
        return n == null ? 0 : n.height;
    }

    private void updateHeight(Node<Integer, String> n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    private int balanceFactor(Node<Integer, String> n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    private Node<Integer, String> rotateRight(Node<Integer, String> y) {
        Node<Integer, String> x = y.left;
        Node<Integer, String> T2 = x.right;
        x.right = y;
        y.left = T2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node<Integer, String> rotateLeft(Node<Integer, String> x) {
        Node<Integer, String> y = x.right;
        Node<Integer, String> T2 = y.left;
        y.left = x;
        x.right = T2;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    private Node<Integer, String> balance(Node<Integer, String> n) {
        if (n == null) return null;
        updateHeight(n);
        int bf = balanceFactor(n);
        if (bf > 1) {
            if (balanceFactor(n.left) < 0) {
                n.left = rotateLeft(n.left);
            }
            return rotateRight(n);
        }
        if (bf < -1) {
            if (balanceFactor(n.right) > 0) {
                n.right = rotateRight(n.right);
            }
            return rotateLeft(n);
        }
        return n;
    }

    @SuppressWarnings("unchecked")
    private int compare(Integer a, Integer b) {
        return ((Comparable<Integer>) a).compareTo(b);
    }

    private Node<Integer, String> insert(Node<Integer, String> node, Integer key, String value) {
        if (node == null) {
            size++;
            return new Node<>(key, value);
        }
        int cmp = compare(key, node.key);
        if (cmp < 0) {
            node.left = insert(node.left, key, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, key, value);
        } else {
            node.value = value;
            return node;
        }
        return balance(node);
    }

    private Node<Integer, String> minValueNode(Node<Integer, String> node) {
        Node<Integer, String> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node<Integer, String> delete(Node<Integer, String> node, Integer key) {
        if (node == null) return null;
        int cmp = compare(key, node.key);
        if (cmp < 0) {
            node.left = delete(node.left, key);
        } else if (cmp > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                Node<Integer, String> temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    node = null;
                } else {
                    node = temp;
                }
                size--;
            } else {
                Node<Integer, String> temp = minValueNode(node.right);
                node.key = temp.key;
                node.value = temp.value;
                node.right = delete(node.right, temp.key);
            }
        }
        if (node == null) return null;
        return balance(node);
    }

    private Node<Integer, String> search(Node<Integer, String> node, Integer key) {
        if (node == null) return null;
        int cmp = compare(key, node.key);
        if (cmp < 0) return search(node.left, key);
        if (cmp > 0) return search(node.right, key);
        return node;
    }

    private void inorder(Node<Integer, String> node, StringBuilder sb) {
        if (node == null) return;
        inorder(node.left, sb);
        if (sb.length() > 1) sb.append(", ");
        sb.append(node.key).append("=").append(node.value);
        inorder(node.right, sb);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return search(root, (Integer) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return containsValue(root, value);
    }

    private boolean containsValue(Node<Integer, String> node, Object value) {
        if (node == null) return false;
        if (Objects.equals(node.value, value)) return true;
        return containsValue(node.left, value) || containsValue(node.right, value);
    }

    @Override
    public String get(Object key) {
        Node<Integer, String> n = search(root, (Integer) key);
        return n == null ? null : n.value;
    }

    @Override
    public String put(Integer key, String value) {
        Node<Integer, String> existing = search(root, key);
        String old = existing == null ? null : existing.value;
        root = insert(root, key, value);
        return old;
    }

    @Override
    public String remove(Object key) {
        Node<Integer, String> existing = search(root, (Integer) key);
        if (existing == null) return null;
        String old = existing.value;
        root = delete(root, (Integer) key);
        return old;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        for (Map.Entry<? extends Integer, ? extends String> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public java.util.Set<Integer> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public java.util.Collection<String> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public java.util.Set<Map.Entry<Integer, String>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        inorder(root, sb);
        sb.append("}");
        return sb.toString();
    }
}