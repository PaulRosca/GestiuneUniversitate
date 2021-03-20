# GestiuneUniversitate

### Informații generale

Proiectul este o aplicație nativă de gestiune a unei universități, realizată pentru 3 tipuri de utilizatori 
  * Studenți
  * Profesori
  * Secretare
  
Pentru acest proiect am ales să folosesc baza de date de la Oracle și anume versiunea *__Oracle
Database Express Edition (XE) Release 18.4.0.0.0 (18c)__*.

Partea de programare am realizat-o în Java, folosind driver-ul *__Oracle Database 18c (18.3)
JDBC Driver__* pentru conectarea la baza de date și API-ul Swing pentru interfața grafică.

Validarea datelor din tabele s-a realizat înainte de inserare atât la nivelul aplicației cât și la nivelul
bazei de date prin intermediul constraint-urilor și al trigger-urilor.

Deoarece Oracle nu suportă tipul de date boolean am folosit pe post de înlocuitor tipul de date
number(1,0) împreună cu un check constraint, pentru a permite doar valori de 1 și 0.

### Setup
Pentru utilizarea aplicației este necesar să fie instalată o versiune de java și baza de date de la Oracle.

Procesul de setup este unul simplu, în primul rănd se rulează în Oracle scriptul *CreareBD.sql*. După care se copiază folderul *Executabil* în directorul dorit și se modifică credențialele si url-ul bazei de date din fișierul *connection* din folderul respectiv, după care aplicația ar trebui să funcționeze.

În folderul *errors* vor apărea eventualele erori.


### Demo

https://www.youtube.com/watch?v=NkNcS5ejGiE
