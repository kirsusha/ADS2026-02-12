package by.it.group551001.kirinskaya.lesson12;

import java.util.*;

public class MySplayMap implements NavigableMap<Integer, String> {

    private static class Node {
        Integer key;
        String value;
        Node left, right, parent;

        Node(Integer key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    private int size;

    private int compare(Integer a, Integer b) {
        return a.compareTo(b);
    }


    private void rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != null) y.right.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.right) x.parent.right = y;
        else x.parent.left = y;
        y.right = x;
        x.parent = y;
    }

    private void splay(Node x) {
        while (x.parent != null) {
            if (x.parent.parent == null) {
                if (x == x.parent.left) rotateRight(x.parent);
                else rotateLeft(x.parent);
            } else if (x == x.parent.left && x.parent == x.parent.parent.left) {
                rotateRight(x.parent.parent);
                rotateRight(x.parent);
            } else if (x == x.parent.right && x.parent == x.parent.parent.right) {
                rotateLeft(x.parent.parent);
                rotateLeft(x.parent);
            } else if (x == x.parent.right && x.parent == x.parent.parent.left) {
                rotateLeft(x.parent);
                rotateRight(x.parent);
            } else {
                rotateRight(x.parent);
                rotateLeft(x.parent);
            }
        }
    }

    private Node search(Integer key) {
        Node node = root;
        Node last = null;
        while (node != null) {
            last = node;
            int cmp = compare(key, node.key);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else {
                splay(node);
                return node;
            }
        }
        if (last != null) splay(last);
        return null;
    }

    private void inorder(Node node, StringBuilder sb) {
        if (node == null) return;
        inorder(node.left, sb);
        if (sb.length() > 1) sb.append(", ");
        sb.append(node.key).append("=").append(node.value);
        inorder(node.right, sb);
    }

    // ---------- NavigableMap / SortedMap / Map ----------
    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public boolean containsKey(Object key) {
        return search((Integer) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return containsValue(root, value);
    }

    private boolean containsValue(Node node, Object value) {
        if (node == null) return false;
        if (Objects.equals(node.value, value)) return true;
        return containsValue(node.left, value) || containsValue(node.right, value);
    }

    @Override
    public String get(Object key) {
        Node n = search((Integer) key);
        return n == null ? null : n.value;
    }

    @Override
    public String put(Integer key, String value) {
        if (root == null) {
            root = new Node(key, value);
            size = 1;
            return null;
        }
        Node node = root;
        Node parent = null;
        while (node != null) {
            parent = node;
            int cmp = compare(key, node.key);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else {
                String old = node.value;
                node.value = value;
                splay(node);
                return old;
            }
        }
        Node newNode = new Node(key, value);
        newNode.parent = parent;
        if (compare(key, parent.key) < 0) parent.left = newNode;
        else parent.right = newNode;
        size++;
        splay(newNode);
        return null;
    }

    @Override
    public String remove(Object key) {
        Node node = search((Integer) key);
        if (node == null) return null;
        String old = node.value;
        splay(node);
        if (node.left == null) {
            root = node.right;
            if (root != null) root.parent = null;
        } else if (node.right == null) {
            root = node.left;
            root.parent = null;
        } else {
            Node leftTree = node.left;
            leftTree.parent = null;
            Node rightTree = node.right;
            rightTree.parent = null;
            Node maxLeft = leftTree;
            while (maxLeft.right != null) maxLeft = maxLeft.right;
            splay(maxLeft);
            maxLeft.right = rightTree;
            rightTree.parent = maxLeft;
            root = maxLeft;
        }
        size--;
        return old;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        for (Map.Entry<? extends Integer, ? extends String> e : m.entrySet()) put(e.getKey(), e.getValue());
    }

    @Override
    public void clear() { root = null; size = 0; }

    @Override
    public Comparator<? super Integer> comparator() { return null; }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        MySplayMap result = new MySplayMap();
        headMap(root, toKey, result);
        return result;
    }

    private void headMap(Node node, Integer toKey, MySplayMap result) {
        if (node == null) return;
        if (compare(node.key, toKey) < 0) {
            headMap(node.left, toKey, result);
            result.put(node.key, node.value);
            headMap(node.right, toKey, result);
        } else {
            headMap(node.left, toKey, result);
        }
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        MySplayMap result = new MySplayMap();
        tailMap(root, fromKey, result);
        return result;
    }

    private void tailMap(Node node, Integer fromKey, MySplayMap result) {
        if (node == null) return;
        if (compare(node.key, fromKey) >= 0) {
            tailMap(node.left, fromKey, result);
            result.put(node.key, node.value);
            tailMap(node.right, fromKey, result);
        } else {
            tailMap(node.right, fromKey, result);
        }
    }

    @Override
    public Integer firstKey() {
        if (root == null) throw new NoSuchElementException();
        Node n = root;
        while (n.left != null) n = n.left;
        splay(n);
        return n.key;
    }

    @Override
    public Integer lastKey() {
        if (root == null) throw new NoSuchElementException();
        Node n = root;
        while (n.right != null) n = n.right;
        splay(n);
        return n.key;
    }

    @Override
    public Map.Entry<Integer, String> lowerEntry(Integer key) { throw new UnsupportedOperationException(); }

    @Override
    public Integer lowerKey(Integer key) {
        Node n = root;
        Integer result = null;
        while (n != null) {
            int cmp = compare(key, n.key);
            if (cmp <= 0) n = n.left;
            else {
                result = n.key;
                n = n.right;
            }
        }
        return result;
    }

    @Override
    public Map.Entry<Integer, String> floorEntry(Integer key) { throw new UnsupportedOperationException(); }

    @Override
    public Integer floorKey(Integer key) {
        Node n = root;
        Integer result = null;
        while (n != null) {
            int cmp = compare(key, n.key);
            if (cmp < 0) n = n.left;
            else {
                result = n.key;
                if (cmp == 0) break;
                n = n.right;
            }
        }
        return result;
    }

    @Override
    public Map.Entry<Integer, String> ceilingEntry(Integer key) { throw new UnsupportedOperationException(); }

    @Override
    public Integer ceilingKey(Integer key) {
        Node n = root;
        Integer result = null;
        while (n != null) {
            int cmp = compare(key, n.key);
            if (cmp > 0) n = n.right;
            else {
                result = n.key;
                if (cmp == 0) break;
                n = n.left;
            }
        }
        return result;
    }

    @Override
    public Map.Entry<Integer, String> higherEntry(Integer key) { throw new UnsupportedOperationException(); }

    @Override
    public Integer higherKey(Integer key) {
        Node n = root;
        Integer result = null;
        while (n != null) {
            int cmp = compare(key, n.key);
            if (cmp >= 0) n = n.right;
            else {
                result = n.key;
                n = n.left;
            }
        }
        return result;
    }

    @Override
    public Map.Entry<Integer, String> firstEntry() { throw new UnsupportedOperationException(); }

    @Override
    public Map.Entry<Integer, String> lastEntry() { throw new UnsupportedOperationException(); }

    @Override
    public Map.Entry<Integer, String> pollFirstEntry() { throw new UnsupportedOperationException(); }

    @Override
    public Map.Entry<Integer, String> pollLastEntry() { throw new UnsupportedOperationException(); }

    @Override
    public NavigableMap<Integer, String> descendingMap() { throw new UnsupportedOperationException(); }

    @Override
    public NavigableSet<Integer> navigableKeySet() { throw new UnsupportedOperationException(); }

    @Override
    public NavigableSet<Integer> descendingKeySet() { throw new UnsupportedOperationException(); }

    @Override
    public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) {
        throw new UnsupportedOperationException();
    }


    @Override
    public Set<Integer> keySet() { throw new UnsupportedOperationException(); }

    @Override
    public Collection<String> values() { throw new UnsupportedOperationException(); }

    @Override
    public Set<Map.Entry<Integer, String>> entrySet() { throw new UnsupportedOperationException(); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        inorder(root, sb);
        sb.append("}");
        return sb.toString();
    }
}