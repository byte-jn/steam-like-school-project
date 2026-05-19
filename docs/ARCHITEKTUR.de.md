# Architektur-Dokumentation

Überblick über die Architektur und technische Struktur des Steam-Like Schulprojekts.

## System-Architektur

```
┌─────────────────────────────────────────────────────────────┐
│                     Client / Frontend                        │
└──────────────┬──────────────────────────┬────────────────────┘
               │                          │
        REST API                   MCP Protocol
               │                          │
┌──────────────▼──────────────────▼───────────────────────────┐
│                    Java Backend (Port 8080)                  │
│  ┌─────────────────────────────────────────────────────┐   │
│  │ REST Controllers                                     │   │
│  │ - UserController                                    │   │
│  │ - GameController                                    │   │
│  │ - DLCController                                     │   │
│  └──────────────────┬──────────────────────────────────┘   │
│                     │                                        │
│  ┌──────────────────▼──────────────────────────────────┐   │
│  │ Service Layer                                        │   │
│  │ - UserService                                       │   │
│  │ - GameService                                       │   │
│  │ - DLCService                                        │   │
│  └──────────────────┬──────────────────────────────────┘   │
│                     │                                        │
│  ┌──────────────────▼──────────────────────────────────┐   │
│  │ Repository Layer (Hibernate)                         │   │
│  │ - UserRepository                                    │   │
│  │ - GameRepository                                    │   │
│  │ - DLCRepository                                     │   │
│  └──────────────────┬──────────────────────────────────┘   │
└─────────────────────┼────────────────────────────────────────┘
                      │
                      │ JDBC
                      │
        ┌─────────────▼──────────────┐
        │   PostgreSQL 16 (Port 5432)│
        │  ┌──────────────────────┐  │
        │  │ 5 Tabellen           │  │
        │  │ - users              │  │
        │  │ - games              │  │
        │  │ - dlcs               │  │
        │  │ - user_owned_games   │  │
        │  │ - user_owned_dlcs    │  │
        │  └──────────────────────┘  │
        └────────────────────────────┘

        ┌────────────────────────────┐
        │   MCP Server (Node.js)     │
        │  ┌──────────────────────┐  │
        │  │ Database Tools       │  │
        │  │ - query_database     │  │
        │  │ - list_tables        │  │
        │  │ - get_table_schema   │  │
        │  │ - execute_write      │  │
        │  └──────────────────────┘  │
        └────────────────────────────┘
```

## Layer-Beschreibung

### 1. Backend-Layer (Java)

**Technologie:** Java 17+, Spring Boot, Hibernate

**Komponenten:**

#### Controller Layer
- RESTful API-Endpunkte
- Request/Response Verarbeitung
- Input-Validierung

#### Service Layer
- Business-Logik
- Datentransformation
- Cross-cutting Concerns (Authentifizierung, Logging)

#### Repository Layer (Hibernate)
- ORM-Mapping zu Java-Objekten
- Datenbankabstraktionen
- Query-Optimierung

### 2. Datenbank-Layer (PostgreSQL)

**Features:**
- ACID-Transaktionen
- Normalisiertes Schema
- Foreign Keys für Integrität
- Indizes für Performance

**Schema:**

```sql
-- Benutzer
CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR UNIQUE NOT NULL,
  email VARCHAR,
  password VARCHAR,
  firstname VARCHAR,
  lastname VARCHAR
);

-- Spiele
CREATE TABLE games (
  id VARCHAR PRIMARY KEY,
  titel VARCHAR,
  description TEXT,
  price DOUBLE PRECISION NOT NULL,
  release_date TIMESTAMP,
  created_at TIMESTAMP NOT NULL
);

-- DLCs
CREATE TABLE dlcs (
  id VARCHAR PRIMARY KEY,
  dlc_name VARCHAR,
  game_title VARCHAR,
  description TEXT,
  price DOUBLE PRECISION NOT NULL,
  release_date TIMESTAMP,
  created_at TIMESTAMP NOT NULL
);

-- Benutzer-Spiel-Zuordnungen
CREATE TABLE user_owned_games (
  user_id BIGINT REFERENCES users(id),
  game_id VARCHAR REFERENCES games(id),
  PRIMARY KEY (user_id, game_id)
);

-- Benutzer-DLC-Zuordnungen
CREATE TABLE user_owned_dlcs (
  user_id BIGINT REFERENCES users(id),
  dlc_id VARCHAR REFERENCES dlcs(id),
  PRIMARY KEY (user_id, dlc_id)
);
```

### 3. MCP-Server (Node.js)

**Model Context Protocol Implementation**

**Tools:**
- `query_database` - SELECT/INSERT/UPDATE/DELETE Abfragen
- `list_tables` - Tabellenverzeichnis
- `get_table_schema` - Spalten-Information
- `execute_write` - Sichere Schreiboperationen

**Verbindung:** PostgreSQL-Client (pg Library)

## Datenfluss

### Beispiel: Spiel abrufen

```
1. Client sendet HTTP GET /games/123
   ↓
2. GameController.getGame(123)
   ↓
3. GameService.getGameById(123)
   ↓
4. GameRepository.findById(123) (Hibernate)
   ↓
5. SQL SELECT * FROM games WHERE id = '123'
   ↓
6. PostgreSQL führt Query aus
   ↓
7. Ergebnis zu Java-Objekt gemappt
   ↓
8. Service verarbeitet Daten
   ↓
9. Controller serialisiert zu JSON
   ↓
10. HTTP 200 + JSON Response an Client
```

## Skalierung & Performance

### Datenbankoptimierungen

**Indizes:**
```sql
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_games_id ON games(id);
CREATE INDEX idx_user_owned_games_user_id ON user_owned_games(user_id);
```

**Connection Pooling:**
- Hibernate nutzt HikariCP
- Konfigurierbar in `application.properties`

### Caching-Strategien

**L1-Cache:** Hibernate Session-Cache
**L2-Cache:** Konfigurierbar (z.B. Redis, Ehcache)

## Sicherheit

### Authentifizierung
- Passwort-Hashing (bcrypt empfohlen)
- JWT-Tokens für Session-Management

### Autorisierung
- Role-Based Access Control (RBAC)
- Resource-Level Security

### Datenschutz
- SQL-Injection Prevention (Prepared Statements)
- CORS-Konfiguration
- TLS für Datentransport (Produktion)

## Deployment-Struktur

### Docker/Podman Compose

```yaml
services:
  db:
    # PostgreSQL 16
    # Port: 5432
    # Volume: postgres_data:/var/lib/postgresql/data
    
  backend:
    # Java Application
    # Port: 8080
    # Abhängig von: db
    
  mcp-server:
    # Node.js MCP Server
    # Abhängig von: db
```

## Migrationen

**Flyway oder Liquibase** für Schema-Verwaltung

**Migrations-Ordner:** `db/migrations/`

**Namenskonvention:** `V<version>__<description>.sql`

Beispiel:
```
V1__initial_schema.sql
V2__add_unique_constraint_to_username.sql
```

## Entwicklungsumgebung

### Lokale Tools

```bash
# Node.js MCP-Server lokal starten
cd mcp
npm install
npm start

# Datenbankverbindung testen
psql -h localhost -U postgres -d app
```

### IDE-Konfiguration

**IntelliJ IDEA:**
1. Project SDK auf Java 17+ setzen
2. Gradle Sync ausführen
3. Database-Tool mit PostgreSQL verbinden

## Best Practices

1. **Separaten Daenbankuser pro Umgebung verwenden**
2. **Migrationen immer in Git committen**
3. **Tests für Service-Layer schreiben**
4. **Logging auf mehreren Levels verwenden**
5. **Performance-Tests für kritische Queries**

## Nächste Schritte

- Siehe [MCP-SERVER.de.md](MCP-SERVER.de.md) für MCP-Details
- Siehe [API.de.md](API.de.md) für API-Referenz
- Siehe [INSTALLATION.de.md](INSTALLATION.de.md) für Setup
