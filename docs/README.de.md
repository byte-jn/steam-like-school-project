# Steam-Like Schulprojekt

Ein vollständiges Steam-ähnliches Verwaltungssystem für Spiele und DLCs, entwickelt als Schulprojekt.

## 📋 Überblick

Dieses Projekt bietet eine komplette Plattform zum Verwalten von:
- **Benutzerkonten** mit Authentifizierung
- **Spiele** mit Metadaten (Titel, Beschreibung, Preis)
- **DLC-Packs** für Spiele
- **Benutzerbestände** (gekaufte Spiele und DLCs)

## 🏗️ Projektstruktur

```
steam-like-school-project/
├── app/                      # Java/Gradle Backend-Anwendung
├── db/                        # Datenbankskripte und Migrationen
├── mcp/                       # MCP-Server für Datenbankoperationen
├── sdks/                      # Generierte SDKs für verschiedene Sprachen
├── docs/                      # Dokumentation
└── docker-compose.yml         # Docker/Podman Orchestrierung
```

## 🚀 Schnellstart

### Voraussetzungen
- Podman oder Docker
- Node.js 20+ (optional, nur für lokale Entwicklung)
- Java 17+ (für Backend-Entwicklung)

### Installation

```bash
# Services mit Podman starten
podman-compose up -d

# Services überprüfen
podman ps
```

Dies startet:
- **PostgreSQL 16** - Datenbankserver
- **Java-Backend** - REST-API auf Port 8080
- **MCP-Server** - Datenbankzugriff über Model Context Protocol

## 📦 Komponenten

### Backend (Java/Gradle)
- Hibernate ORM für Datenbankzugriff
- REST-API für Spielverwaltung
- Benutzerauthentifizierung

**Port:** 8080

### Datenbank (PostgreSQL)
- Normalisierte Relational-Datenbank
- Automatische Migrations-Verwaltung
- 5 Haupttabellen (users, games, dlcs, user_owned_games, user_owned_dlcs)

**Port:** 5432

### MCP-Server (Node.js)
- Model Context Protocol Server
- Tools für SQL-Abfragen
- Schemadarstellung
- Datenbankoperationen

## 🗄️ Datenbankschema

Siehe [db-er-diagram.md](db-er-diagram.md) für das ER-Diagramm.

**Tabellen:**
- `users` - Benutzerkonten
- `games` - Spielinformationen
- `dlcs` - DLC-Packs
- `user_owned_games` - Benutzer-Spiel-Zuordnungen
- `user_owned_dlcs` - Benutzer-DLC-Zuordnungen

## 🔧 Entwicklung

### Lokale Datenbankverbindung

```bash
# Umgebungsvariablen setzen
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=app
export DB_USER=postgres
export DB_PASSWORD=postgres
```

### MCP-Server lokal starten

```bash
cd mcp
npm install
npm start
```

## 📚 Dokumentation

- [INSTALLATION.de.md](INSTALLATION.de.md) - Detaillierte Installationsanleitung
- [ARCHITEKTUR.de.md](ARCHITEKTUR.de.md) - Architekturübersicht
- [API.de.md](API.de.md) - API-Referenz
- [MCP-SERVER.de.md](MCP-SERVER.de.md) - MCP-Server Dokumentation

## 🤝 Beitragen

Änderungen sollten:
1. Auf einem Feature-Branch durchgeführt werden
2. Mit Tests abgedeckt sein
3. Nach den Projekt-Konventionen formatiert sein
4. Mit aussagekräftigen Commit-Nachrichten versehen sein

## 📄 Lizenz

Schulprojekt - Siehe LICENSE für Details.
