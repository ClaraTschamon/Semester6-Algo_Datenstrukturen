# Kruskal

Der Kruskal Algorithmus wird verwendet, um einen minimalen 
Spannbaum in einem ungerichteten Graphen zu finden.

Der Kruskal Algorithmus fügt immer die nächst kürzeste Kante zum Graphen
hinzu, falls beide Knoten noch nicht besucht sind.

### Kruskal vs. Prim
Der Unterschied zum Kruskal-Algorithmus liegt darin, wie der minimale 
Spannbaum aufgebaut wird.   
Beim Prim-Algorithmus wird mit einem Knoten gestartet. Von diesem ausgehend 
wird nach und nach ein Teilgraph gebildet. Der Kruskal-Algorithmus hingegen 
sortiert die Kanten nach den Gewichten und fügt sie in aufsteigender Reihenfolge hinzu.
