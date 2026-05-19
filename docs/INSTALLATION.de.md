# Installationsanleitung

Schritt-für-Schritt Anleitung zur Installation und zum Starten des Steam-Like Schulprojekts.

## Voraussetzungen

### Erforderlich
- **Podman** oder **Docker Desktop**
- **Git** für Versionskontrolle
- Mindestens **2GB RAM** verfügbar

### Optional
- **Node.js 20+** für lokale MCP-Server-Entwicklung
- **Java 17+** für Backend-Entwicklung
- **PostgreSQL-Client** für direkte Datenbankabfragen

## Installation unter Windows

### 1. Podman installieren

```powershell
# Mit Windows Package Manager (empfohlen)
winget install RedHat.Podman

# Oder über Chocolatey
choco install podman
```

Nach der Installation Podman starten:
```powershell
podman machine start
```

### 2. Repository klonen

```powershell
git clone https://github.com/your-org/steam-like-school-project.git
cd steam-like-school-project
```

### 3. Services starten

```powershell
# Mit Podman Compose
podman-compose up -d

# Services überprüfen
podman ps
```

Erwartete Ausgabe:
```
CONTAINER ID  IMAGE          NAMES
...           postgres:16    postgres-db
...           gradle-app     gradle-hibernate-app
...           mcp-server     mcp-server-app
```

### 4. Datenbankmigrationen überprüfen

```powershell
# Logs des Datenbankservers ansehen
podman logs postgres-db

# Nach "database system is ready to accept connections" suchen
```

## Installation unter Linux/macOS

### 1. Podman installieren

**Ubuntu/Debian:**
```bash
sudo apt-get update
sudo apt-get install podman podman-compose
```

**macOS:**
```bash
brew install podman podman-compose
```

### 2. Podman-Maschine starten

```bash
podman machine init
podman machine start
```

### 3. Repository klonen und starten

```bash
git clone https://github.com/your-org/steam-like-school-project.git
cd steam-like-school-project
podman-compose up -d
```

## Konfiguration

### Umgebungsvariablen

Die folgenden Variablen können in `docker-compose.yml` angepasst werden:

```yaml
environment:
  DB_HOST: db
  DB_PORT: 5432
  DB_NAME: app
  DB_USER: postgres
  DB_PASSWORD: postgres
```

### Ports konfigurieren

Standard-Portmappings:
- **8080** → Backend-API
- **5432** → PostgreSQL-Datenbankserver

Zum Ändern: `docker-compose.yml` bearbeiten und Services neu starten:

```bash
podman-compose down
podman-compose up -d
```

## Verifikation der Installation

### Services überprüfen

```bash
# Alle Container anzeigen
podman ps -a

# Service-Logs ansehen
podman logs postgres-db
podman logs gradle-hibernate-app
podman logs mcp-server-app
```

### Datenbankverbindung testen

```bash
# Mit PostgreSQL-Client
psql -h localhost -U postgres -d app

# Query ausführen
SELECT COUNT(*) FROM users;
```

### Backend-API testen

```bash
# Health-Check
curl http://localhost:8080/health

# Oder mit PowerShell
Invoke-WebRequest -Uri http://localhost:8080/health
```

## MCP-Server Konsole starten

### Separate Console-Instanz (Entwicklung)

```bash
podman run -it --rm --name mcp-console-dev \
  -e DB_HOST=postgres-db \
  -e DB_PORT=5432 \
  -e DB_NAME=app \
  -e DB_USER=postgres \
  -e DB_PASSWORD=postgres \
  --network steam-like-school-project_default \
  steam-like-school-project-mcp-server:latest
```

### Logs anzeigen

```bash
# Live-Logs des laufenden MCP-Servers
podman logs -f mcp-server-app
```

## Fehlerbehebung

### "Docker daemon is not running"

**Lösung:**
```powershell
# Windows
podman machine start

# Linux/macOS
podman machine start
```

### "Connection refused" bei Datenbankzugriff

**Mögliche Ursachen:**
1. Datenbank startet noch - warten Sie 30 Sekunden
2. PORT 5432 ist belegt - anderen Dienst stoppen
3. Falsche Umgebungsvariablen

**Debugging:**
```bash
# Container-Logs überprüfen
podman logs postgres-db

# Sollte "database system is ready" enthalten
```

### Out of Memory Fehler

**Lösung:**
```bash
# Mehr RAM für Podman zuteilen
podman machine set --memory 4096
podman machine start
```

## Services herunterfahren

```bash
# Alle Services stoppen
podman-compose down

# Mit Datenlöschung (Vorsicht!)
podman-compose down -v
```

## Nächste Schritte

- Siehe [README.de.md](README.de.md) für Projektüberblick
- Siehe [ARCHITEKTUR.de.md](ARCHITEKTUR.de.md) für technische Details
- Siehe [MCP-SERVER.de.md](MCP-SERVER.de.md) für MCP-Server Dokumentation
