# Dijkstra
Herausforderung:  
Finden von kürzestem Pfad von Knoten v nach Knoten w.  

1) Initialisierung der Distanzen:  
  Ein distances-Map wird erstellt, das die minimale Entfernung von jedem Knoten zum Startknoten speichert. Anfangs wird für alle Knoten die Entfernung auf unendlich (Integer.MAX_VALUE) gesetzt, außer für den Startknoten selbst, der eine Entfernung von 0 hat.


2) Eine Prioritätswarteschlange (minHeap) wird verwendet, um immer den Knoten mit der aktuell kleinsten bekannten Entfernung zu wählen. Zunächst wird der Startknoten in diese Warteschlange eingefügt.


3) Ein previousVertices-Map speichert für jeden Knoten den vorherigen Knoten im kürzesten Pfad, um später den genauen Pfad rekonstruieren zu können.


4) Hauptschleife:  
Solange die Prioritätswarteschlange nicht leer ist, wird der Knoten mit der kleinsten Entfernung (current) herausgenommen.
Wenn der current Knoten der Endknoten ist und dieser spezifiziert ist, wird die Schleife beendet.


5) Aktualisierung der Distanzen:  
Für jeden Nachbarknoten (neighbor) des aktuellen Knotens wird die Entfernung zum Startknoten über den aktuellen Knoten berechnet (newDistance).
Wenn diese neue Entfernung kürzer ist als die bisher bekannte Entfernung, wird diese im distances-Map aktualisiert.
Der Nachbarknoten und die neue Entfernung werden auch in die Prioritätswarteschlange eingefügt, und der previousVertices-Map wird aktualisiert, um den aktuellen Knoten als vorherigen Knoten des Nachbarn zu speichern.

# Prim
Herausforderung:  
Finden von minimalem Spannbaum

1) Initialisierung:  
   - visited: Set der besuchten Knoten.
   - priorityQueue: Prioritätswarteschlange der Kanten nach Gewicht.
   - minimumSpanningTree: Liste der Kanten im MST.
   Start:
   
   Füge Startknoten zu visited hinzu. Füge alle Kanten des Startknotens zur priorityQueue hinzu.


2) Hauptschleife:  
    - Wähle die leichteste Kante (minEdge). 
    - Bestimme die beiden Knoten der Kante. 
    - Überspringe Kanten, die zu Zyklen führen. 
    - Füge die Kante zum minimumSpanningTree hinzu. 
    - Füge den nicht besuchten Knoten der visited-Menge hinzu und seine Kanten zur priorityQueue.
