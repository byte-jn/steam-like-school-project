# REST API Dokumentation

Vollständige Referenz für die REST-API des Steam-Like Schulprojekts.

## Basis-Information

**Basis-URL:** `http://localhost:8080`

**Format:** JSON

**Authentifizierung:** Bearer Token (Standard)

## Allgemeine Response-Struktur

### Erfolgreiche Response (200)

```json
{
  "data": {
    "id": "...",
    "...": "..."
  },
  "status": "success"
}
```

### Fehlresponse

```json
{
  "error": {
    "message": "Beschreibung des Fehlers",
    "code": "ERROR_CODE"
  },
  "status": "error"
}
```

## User-Endpoints

### Benutzerliste abrufen

```http
GET /api/users
```

**Query Parameter:**
- `limit` (int, optional) - Anzahl der Ergebnisse (Standard: 20)
- `offset` (int, optional) - Offset für Pagination (Standard: 0)
- `search` (string, optional) - Nach Benutzername suchen

**Response:**
```json
{
  "data": [
    {
      "id": 1,
      "username": "alice",
      "email": "alice@example.com",
      "firstname": "Alice",
      "lastname": "Smith"
    }
  ],
  "total": 1,
  "status": "success"
}
```

**Status-Codes:**
- `200` - OK
- `400` - Ungültige Parameter

### Benutzer abrufen

```http
GET /api/users/{id}
```

**Path Parameter:**
- `id` (long) - Benutzer-ID

**Response:**
```json
{
  "data": {
    "id": 1,
    "username": "alice",
    "email": "alice@example.com",
    "firstname": "Alice",
    "lastname": "Smith"
  },
  "status": "success"
}
```

**Status-Codes:**
- `200` - OK
- `404` - Benutzer nicht gefunden

### Benutzer erstellen

```http
POST /api/users
```

**Request Body:**
```json
{
  "username": "newuser",
  "email": "newuser@example.com",
  "password": "securepassword",
  "firstname": "New",
  "lastname": "User"
}
```

**Response:** `201 Created`
```json
{
  "data": {
    "id": 2,
    "username": "newuser",
    "email": "newuser@example.com"
  },
  "status": "success"
}
```

**Status-Codes:**
- `201` - Erstellt
- `400` - Ungültige Eingabe
- `409` - Benutzername existiert bereits

### Benutzer aktualisieren

```http
PUT /api/users/{id}
```

**Request Body:**
```json
{
  "email": "newemail@example.com",
  "firstname": "Updated"
}
```

**Response:** `200 OK`

**Status-Codes:**
- `200` - OK
- `400` - Ungültige Eingabe
- `404` - Benutzer nicht gefunden

### Benutzer löschen

```http
DELETE /api/users/{id}
```

**Response:** `204 No Content`

**Status-Codes:**
- `204` - Gelöscht
- `404` - Benutzer nicht gefunden

## Game-Endpoints

### Spiele abrufen

```http
GET /api/games
```

**Query Parameter:**
- `limit` (int, optional) - Anzahl der Ergebnisse
- `offset` (int, optional) - Offset für Pagination
- `minPrice` (double, optional) - Minimumpreis Filter
- `maxPrice` (double, optional) - Maximumpreis Filter
- `search` (string, optional) - Nach Titel suchen

**Response:**
```json
{
  "data": [
    {
      "id": "game-001",
      "titel": "Awesome Game",
      "description": "An amazing adventure",
      "price": 39.99,
      "releaseDate": "2023-06-15T00:00:00Z",
      "createdAt": "2026-05-19T10:00:00Z"
    }
  ],
  "total": 1,
  "status": "success"
}
```

**Beispiel - Spiele unter 20€:**
```http
GET /api/games?maxPrice=20&limit=10
```

### Spiel abrufen

```http
GET /api/games/{id}
```

**Response:**
```json
{
  "data": {
    "id": "game-001",
    "titel": "Awesome Game",
    "description": "An amazing adventure",
    "price": 39.99,
    "releaseDate": "2023-06-15T00:00:00Z",
    "createdAt": "2026-05-19T10:00:00Z"
  },
  "status": "success"
}
```

### Spiel erstellen

```http
POST /api/games
```

**Request Body:**
```json
{
  "id": "game-new-001",
  "titel": "New Game",
  "description": "Description of the game",
  "price": 29.99,
  "releaseDate": "2026-06-15T00:00:00Z"
}
```

**Response:** `201 Created`

### Spiel aktualisieren

```http
PUT /api/games/{id}
```

**Request Body:**
```json
{
  "price": 19.99,
  "description": "Updated description"
}
```

**Response:** `200 OK`

### Spiel löschen

```http
DELETE /api/games/{id}
```

**Response:** `204 No Content`

## DLC-Endpoints

### DLCs abrufen

```http
GET /api/dlcs
```

**Query Parameter:**
- `gameId` (string, optional) - Nur DLCs für ein Spiel
- `limit` (int, optional) - Anzahl der Ergebnisse
- `offset` (int, optional) - Offset

**Response:**
```json
{
  "data": [
    {
      "id": "dlc-001",
      "dlcName": "Expansion Pack",
      "gameTitle": "Awesome Game",
      "description": "Additional content",
      "price": 9.99,
      "createdAt": "2026-05-19T10:00:00Z"
    }
  ],
  "total": 1,
  "status": "success"
}
```

### DLC abrufen

```http
GET /api/dlcs/{id}
```

### DLC erstellen

```http
POST /api/dlcs
```

**Request Body:**
```json
{
  "id": "dlc-new-001",
  "dlcName": "New DLC",
  "gameTitle": "Awesome Game",
  "description": "New DLC content",
  "price": 14.99
}
```

### DLC aktualisieren

```http
PUT /api/dlcs/{id}
```

### DLC löschen

```http
DELETE /api/dlcs/{id}
```

## Benutzer-Spiel Endpoints

### Spiele eines Benutzers abrufen

```http
GET /api/users/{userId}/games
```

**Response:**
```json
{
  "data": [
    {
      "id": "game-001",
      "titel": "Awesome Game",
      "price": 39.99
    }
  ],
  "total": 1,
  "status": "success"
}
```

### Spiel kaufen (hinzufügen)

```http
POST /api/users/{userId}/games
```

**Request Body:**
```json
{
  "gameId": "game-001"
}
```

**Response:** `201 Created`

**Status-Codes:**
- `201` - Spiel hinzugefügt
- `400` - Ungültige Game-ID
- `409` - Benutzer besitzt das Spiel bereits

### Spiel entfernen

```http
DELETE /api/users/{userId}/games/{gameId}
```

**Response:** `204 No Content`

## Benutzer-DLC Endpoints

### DLCs eines Benutzers abrufen

```http
GET /api/users/{userId}/dlcs
```

### DLC kaufen

```http
POST /api/users/{userId}/dlcs
```

**Request Body:**
```json
{
  "dlcId": "dlc-001"
}
```

### DLC entfernen

```http
DELETE /api/users/{userId}/dlcs/{dlcId}
```

## Health-Check Endpoint

### Server-Status überprüfen

```http
GET /health
```

**Response:** `200 OK`
```json
{
  "status": "UP"
}
```

## Fehler-Codes

| Code | Status | Beschreibung |
|------|--------|-------------|
| `INVALID_INPUT` | 400 | Ungültige Eingabedaten |
| `RESOURCE_NOT_FOUND` | 404 | Ressource existiert nicht |
| `CONFLICT` | 409 | Ressource existiert bereits |
| `INTERNAL_ERROR` | 500 | Interner Serverfehler |
| `UNAUTHORIZED` | 401 | Authentifizierung erforderlich |
| `FORBIDDEN` | 403 | Zugriff verweigert |

## Beispiele mit curl

### Alle Spiele abrufen

```bash
curl -X GET http://localhost:8080/api/games
```

### Spiel nach Preis filtern

```bash
curl -X GET "http://localhost:8080/api/games?maxPrice=25&limit=5"
```

### Neues Spiel erstellen

```bash
curl -X POST http://localhost:8080/api/games \
  -H "Content-Type: application/json" \
  -d '{
    "id": "game-new",
    "titel": "New Game",
    "price": 29.99
  }'
```

### Benutzer erstellen

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "user@example.com",
    "password": "secure123",
    "firstname": "New",
    "lastname": "User"
  }'
```

### Spiel kaufen

```bash
curl -X POST http://localhost:8080/api/users/1/games \
  -H "Content-Type: application/json" \
  -d '{
    "gameId": "game-001"
  }'
```

## Authentifizierung

### Login

```http
POST /api/auth/login
```

**Request Body:**
```json
{
  "username": "alice",
  "password": "password123"
}
```

**Response:**
```json
{
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIs...",
    "expiresIn": 3600
  },
  "status": "success"
}
```

### Geschützte Endpoints verwenden

```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer YOUR_TOKEN"
```

## Rate Limiting

**Standard:** 100 Anfragen pro Minute pro IP

**Headers:**
```
X-RateLimit-Limit: 100
X-RateLimit-Remaining: 95
X-RateLimit-Reset: 1621500000
```

## Pagination

Alle List-Endpoints unterstützen Pagination:

```http
GET /api/games?limit=20&offset=40
```

- `limit`: Anzahl der Ergebnisse (max. 100)
- `offset`: Startposition (Standard: 0)

**Response:**
```json
{
  "data": [...],
  "total": 1000,
  "limit": 20,
  "offset": 40,
  "status": "success"
}
```

## Testing der API

### Mit Postman

1. Basis-URL importieren: `http://localhost:8080`
2. Endpoints in Sammlungen organisieren
3. Tokens in Umgebungsvariablen speichern
4. Tests für automatisiertes Testen schreiben

### Mit PowerShell

```powershell
$response = Invoke-RestMethod -Uri http://localhost:8080/api/games `
  -Method Get
$response.data | Format-Table
```

## Nächste Schritte

- Siehe [MCP-SERVER.de.md](MCP-SERVER.de.md) für Datenbankoperationen
- Siehe [ARCHITEKTUR.de.md](ARCHITEKTUR.de.md) für System-Design
- Siehe [INSTALLATION.de.md](INSTALLATION.de.md) für Setup-Anleitung
