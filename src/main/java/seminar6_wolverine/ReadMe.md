<h1>Wolverine</h1>

Die beiden Varianten zur Lösung des Problems haben jeweils ihre eigenen Vor- und Nachteile:

<h2>GameStateBitmask (Bitmaskenbasiert):</h2>  
<b>Vorteile:</b>  
<u>Speicherplatzeffizienz:</u> Da der Zustand des Spiels als einzelner Byte repräsentiert wird.  
<u>Effiziente Bitoperationen:</u> Durch die Verwendung von Bitoperationen können Zustandsänderungen effizient durchgeführt werden, was zu einer schnelleren Ausführung führt.  

<b>Nachteile:</b>  
<u>Lesbarkeit und Komplexität:</u> Die Verwendung von Bitmasken kann den Code weniger lesbar machen.  
<u>Begrenzte Erweiterbarkeit:</u> Das Hinzufügen zusätzlicher Objekte oder Regeln zum Spiel kann die Bitmaskenlösung komplizierter und unübersichtlicher machen.  

<h2>GameStateObjectOriented (objektorientiert):</h2>  
<b>Vorteile:</b>  
<u>Lesbarkeit und Verständlichkeit:</u> Die objektorientierte Lösung bietet in der Regel einen klareren und leichter verständlichen Code.  
<u>Erweiterbarkeit und Wartbarkeit:</u> Durch die Verwendung von Objekten und Klassen ist die objektorientierte Lösung oft leichter zu erweitern und anzupassen, insbesondere wenn neue Objekte oder Regeln hinzugefügt werden müssen.  

<b>Nachteile:</b>  
<u>Speicherplatzverbrauch und Leistungseinbußen:</u> Die objektorientierte Lösung kann tendenziell mehr Speicherplatz verbrauchen und langsamer sein., da jedes Objekt seine eigenen Attribute und Referenzen benötigt.
