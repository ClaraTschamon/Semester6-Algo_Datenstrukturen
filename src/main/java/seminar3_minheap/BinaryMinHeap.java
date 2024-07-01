package seminar3_minheap;

public class BinaryMinHeap {

    class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            right = null;
            left = null;
        }
    }
    Node root;

    public BinaryMinHeap() {
        root = null;
    }

    // --------------------------------------------------------------------------------

    //1. Neue Knoten werden immer als Blatt eingefügt an der ersten freien Stelle (von links)
    //2. Wird durch den neu hinzugefügten Knoten die Ordnung verletzt, muss der Knoten mit seinem Parent getauscht werden
    //3. Nach dem Tausch erfolgt eine neuerliche Prüfung mit dem Parent ➔ Fortsetzen mit 2.
    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    private Node insertRecursive(Node root, int value) {
        // If the heap is empty, insert as the root
        if (root == null) {
            root = new Node(value);
            return root;
        }

        // Traverse the tree to find the correct position to insert the new node
        if (root.left == null) {
            // Insert as the left child if left child is null
            root.left = new Node(value);
        } else if (root.right == null) {
            // Insert as the right child if left child is not null but right child is null
            root.right = new Node(value);
        } else {
            // Both children exist, so recursively insert into the subtree with smaller size
            if (subtreeSize(root.left) <= subtreeSize(root.right)) {
                root.left = insertRecursive(root.left, value);
            } else {
                root.right = insertRecursive(root.right, value);
            }
        }

        // Restore heap property by swapping with parent if necessary
        root = heapify(root);

        return root;
    }

    // Helper function to get the size of the subtree
    // the size of the subtree is the number of nodes in the subtree
    private int subtreeSize(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + subtreeSize(node.left) + subtreeSize(node.right);
    }

    // Helper function to get the parent of a given node
    private Node getParent(Node root, Node node) {
        if (root == null || root == node) {
            return null;
        }

        if (root.left == node || root.right == node) {
            return root;
        }

        Node leftParent = getParent(root.left, node);
        if (leftParent != null) {
            return leftParent;
        }

        return getParent(root.right, node);
    }

    //--------------------------------------------------------------------------------

    // Der kleinste Wert befindet sich im Root-Knoten
    // Um diesen Wert zu extrahieren (und damit aus dem Baum zu entfernen) ist folgender Ablauf notwendig:
    // 1. Finde den letzten Knoten im Heap (letzter rechter Kind-Knoten)
    // 2. Ersetze den Root-Node mit diesem Knoten
    // 3. Prüfe ob die Heap-Bedingungen verletzt sind
        //a. Wenn ja, tausche mit Kind-Knoten
        //b. Setze bei 3. fort
    private void extractMin() {
        if (root == null) {
            return;
        }

        // Find the rightmost node
        Node rightMostNode = findRightMostNode(root);

        // Replace root value with the value of rightmost node
        root.value = rightMostNode.value;

        // Remove the rightmost node
        root = removeRightMostNode(root);

        // Heapify the tree after extraction
        root = heapify(root);
    }

    // Helper function to find the rightmost node in the heap
    private Node findRightMostNode(Node root) {
        while (root.right != null) {
            root = root.right;
        }
        return root;
    }

    // Helper function to remove the rightmost node
    private Node removeRightMostNode(Node root) {
        if (root == null) {
            return null;
        }

        if (root.right == null) {
            return root.left;
        }

        root.right = removeRightMostNode(root.right);

        return root;
    }

    // Helper function to heapify the tree after extraction
    private Node heapify(Node root) {
        if (root == null) {
            return null;
        }

        // Heapify subtree if needed
        if (root.left != null && root.left.value < root.value) {
            swap(root, root.left);
            root.left = heapify(root.left);
        }
        if (root.right != null && root.right.value < root.value) {
            swap(root, root.right);
            root.right = heapify(root.right);
        }

        return root;
    }

    // Helper function to swap values of two nodes
    private void swap(Node node1, Node node2) {
        int temp = node1.value;
        node1.value = node2.value;
        node2.value = temp;
    }

    private void printHeap(String prefix, Node n) {
        if (n != null) {
            printHeap(prefix + "     ", n.right);
            System.out.println (prefix + ("|-- ") + n.value);
            printHeap(prefix + "     ", n.left);
        }
    }

    public static void main(String[] args) {
        BinaryMinHeap binaryMinHeap = new BinaryMinHeap();
        binaryMinHeap.insert(8);
        binaryMinHeap.insert(3);
        binaryMinHeap.insert(12);
        binaryMinHeap.insert(1);
        binaryMinHeap.insert(100);
        binaryMinHeap.insert(120);
        binaryMinHeap.insert(0);
        binaryMinHeap.insert(2);
        binaryMinHeap.insert(40);

        binaryMinHeap.printHeap("", binaryMinHeap.root);

        System.out.println("------------------------");
        System.out.println("Extracting min value");

        binaryMinHeap.extractMin();
        binaryMinHeap.printHeap("", binaryMinHeap.root);

        System.out.println("------------------------");
        System.out.println("Extracting min value");

        binaryMinHeap.extractMin();
        binaryMinHeap.printHeap("", binaryMinHeap.root);

        System.out.println("------------------------");
        System.out.println("Insert 1");

        binaryMinHeap.insert(1);
        binaryMinHeap.printHeap("", binaryMinHeap.root);
    }
}
