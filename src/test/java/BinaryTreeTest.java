

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class BinaryTreeTest {

    @Test
    public void add() {
        BinaryTree result = new BinaryTree(1, 2, 3, 4, 5, 6);
        result.add(7);
        assertEquals("1 2 3 4 5 6 7", result.toString());
    }

    @Test
    public void remove() {
        BinaryTree binaryTree = new BinaryTree(1, 2, 3, 4, 5, 6);
        binaryTree.remove(5);
        assertEquals("1 2 3 4 6", binaryTree.toString());
        binaryTree.removeAll(1, 2, 3, 4, 6);
        assertEquals("", binaryTree.toString());
    }

    @Test
    public void contains() {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.add(2);
        assertFalse(binaryTree.contains(1));
        assertTrue(binaryTree.contains(2));
    }

    @Test
    public void constructor() {
        BinaryTree binaryTree = new BinaryTree(9, 1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals("1 2 3 4 5 6 7 8 9", binaryTree.toString());
    }


    @Test
    public void size() {
        BinaryTree binaryTree = new BinaryTree(1, 2, 3, 4, 5, 6, 7);
        assertEquals(7, binaryTree.size());
    }

    @Test
    public void isEmpty() {
        BinaryTree binaryTree = new BinaryTree(1, 2, 3, 4, 5, 6, 7);
        binaryTree.remove(1);
        binaryTree.remove(2);
        binaryTree.remove(3);
        binaryTree.remove(4);
        binaryTree.remove(5);
        binaryTree.remove(6);
        binaryTree.remove(7);
        assertTrue(binaryTree.isEmpty());
    }
}
