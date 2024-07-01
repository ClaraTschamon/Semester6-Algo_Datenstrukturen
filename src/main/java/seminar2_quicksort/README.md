# Quicksort

Beim Quicksort wird eine Liste durch das Divide-and-Conquer Verfahren
sortiert.

1) es wird ein Pivotelement gewählt. In meiner Implementierung
    ist das Pivotelement immer das letzte Element im Array.
2) Jedes Element wird mit dem Pivot verglichen.
     Wenn ein Element kleiner als der Pivot ist, wird es nach vorne verschoben, indem es mit dem Element bei upperPointer + 1 getauscht wird. 
     Der upperPointer wird erhöht. Nachdem alle Elemente durchlaufen wurden, 
     wird das Pivot-Element an die richtige Position (zwischen den kleineren und größeren Elementen) getauscht.
3) Nachdem die Partitionierung abgeschlossen ist, wird die quicksort-Methode rekursiv auf die beiden entstandenen Teile des 
     Arrays angewendet:
     Der Teil links vom Pivot (lowIndex bis pivot - 1).
     Der Teil rechts vom Pivot (pivot + 1 bis highIndex).
