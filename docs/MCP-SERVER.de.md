# MCP-Server Dokumentation

Vollständige Dokumentation des Model Context Protocol (MCP) Servers für das Steam-Like Schulprojekt.

## Überblick

Der MCP-Server ist eine Node.js-Anwendung, die das Model Context Protocol implementiert und sichere Datenbankoperationen bereitstellt.

**Sprache:** JavaScript (Node.js 20+)  
**Abhängigkeiten:**
- `@modelcontextprotocol/sdk` - MCP SDK
- `pg` - PostgreSQL-Client

## Installation

### Lokale Installation

```bash
cd mcp
npm install
```

### Docker Image

```bash
# Image bauen
podman build -t steam-like-school-project-mcp-server:latest mcp/

# Container starten
podman run -it \
  -e DB_HOST=postgres-db \
  -e DB_PORT=5432 \
  -e DB_NAME=app \
  -e DB_USER=postgres \
  -e DB_PASSWORD=postgres \
  steam-like-school-project-mcp-server:latest
```

## Konfiguration

### Umgebungsvariablen

```bash
DB_HOST       # Datenbankserver Hostname (Standard: db)
DB_PORT       # Datenbankserver Port (Standard: 5432)
DB_NAME       # Datenbankname (Standard: app)
DB_USER       # Datenbankbenutzer (Standard: postgres)
DB_PASSWORD   # Datenbankpasswort (Standard: postgres)
```

### Docker Compose Integration

```yaml
mcp-server:
  build:
    context: ./mcp
    dockerfile: Dockerfile
  container_name: mcp-server-app
  environment:
    - DB_HOST=db
    - DB_PORT=5432
    - DB_NAME=app
    - DB_USER=postgres
    - DB_PASSWORD=postgres
  depends_on:
    db:
      condition: service_healthy
  restart: always
```

## Tools (API)

Der MCP-Server stellt 4 Haupttools zur Verfügung:

### 1. `query_database`

Führt SQL SELECT, INSERT, UPDATE oder DELETE Abfragen aus.

**Input Schema:**
```json
{
  "type": "object",
  "properties": {
    "query": {
      "type": "string",
      "description": "SQL query to execute"
    }
  },
  "required": ["query"]
}
```

**Beispiel:**
```javascript
{
  "query": "SELECT * FROM games WHERE price < 20"
}
```

**Output:**
```json
{
  "rows": [
    {
      "id": "game-1",
      "titel": "Awesome Game",
      "price": 19.99
    }
  ],
  "rowCount": 1,
  "command": "SELECT"
}
```

**Use Cases:**
- Abfragen von Spielen, Benutzern, DLCs
- Daten filtern und sortieren
- Berechnungen durchführen

### 2. `list_tables`

Listet alle Tabellen in der Datenbank auf.

**Input Schema:**
```json
{
  "type": "object",
  "properties": {},
  "required": []
}
```

**Output:**
```json
[
  "users",
  "games",
  "dlcs",
  "user_owned_games",
  "user_owned_dlcs"
]
```

**Use Cases:**
- Datenbank-Exploration
- Schema-Discovery
- Dokumentation generieren

### 3. `get_table_schema`

Ruft die Spalten-Definitionen einer Tabelle ab.

**Input Schema:**
```json
{
  "type": "object",
  "properties": {
    "table_name": {
      "type": "string",
      "description": "Name of the table"
    }
  },
  "required": ["table_name"]
}
```

**Beispiel:**
```javascript
{
  "table_name": "games"
}
```

**Output:**
```json
[
  {
    "column_name": "id",
    "data_type": "character varying",
    "is_nullable": "NO"
  },
  {
    "column_name": "titel",
    "data_type": "character varying",
    "is_nullable": "YES"
  },
  {
    "column_name": "price",
    "data_type": "double precision",
    "is_nullable": "NO"
  }
]
```

**Use Cases:**
- Tabellen-Schema verstehen
- Validierung von Spalten-Typen
- Dokumentation erstellen

### 4. `execute_write`

Führt Schreiboperationen (INSERT, UPDATE, DELETE) mit erhöhter Sicherheit aus.

**Input Schema:**
```json
{
  "type": "object",
  "properties": {
    "query": {
      "type": "string",
      "description": "SQL write query (INSERT, UPDATE, DELETE)"
    }
  },
  "required": ["query"]
}
```

**Beispiel - INSERT:**
```javascript
{
  "query": "INSERT INTO games (id, titel, price, created_at) VALUES ('game-123', 'New Game', 29.99, NOW()) RETURNING *"
}
```

**Output:**
```json
{
  "success": true,
  "rowCount": 1,
  "command": "INSERT",
  "rows": [
    {
      "id": "game-123",
      "titel": "New Game",
      "price": 29.99,
      "created_at": "2026-05-19T10:00:00Z"
    }
  ],
  "message": "INSERT query executed. Rows affected: 1"
}
```

**Beispiel - UPDATE:**
```javascript
{
  "query": "UPDATE games SET price = 19.99 WHERE id = 'game-123' RETURNING *"
}
```

**Beispiel - DELETE:**
```javascript
{
  "query": "DELETE FROM games WHERE id = 'game-123'"
}
```

**Features:**
- RETURNING-Klausel-Unterstützung
- Betroffene Zeilen zählen
- Fehlerbehandlung

## Fehlerbehandlung

### Beispiel-Fehler: Ungültige Tabelle

**Request:**
```javascript
{
  "name": "get_table_schema",
  "arguments": {
    "table_name": "nonexistent"
  }
}
```

**Response:**
```json
{
  "content": [
    {
      "type": "text",
      "text": "Error: relation \"nonexistent\" does not exist"
    }
  ],
  "isError": true
}
```

### Häufige Fehler

| Fehler | Ursache | Lösung |
|--------|--------|--------|
| `Connection refused` | Datenbankserver läuft nicht | DB-Container starten |
| `relation does not exist` | Tabellenname falsch | `list_tables` verwenden |
| `NOT NULL constraint violation` | Erforderliches Feld fehlt | Schema mit `get_table_schema` überprüfen |
| `syntax error` | Ungültige SQL | SQL-Syntax validieren |

## Praxisbeispiele

### Alle Spiele mit Preis > 20€ abrufen

```javascript
{
  "name": "query_database",
  "arguments": {
    "query": "SELECT id, titel, price FROM games WHERE price > 20 ORDER BY price DESC"
  }
}
```

### Benutzer und seine gekauften Spiele

```javascript
{
  "name": "query_database",
  "arguments": {
    "query": `
      SELECT u.username, g.titel, g.price
      FROM users u
      LEFT JOIN user_owned_games uog ON u.id = uog.user_id
      LEFT JOIN games g ON uog.game_id = g.id
      WHERE u.id = 1
    `
  }
}
```

### Neues Spiel eintragen

```javascript
{
  "name": "execute_write",
  "arguments": {
    "query": `
      INSERT INTO games (id, titel, description, price, created_at)
      VALUES ('new-game-001', 'Epic Adventure', 'An amazing adventure game', 39.99, NOW())
      RETURNING *
    `
  }
}
```

### Spielpreis aktualisieren

```javascript
{
  "name": "execute_write",
  "arguments": {
    "query": "UPDATE games SET price = 14.99 WHERE id = 'game-123' RETURNING *"
  }
}
```

## Konsole Starten

### Separate Development-Instanz

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

### Logs ansehen

```bash
podman logs -f mcp-server-app
```

## Security Best Practices

### Input-Validierung
- Sanitize SQL-Eingaben
- Prepared Statements verwenden
- Parameter-Binding nutzen

### Datenbankzugriff
- Separate Credentials pro Umgebung
- Least Privilege Prinzip
- Audit-Logging für Writes

### Netzwerk
- MCP-Server nur intern verfügbar
- TLS für Remote-Verbindungen
- Firewall-Regeln konfigurieren

## Performance-Tipps

### Query-Optimierung

```javascript
// ❌ Schlecht: N+1 Problem
users.forEach(u => db.query(`SELECT * FROM games WHERE user_id = ${u.id}`))

// ✅ Gut: Single Join Query
SELECT g.* FROM games g
JOIN user_owned_games uog ON g.id = uog.game_id
JOIN users u ON u.id = uog.user_id
```

### Indizes verwenden

```sql
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_games_price ON games(price);
CREATE INDEX idx_user_owned_games_user_id ON user_owned_games(user_id);
```

### Pagination für große Ergebnismengen

```javascript
{
  "query": "SELECT * FROM games ORDER BY id LIMIT 100 OFFSET 0"
}
```

## Monitoring

### Verbindungs-Status überprüfen

```bash
# Logs ansehen
podman logs mcp-server-app | grep -E "Connected|Error"
```

### Query Performance

```javascript
{
  "query": "EXPLAIN ANALYZE SELECT * FROM games WHERE price > 20"
}
```

## Troubleshooting

### MCP-Server startet nicht

```bash
# Logs überprüfen
podman logs mcp-server-app

# DB-Verbindung testen
podman exec postgres-db psql -U postgres -c "SELECT 1"
```

### Queries sind langsam

1. EXPLAIN ANALYZE verwenden
2. Indizes überprüfen
3. N+1 Probleme suchen
4. Connection-Pool-Größe prüfen

### Datenbankfehler bei Writes

1. Schema mit `get_table_schema` überprüfen
2. Beispiel-Query mit `execute_write` testen
3. RETURNING-Klausel für Debugging hinzufügen

## Nächste Schritte

- Siehe [INSTALLATION.de.md](INSTALLATION.de.md) für Setup
- Siehe [ARCHITEKTUR.de.md](ARCHITEKTUR.de.md) für System-Architektur
- Siehe [API.de.md](API.de.md) für Backend-API
