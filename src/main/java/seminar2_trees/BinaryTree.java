package seminar2_trees;

public class BinaryTree {

    class Node {
        int value;
        Node left;
        Node right;
        Node parent;

        Node(int value) {
            this.value = value;
            right = null;
            left = null;
            parent = null;
        }
    }
    Node root;

    public BinaryTree() {
        root = null;
    }


    private void insert(int value) {
        root = insertRec(root, value);
    }

    private Node insertRec(Node root, int value) {
        if (root == null) {
            root = new Node(value);
            return root;
        }

        if (value < root.value) {
            root.left = insertRec(root.left, value);
            root.left.parent = root;
        } else if (value > root.value) {
            root.right = insertRec(root.right, value);
            root.right.parent = root;
        }

        return root;
    }

    private boolean search(Node currentNode, int value) {
        if (currentNode == null) {
            return false;
        }

        if (value == currentNode.value) {
            return true;
        }

        return value < currentNode.value
                ? search(currentNode.left, value)
                : search(currentNode.right, value);
    }

    private int getMin(Node currentNode) {
        return currentNode.left == null ? currentNode.value : getMin(currentNode.left);
    }

    private int getMax(Node currentNode){
        return currentNode.right == null ? currentNode.value : getMax(currentNode.right);
    }


    /*The maximum value in left subtree of node is the predecessor*/
    private Integer getPredecessor(Node root, int value) {

        Node current = findNode(root, value);
        if (current == null) {
            return null;
        }

        // If current node has left child, predecessor is the maximum value in its left subtree
        if (current.left != null) {
            return getMax(current.left);
        }

        Node fathernode = current.parent;

        while (fathernode != null && fathernode.left == current) { // wenn knoten rechtes kind ist, dann ist der parent der predecessor
            current = fathernode;
            fathernode = current.parent;
        }

        return fathernode != null ? fathernode.value : null;
    }

    /*The minimum value in right subtree is successor*/
    private Integer getSuccessor(Node root, int value){

        Node current = findNode(root, value);
        if (current == null) {
            return null; // No successor found
        }

        // 1. Fall: Knoten hat rechten Kindknoten
        if (current.right != null) {
            return getMin(current.right);
        }

        // 2. Fall: Knoten hat keinen rechten Kindknoten. Successor liegt auf dem Pfad zur Wurzel
        Node fathernode = current.parent;

        while (fathernode != null && fathernode.right == current) { // wenn knoten linkes kind ist, dann ist der parent der successor
            current = fathernode;
            fathernode = current.parent;
        }

        return fathernode != null ? fathernode.value : null;
    }

    private Node findNode(Node current, int value) {
        while (current != null && current.value != value) {
            if (value < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return current;
    }

    private void traveseInOrder(Node current) {
        if (current != null) {
            traveseInOrder(current.left);
            System.out.print(" " + current.value);
            traveseInOrder(current.right);
        }
    }

    private Node remove(Node current, int value) {
        if (current == null) {
            return null;
        }

        if (value == current.value) {
            // Case 1: no children
            if (current.left == null && current.right == null) {
                return null;
            }

            // Case 2: only 1 child
            if (current.right == null) {
                return current.left;
            }

            if (current.left == null) {
                return current.right;
            }

            // Case 3: 2 children
            int smallestValue = getMin(current.right);
            current.value = smallestValue;
            current.right = remove(current.right, smallestValue);
            return current;
        }
        if (value < current.value) {
            current.left = remove(current.left, value);
            return current;
        }

        current.right = remove(current.right, value);
        return current;
    }


    /*
    Schaffen Sie eine Möglichkeit den Wert eines bestimmten Knotens zu aktualisieren. Dabei müssen
    Sie die Grenzen für eine zulässige Aktualisierung beachten und dürfen die Struktur des binären
    Suchbaumes nicht verletzen. Wenn Sie einen Wert aktualisieren darf er sich daher nur zwischen
    dem Predecessor und Successor befinden.*/
    private boolean updateNode(Node tree, int oldValue, int newValue) {
        if (!search(root, oldValue)) {
            return false; // Node with oldValue not found in the tree
        }

        // Check if newValue is between predecessor and successor
        int predecessor = getPredecessor(root, oldValue);
        int successor = getSuccessor(root, oldValue);
        if (newValue <= predecessor || newValue >= successor) {
            return false; // newValue is not within the range
        }

        // Remove the node with oldValue and insert node with newValue
        root = remove(root, oldValue);
        insert(newValue);
        return true;
    }

    private void printTree(String prefix, Node n) {
        if (n != null) {
            printTree(prefix + "     ", n.right);
            System.out.println (prefix + ("|-- ") + n.value);
            printTree(prefix + "     ", n.left);
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.insert(5);
        tree.insert(7);
        tree.insert(2);
        tree.insert(4);
        tree.insert(6);
        tree.insert(8);
        tree.insert(3);
        tree.insert(10);
        tree.insert(15);
        tree.insert(0);

        tree.printTree("", tree.root);

        System.out.println("Traverse in Order: ");
        tree.traveseInOrder(tree.root);

        System.out.println("\nMin: " + tree.getMin(tree.root));
        System.out.println("Max: " + tree.getMax(tree.root));
        System.out.println("Find 4: " + tree.search(tree.root, 4));
        System.out.println("Find 1: " + tree.search(tree.root, 1));
        System.out.println("Predecessor of 6: " + tree.getPredecessor(tree.root, 6));
        System.out.println("Successor of 6: " + tree.getSuccessor(tree.root, 6));
        System.out.println("Remove 3:");
        System.out.println(tree.remove(tree.root, 3));
        tree.printTree("-", tree.root);

        System.out.println("Update node to new value: " + tree.updateNode(tree.root, 10, 12));
        tree.printTree("", tree.root);
    }
}
