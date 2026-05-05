# Steam-Like School Project

Ein konsolenbasiertes Java-Projekt, das eine vereinfachte Steam-ähnliche Plattform simuliert. Es richtet sich an Schüler:innen und Studierende, die Java und objektorientierte Programmierung lernen möchten.

## Features

- Benutzerregistrierung und Login
- Anlegen und Auflisten von Spielen
- Anlegen und Auflisten von DLCs
- Hinzufügen von Spielen und DLCs zur Benutzerbibliothek
- Persistenz aller Daten in CSV-Dateien

## Übersicht

Dieses Projekt bietet ein kleines Verwaltungssystem für Benutzer, Spiele und DLCs. Es eignet sich als Lernprojekt für objektorientierte Programmierung, Dateiverarbeitung und einfache Konsoleninteraktion.


## Architektur

Das Projekt ist in folgende Klassen unterteilt:

- **Main**: Einstiegspunkt, enthält die `main`-Methode und startet das Programm.
- **FunctionService**: Steuert den Menüfluss und die Benutzerinteraktion.
- **CsvDataService**: Lädt und speichert Daten aus/in CSV-Dateien.
- **LookupService**: Stellt Hilfsfunktionen für die Suche nach Benutzern, Spielen und DLCs bereit.
- **User, Game, DLC**: Die zentralen Entitäten des Systems.


## Datenmodell

**User**: Benutzername, Passwort, Liste der gekauften Spiele und DLCs

**Game**: Name, Genre, Preis, Entwickler

**DLC**: Name, Zugehöriges Spiel, Preis

## Datenspeicherung (CSV)

Die Daten werden in folgenden Dateien gespeichert:

- `data/csv/users.csv`
- `data/csv/games.csv`
- `data/csv/dlcs.csv`

Falls Ordner oder Dateien fehlen, werden sie beim Start automatisch angelegt. Die CSV-Header werden ebenfalls automatisch erzeugt.

### Beispiel für CSV-Inhalte

**users.csv**
```csv
username,password,ownedGames,ownedDlcs
max,mypass,Half-Life;Portal,HL2-DLC
```

**games.csv**
```csv
name,genre,price,developer
Half-Life,Shooter,9.99,Valve
Portal,Puzzle,7.99,Valve
```

**dlcs.csv**
```csv
name,game,price
HL2-DLC,Half-Life,2.99
```


## Voraussetzungen

- JDK 8 oder höher
- IntelliJ IDEA (empfohlen) oder eine andere Java-IDE


## Ausführen

### In IntelliJ IDEA
1. Projekt öffnen
2. `app/src/main/java/org/example/Main.java` öffnen
3. `Main` ausführen

### Über die Windows PowerShell
```powershell
Set-Location "C:\Users\JannisLauer\IdeaProjects\steam-like-school-project\app\src\main\java\org\example"
javac *.java
java Main
```


## Projektstruktur

```text
steam-like-school-project/
├─ README.md
├─ app/
│  └─ src/
│     └─ main/
│        └─ java/
│           └─ org/
│              └─ example/
│                 ├─ Main.java
│                 ├─ FunctionService.java
│                 ├─ CsvDataService.java
│                 ├─ LookupService.java
│                 ├─ User.java
│                 ├─ Game.java
│                 └─ DLC.java
└─ data/
   └─ csv/
      ├─ users.csv
      ├─ games.csv
      └─ dlcs.csv
```


## Beispiel-Session

1. Benutzer registrieren oder einloggen
2. Spiele und DLCs anlegen
3. Spiele/DLCs zur eigenen Bibliothek hinzufügen
4. Daten werden automatisch gespeichert

## Testen

Das Projekt verwendet JUnit 5 für Unit-Tests. Beispiel für einen Test:

```java
import org.junit.jupiter.api.Assertions;

@Test
void testGamePrice() {
    Game game = new Game("Half-Life", "Shooter", 9.99, "Valve");
    Assertions.assertEquals(9.99, game.getPrice());
}
```

## Roadmap

- GUI-Version
- Weitere CLI-Kommandos
- Datenbank-Backend statt CSV
- Spielzeit-Tracking


## Beitragende

Pull Requests und Vorschläge sind willkommen! Bitte Issues für Fehler oder Feature-Wünsche anlegen.

## Autor

Jannis Lauer - jannis280@outlook.de


## Lizenz

MIT License (siehe LICENSE-Datei, falls vorhanden)

## Status

In Entwicklung.
