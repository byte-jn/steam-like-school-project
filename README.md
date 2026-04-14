# Steam-Like School Project

Console-based Java school project to manage users, games, and DLCs.

## Overview

This project provides a small Steam-like management system for educational purposes.
You can:

- register/login users,
- create and list games,
- create and list DLCs,
- add owned games and DLCs to a user library,
- persist all data in CSV files.

## Current Architecture

The project was refactored into focused classes:

- `Main` - entry point (`main` method)
- `FunctionService` - menu flow and user interaction
- `CsvDataService` - load/save data from/to CSV
- `LookupService` - shared lookup helpers (user/game/dlc)
- `User`, `Games`, `DLC` - domain entities

## Data Storage (CSV)

Data is stored in:

- `data/csv/users.csv`
- `data/csv/games.csv`
- `data/csv/dlcs.csv`

If the folder/files do not exist, they are created automatically on startup.
CSV headers are also created automatically.

## Requirements

- JDK 8+
- IntelliJ IDEA (recommended) or any Java IDE

## Run

### IntelliJ IDEA

1. Open the project.
2. Open `app/src/main/java/org/example/Main.java`.
3. Run `Main`.

### Windows PowerShell

```powershell
Set-Location "C:\Users\JannisLauer\IdeaProjects\steam-like-school-project\app\src\main\java\org\example"
javac *.java
java Main
```

## Project Structure

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
│                 ├─ Games.java
│                 └─ DLC.java
└─ data/
   └─ csv/
      ├─ users.csv
      ├─ games.csv
      └─ dlcs.csv
```

## Roadmap Ideas

- GUI version
- More CLI commands
- Database backend instead of CSV
- Playtime tracking

## Author

Jannis Lauer - jannis280@outlook.de

## Status

In development.
