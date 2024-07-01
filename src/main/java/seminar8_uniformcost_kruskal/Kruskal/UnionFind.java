package seminar8_uniformcost_kruskal.Kruskal;

import java.util.HashMap;
import java.util.Map;

public class UnionFind<T> {
    private Map<T, T> parent;
    private Map<T, Integer> rank;

    public UnionFind() {
        parent = new HashMap<>();
        rank = new HashMap<>();
    }

    public void add(T element) {
        parent.put(element, element);
        rank.put(element, 0);
    }

    public T find(T element) {
        if (parent.get(element) != element) {
            parent.put(element, find(parent.get(element))); // Path compression
        }
        return parent.get(element);
    }

    public void union(T element1, T element2) {
        T root1 = find(element1);
        T root2 = find(element2);
        if (root1 != root2) {
            int rank1 = rank.get(root1);
            int rank2 = rank.get(root2);
            if (rank1 > rank2) {
                parent.put(root2, root1);
            } else if (rank1 < rank2) {
                parent.put(root1, root2);
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank1 + 1);
            }
        }
    }
}
