package seminar2_trees;

public class AVLTree {

    class Node {
        int value;
        int height;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            height = 1;
            right = null;
            left = null;
        }
    }
    Node root;

    public AVLTree() {
        root = null;
    }

    private int getHeight(Node node){
        if (node == null){
            return 0;
        }
        return node.height;
    }

    private int getBalance(Node node){
        if (node == null){
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    private Node rightRotate(Node node){
        Node newRoot = node.left;
        Node rightSubtreeOfNewRoot = newRoot.right;

        // Perform rotation
        newRoot.right = node;
        node.left = rightSubtreeOfNewRoot;

        // Update heights
        node.height = max(getHeight(node.left), getHeight(node.right)) + 1;
        newRoot.height = max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;

        // Return new root
        return newRoot;
    }

    private Node leftRotate(Node node) {
        Node newRoot = node.right;
        Node leftSubtreeOfNewRoot = newRoot.left;

        // Perform rotation
        newRoot.left = node;
        node.right = leftSubtreeOfNewRoot;

        // Update heights
        node.height = max(getHeight(node.left), getHeight(node.right)) + 1;
        newRoot.height = max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;

        // Return new root
        return newRoot;
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
        } else if (value > root.value) {
            root.right = insertRec(root.right, value);
        } else {
            return root; //Duplicate keys are not allowed
        }

        // Update height of this ancestor node
        root.height = 1 + max(getHeight(root.left), getHeight(root.right));

        // Get the balance factor of this ancestor node to check whether this node became unbalanced
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are four cases
        // Left Left Case
        if (balance > 1 && value < root.left.value)
            return rightRotate(root);

        // Right Right Case
        if (balance < -1 && value > root.right.value)
            return leftRotate(root);

        // Left Right Case
        if (balance > 1 && value > root.left.value) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Left Case
        if (balance < -1 && value < root.right.value) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    public boolean search(Node currentNode, int value) {
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

    public int getMin(Node currentNode) {
        return currentNode.left == null ? currentNode.value : getMin(currentNode.left);
    }

    public int getMax(Node currentNode){
        return currentNode.right == null ? currentNode.value : getMax(currentNode.right);
    }


    /*The maximum value in left subtree of node is predecessor*/
    public Integer getPredecessor(Node root, int value) {

        Node current = findNode(root, value);
        if (current == null) {
            return null;
        }

        // If current node has left child, predecessor is the maximum value in its left subtree
        if (current.left != null) {
            return getMax(current.left);
        }

        // If current node doesn't have left child, predecessor is the parent of current node
        Node predecessor = null;
        Node ancestor = root;
        while (ancestor != current) {
            if (current.value > ancestor.value) {
                predecessor = ancestor;
                ancestor = ancestor.right;
            } else {
                ancestor = ancestor.left;
            }
        }

        return predecessor != null ? predecessor.value : null;
    }

    /*The minimum value in right subtree is successor*/
    public Integer getSuccessor(Node root, int value){

        Node current = findNode(root, value);
        if (current == null) {
            return null; // No successor found
        }

        // If current node has right child, successor is the minimum value in its right subtree
        if (current.right != null) {
            return getMin(current.right);
        }

        // If current node doesn't have right child, successor is the parent of current node
        Node successor = null;
        Node ancestor = root;
        while (ancestor != current) {
            if (current.value < ancestor.value) {
                successor = ancestor;
                ancestor = ancestor.left;
            } else {
                ancestor = ancestor.right;
            }
        }

        return successor != null ? successor.value : null;
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

    public void traveseInOrder(Node current) {
        if (current != null) {
            traveseInOrder(current.left);
            System.out.print(" " + current.value);
            traveseInOrder(current.right);
        }
    }

    public Node remove(Node current, int value) {
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
    public boolean updateNode(Node tree, int oldValue, int newValue) {
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
        AVLTree tree = new AVLTree();
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

        System.out.println("\n\n");
        tree.insert(9);
        tree.printTree("", tree.root);

        System.out.println("\n\n");
        tree.insert(16);
        tree.insert(17);
        tree.insert(18);
        tree.insert(19);
        tree.insert(20);
        tree.printTree("", tree.root);
    }
}
