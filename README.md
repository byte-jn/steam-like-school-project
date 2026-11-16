# Steam-Like School Project

A console-based Java project that simulates a simplified Steam-like platform. It is aimed at pupils and students who want to learn Java and object-oriented programming.

## Features

- User registration and login
- Creating and listing games
- Creating and listing DLCs
- Adding games and DLCs to the user's library
- Persistence of all data in CSV files

## Overview

This project provides a small management system for users, games and DLCs. It works well as a learning project for object-oriented programming, file handling and simple console interaction.


## Architecture

The project is split into the following classes:

- **Main**: Entry point, contains the `main` method and starts the program.
- **FunctionService**: Controls the menu flow and the user interaction.
- **CsvDataService**: Loads and saves data from/to CSV files.
- **LookupService**: Provides helper functions to look up users, games and DLCs.
- **User, Game, DLC**: The core entities of the system.


## Data model

**User**: Username, password, list of purchased games and DLCs

**Game**: Name, genre, price, developer

**DLC**: Name, related game, price

## Data storage (CSV)

The data is stored in the following files:

- `data/csv/users.csv`
- `data/csv/games.csv`
- `data/csv/dlcs.csv`

If folders or files are missing, they are created automatically on start-up. The CSV headers are generated automatically as well.

### Example CSV content

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


## Requirements

- JDK 8 or higher
- IntelliJ IDEA (recommended) or another Java IDE


## Running

### In IntelliJ IDEA
1. Open the project
2. Open `app/src/main/java/org/example/Main.java`
3. Run `Main`

### From Windows PowerShell
```powershell
Set-Location "<path-to>\steam-like-school-project\app\src\main\java\org\example"
javac *.java
java Main
```


## Project structure

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


## Example session

1. Register or log in
2. Create games and DLCs
3. Add games/DLCs to your own library
4. Data is saved automatically

## Testing

The project uses JUnit 5 for unit tests. Example test:

```java
import org.junit.jupiter.api.Assertions;

@Test
void testGamePrice() {
    Game game = new Game("Half-Life", "Shooter", 9.99, "Valve");
    Assertions.assertEquals(9.99, game.getPrice());
}
```

## Roadmap

- GUI version
- More CLI commands
- Database backend instead of CSV
- Playtime tracking


## Contributing

Pull requests and suggestions are welcome! Please open issues for bugs or feature requests.

## Author

Jannis Lauer - jannis280@outlook.de


## License

MIT License (see the LICENSE file, if present)

## Status

In development.

## Dependency management with Maven

Maven is now used to manage the required libraries. All dependencies are defined in the `pom.xml` file in the project directory. This file is part of the repository.

### Adding a new library

1. Open the `pom.xml` file.
2. Add a new entry inside the `<dependencies>` block, e.g. for OpenCSV:
   ```xml
   <dependency>
       <groupId>com.opencsv</groupId>
       <artifactId>opencsv</artifactId>
       <version>5.7.1</version>
   </dependency>
   ```
3. Save the file.
4. Run in the console in the project directory:
   ```powershell
   mvn clean install
   ```
   This downloads all dependencies automatically.

### Building and running the project

1. Build the project:
   ```powershell
   mvn clean package
   ```
2. Run the program (example):
   ```powershell
   java -cp target/steam-like-school-project-1.0-SNAPSHOT.jar org.example.Main
   ```

### Benefits
- No more manual .jar files
- Everyone on the team always has the same libraries
- Easy adding/removing of dependencies
