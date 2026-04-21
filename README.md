# Video Game and DLC Management Software

A management software for organizing video games and their DLCs (Downloadable Content).

## Description

This project is a Steam-like management software that allows you to organize and manage video games and their associated DLCs. The application was developed as a school project in Java and uses .class files to execute the core logic.

## Concept

The concept of this software is to provide a platform for managing video games and their downloadable content (DLCs), similar to Steam, but implemented as a school project in Java. Users can add, organize, and manage their video games and assign DLCs to each game. The core functionality is realized through .class files, which contain the logic for managing games and DLCs. The goal is to offer a structured and user-friendly solution for efficiently handling a digital collection of games and DLCs. The software is designed for educational purposes and can be extended with additional features in the future.

## Features

- Manage video games
- Manage DLCs (Downloadable Content)
- Assign DLCs to individual games
- Organize and categorize games
- Administrative functions for the game library
- Store and execute core logic via .class files

## Ideas

- GUI interface
- CLI commands
- Database storage
- Ability to track playtime
- Advanced DLC management (e.g. DLC lists, DLC categorization)
- ...

## Requirements

- Java Development Kit (JDK) 8 or higher
- IntelliJ IDEA (recommended) or another Java IDE

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/byte-jn/steam-like-school-project
   ```

2. Open the project in IntelliJ IDEA or another IDE

3. Make sure the JDK is configured correctly

## Running the Application

### With IntelliJ IDEA
- Open the file `Main.java` in the `src` folder
- Click the green "Run" button or press `Shift + F10`

### Using the Command Line
```bash
cd src
javac Main.java
java Main
```

## Project Structure

```
steam-like-school-project/
├── app/
│   └── src/
│       └── main/
│           └── java/
│               └── org/example/
│                   ├── Main.java          # Main entry point of the application
│                   ├── Games.java         # Game management
│                   ├── DLC.java           # DLC management
│                   └── ...                # Other .class files for logic
├── db/                # Database configuration
├── README.md          # This file
└── ...                # Other configuration files
```

## Development

This is a school project and is currently under development. More features, especially for advanced DLC management, will be added over time.

## License

This project was created for educational purposes.

## Author

School project

## Status

In development
