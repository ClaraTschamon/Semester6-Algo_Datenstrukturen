# Acht Puzzle

### Iterative Deepening Search
Gehört zur Kategorie der uninformierten Suchen.  
Iterative Deepening umgeht das Problem, die richtige Tiefenbeschränkung
zu finden.

### Greedy Search
Nimmt immer den kürzesten Weg richtung Ziel und
berücksichtigt nicht, die bereits zurückgelegte Weglänge

### A* Search
Garantiert die optimale Lösung.  
Es wird immer berechnet, welcher Weg eingeschlagen werden muss, damit
der gesamt zurückgelegte Weg am kürzesten ist.

### Uniform Cost Expansion
Die Uniform-Cost-Search gehört zu den uninformierten Suchverfahren (ohne Heuristiken).  
Dieses Suchverfahren ist optimal.  
Uniform-Cost ist eine Modifikation der Breitensuche, die immer zuerst den Knoten nmit den geringsten Pfadkosten g(n)expandiert.  
In unserem Fall hier hat aber jeder Pfad Kosten von 1.

# Heuristiken:
Folgende Heuristiken wurden für die Greedy Search und die A* Search
verwendet:

- h1: "Number of misplaced tiles"
- h2: "Manhattan distance of each tile to its correct position"
- h3: "Number of tiles which are at the right position"

# Ergebnistabelle:
Die Tabelle zeigt, wie viele Knoten expandiert wurden.

| Suchalgorithmus            | h1      | h2 | h3     |
|----------------------------|---------|--|--------|
| Greedy Search              | 788     | 47171 | 267676 |
| A* Search                  | 843     | 43816 | 181439 |
| Iterative Deepening Search | 1155803 | Tiefe: 43 |        |
| Uniform Cost Expansion     | 70812     |  |
