import java.lang.reflect.Array;
import java.util.*;
public class BinaryTree implements Set<Integer> {

    /*
    Хранит целые числа в виде бинарного дерева поиска.
    Дерево не может содержать одно и то же число более одного раза.
    Методы: добавление числа, add()
    удаление числа, remove()
    поиск числа в дереве, contains()
    определение соседей числа в дереве (предок, левый потомок, правый потомок).
     */
    private Node root;
    private int size = 0;
    public BinaryTree(int... integers) {
        Arrays.sort(integers);
        root = buildBST(integers, 0, integers.length - 1, null);
        size = integers.length;
    }

    private Node buildBST(int[] num, int start, int end, Node parent) {
        if (start > end) {
            return null;
        }
        int mid = start + (end - start) / 2;
        Node root = new Node(num[mid], parent);
        Node left = buildBST(num, start, mid - 1, root);
        Node right = buildBST(num, mid + 1, end, root);
        root.left = left;
        root.right = right;
        return root;
    }

    public Node getRoot() {
        return root;
    }

    // через этот метод можно достать нод, а у него уже найти соседей через геттеры parent, left, right
    public Node searchForNode(int i) {
        Node current = root;
        while (current != null) {
            if (i > current.data) current = current.right;
            else if (i < current.data) current = current.left;
            else return current;
        }
        return null;
    }

    @Override
    public boolean add(Integer i) {
        if (root == null) {
            root = new Node(i, null);
            size++;
            return true;
        }
        Node current = root;
        Node previous = null;
        while (true) {
            if (i > current.data) {
                previous = current;
                current = current.right;
                if (current == null) {
                    previous.right = new Node(i, previous);
                    size++;
                    return true;
                }
            } else if (i < current.data) {
                previous = current;
                current = current.left;
                if (current == null) {
                    previous.left = new Node(i, previous);
                    size++;
                    return true;
                }
            } else return false;
        }
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Integer)) return false;
        return searchForNode((int) o) != null;
    }

    @Override
    public boolean remove(Object object) {
        if (!(object instanceof Integer)) return false;
        int i = (int) object;
        Node node = searchForNode(i);
        if (node != null) {
            deleteNode(node);
            return true;
        }
        return false;
    }

    private void deleteNode(Node p) {
        size--;

        // If strictly internal, copy successor's element to p and then make p
        // point to successor.
        if (p.left != null && p.right != null) {
            Node s = successor(p);
            p.data = s.data;
            p = s;
        } // p has 2 children

        // Start fixup at replacement node, if it exists.
        Node replacement = (p.left != null ? p.left : p.right);

        if (replacement != null) {
            // Link replacement to parent

            replacement.parent = p.parent;
            if (p.parent == null)
                root = replacement;
            else if (p == p.parent.left)
                p.parent.left  = replacement;
            else
                p.parent.right = replacement;

            // Null out links so they are OK to use by fixAfterDeletion.
            p.left = p.right = p.parent = null;
        } else if (p.parent == null) { // return if we are the only node.
            root = null;
        } else { //  No children. Use self as phantom replacement and unlink.
            if (p == p.parent.left)
                p.parent.left = null;
            else if (p == p.parent.right)
                p.parent.right = null;
            p.parent = null;
        }
    }

    // ищет нод, с самым маленьким значением больше нашего
    static Node successor(Node t) {
        if (t == null)
            return null;
        else if (t.right != null) {
            Node p = t.right;
            while (p.left != null)
                p = p.left;
            return p;
        } else {
            Node p = t.parent;
            Node ch = t;
            while (p != null && ch == p.right) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
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
    public Iterator<Integer> iterator() {
        return new BinaryTreeIterator(root);
    }

    @Override
    public Object[] toArray() {
        return Arrays.stream(toIntArray()).boxed().toArray();
    }

    public int[] toIntArray() {
        int[] result = new int[size];
        int i = 0;
        for (int element: this) {
            result[i++] = element;
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        int size = size();
        if (array.length < size) {
            array = (T[]) Array.newInstance(array.getClass().getComponentType(), size);
        } else if (array.length > size) {
            array[size] = null;
        }

        int i = 0;
        for (Integer e: this) {

            array[i] = (T) e;
            i++;
        }
        return array;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o: c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    public boolean containsAll(Integer... integers) {
        return containsAll(Arrays.asList(integers));
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        boolean flag = true;
        for (int i: c) {
            boolean b = add(i);
            if (!b) flag = false;
        }
        return flag;
    }

    public boolean addAll(Integer... integers) {
        return addAll(Arrays.asList(integers));
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean flag = true;
        for (int i: this) {
            if (!c.contains(i)) {
                boolean b = remove(i);
                if (!b) flag = false;
            }
        }
        return flag;
    }

    public boolean retainAll(Integer... integers) {
        return retainAll(Arrays.asList(integers));
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean flag = true;
        for (Object o: c) {
            boolean b = remove(o);
            if (!b) flag = false;
        }
        return flag;
    }

    public boolean removeAll(Integer... integers) {
        return removeAll(Arrays.asList(integers));
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) return false;
        BinaryTree other = (BinaryTree) o;
        if (this.size() != other.size()) return false;
        int[] arrayThis = this.toIntArray();
        int[] arrayOther = other.toIntArray();
        return Arrays.equals(arrayThis, arrayOther);
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (Integer i: this) {
            result += i.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i: this) {
            sb.append(i);
            sb.append(" ");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private static class Node {

        private int data;
        private Node parent;
        private Node left;
        private Node right;

        Node(int data, Node parent) {
            this.data = data;
            this.parent = parent;
        }

        public Node getParent() {
            return parent;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        void delete() {
            if (isRight()) parent.right = null;
            else parent.left = null;
            this.parent = null;
        }

        boolean isRight() {
            return parent != null &&
                    parent.right != null &&
                    parent.right.data == data;
        }

        boolean isLeft() {
            return !isRight();
        }
    }


    public static class BinaryTreeIterator implements Iterator<Integer> {
        private Node next;
        private Node lastReturned;

        public BinaryTreeIterator(Node root) {
            next = root;
            if(next == null)
                return;

            while (next.left != null)
                next = next.left;
        }

        public boolean hasNext(){
            return next != null;
        }

        @Override
        public Integer next() {
            return nextNode().data;
        }

        public Node nextNode(){
            if(!hasNext()) throw new NoSuchElementException();
            lastReturned = next;
            next = successor(next);
            return lastReturned;
        }

        public Node lastReturned() {
            return lastReturned;
        }
    }
}
