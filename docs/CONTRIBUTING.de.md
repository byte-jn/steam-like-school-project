# Beitrag zum Projekt

Richtlinien und Best Practices für die Mitarbeit am Steam-Like Schulprojekt.

## Erste Schritte

### Repository klonen

```bash
git clone https://github.com/your-org/steam-like-school-project.git
cd steam-like-school-project
```

### Feature-Branch erstellen

```bash
# Branch vom aktuellen Stand erstellen
git checkout -b feature/deine-feature-beschreibung

# Oder für Bugfixes
git checkout -b fix/dein-bugfix
```

**Branch-Namenskonvention:**
- `feature/` - Neue Features
- `fix/` - Bugfixes
- `docs/` - Dokumentation
- `refactor/` - Code-Verbesserungen
- `test/` - Test-Erweiterungen

## Lokale Entwicklung

### Services starten

```bash
podman-compose up -d
```

### MCP-Server lokal starten (optional)

```bash
cd mcp
npm install
npm start
```

### Backend-Änderungen testen

```bash
# Gradle Build
./gradlew build

# Tests ausführen
./gradlew test
```

## Code-Standards

### Java Backend

**Namenskonvention:**
```java
// Klassen: PascalCase
public class UserService { }

// Methoden: camelCase
public User getUserById(Long id) { }

// Konstanten: UPPER_SNAKE_CASE
public static final int DEFAULT_PAGE_SIZE = 20;
```

**Formatierung:**
- 4 Spaces Indentation
- Max. 120 Zeichen pro Zeile
- Keine wildcard imports

**Dokumentation:**
```java
/**
 * Ruft einen Benutzer anhand seiner ID ab.
 *
 * @param id die Benutzer-ID
 * @return den gefundenen Benutzer oder Optional.empty()
 * @throws EntityNotFoundException wenn Benutzer nicht existiert
 */
public User getUserById(Long id) { }
```

### JavaScript / Node.js

**Namenskonvention:**
```javascript
// Konstanten
const DB_CONNECTION_TIMEOUT = 5000;

// Funktionen
function queryDatabase(sql) { }

// Klassen
class MCPServer { }
```

**Formatierung:**
- 2 Spaces Indentation
- Semicolons verwenden
- `const` vor `let` vor `var`

### SQL Migrationen

**Dateiname:**
```
V<version>__<beschreibung>.sql
V1__initial_schema.sql
V2__add_unique_constraint_to_username.sql
```

**Struktur:**
```sql
-- Kommentar für die Änderung
-- Falls erforderlich: Daten-Migrationen einschließen

ALTER TABLE users ADD CONSTRAINT uk_username UNIQUE (username);
```

## Git Workflow

### Commits

**Format:**
```
<typ>: <kurze Beschreibung>

<ausführliche Beschreibung, falls erforderlich>
```

**Typen:**
- `feat:` - Neue Feature
- `fix:` - Bugfix
- `docs:` - Dokumentation
- `refactor:` - Code-Umstrukturierung
- `test:` - Test-Erweiterungen
- `chore:` - Build-Prozess, Dependencies

**Beispiele:**
```bash
git commit -m "feat: add user authentication with JWT"
git commit -m "fix: prevent SQL injection in game search"
git commit -m "docs: add MCP server documentation in German"
```

### Pull Request (PR) Prozess

**1. Aktuelle Änderungen pushen**
```bash
git push origin feature/deine-feature
```

**2. PR im GitHub erstellen**
- Aussagekräftigen Titel verwenden
- Beschreibung mit Context schreiben
- Related Issues verlinken

**3. PR-Vorlage verwenden**
```markdown
## Beschreibung
Was ändert diese PR?

## Type of Change
- [ ] Bug-Fix
- [ ] Neue Feature
- [ ] Breaking Change

## Testing
Wie wurde das getestet?

## Checklist
- [ ] Code läuft lokal
- [ ] Tests bestanden
- [ ] Dokumentation aktualisiert
```

**4. Reviews und Iterationen**
- Auf Feedback reagieren
- Commits hinzufügen, nicht neu squashen
- Conversation auflösen

**5. Merge**
```bash
# Nach Approval mergen
git checkout master
git pull origin master
git merge origin/feature/deine-feature
git push origin master
```

## Testing

### Unit Tests schreiben

```java
@Test
public void testGetUserById_UserExists_ReturnsUser() {
    // Arrange
    User expected = new User(1L, "alice", "alice@example.com");
    when(userRepository.findById(1L)).thenReturn(Optional.of(expected));
    
    // Act
    User actual = userService.getUserById(1L);
    
    // Assert
    assertEquals(expected, actual);
    verify(userRepository).findById(1L);
}
```

### Integration Tests

```java
@SpringBootTest
@DataJpaTest
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void testFindByUsername() {
        // Test mit echtem DB-Context
    }
}
```

### Tests ausführen

```bash
# Alle Tests
./gradlew test

# Spezifische Test-Klasse
./gradlew test --tests UserServiceTest

# Mit Coverage
./gradlew test jacocoTestReport
```

## Dokumentation

### Wenn du Features hinzufügst

1. **API-Dokumentation aktualisieren**
   - [API.de.md](API.de.md) ändern
   - Beispiele hinzufügen

2. **Architektur-Dokumentation**
   - [ARCHITEKTUR.de.md](ARCHITEKTUR.de.md) aktualisieren falls relevant

3. **Inline-Dokumentation**
   - Komplexe Logik kommentieren
   - JavaDoc für öffentliche APIs

### Dokumentation schreiben

**Markdown Best Practices:**
```markdown
# Überschrift 1
## Überschrift 2

**Fett** für Wichtiges
`code` für Inline-Code

\`\`\`java
// Code-Blöcke mit Sprache
\`\`\`
```

## Code Review Checkliste

Bevor du einen PR stellst:

- [ ] Code läuft lokal ohne Fehler
- [ ] Tests bestanden alle
- [ ] Keine Console-Fehler oder Warnings
- [ ] Code folgt Projekt-Standards
- [ ] Dokumentation aktualisiert
- [ ] Commit-Messages sind aussagekräftig
- [ ] Keine sensiblen Daten committed (.env, Passwords, API-Keys)
- [ ] Keine Breaking Changes ohne Ankündigung

## Häufige Aufgaben

### Datenbankschema ändern

1. **Migration erstellen**
```bash
# Neue Migration-Datei
touch db/migrations/V3__your_change.sql
```

2. **SQL schreiben**
```sql
-- db/migrations/V3__add_release_date_to_games.sql
ALTER TABLE games ADD COLUMN release_date TIMESTAMP;
```

3. **Hibernate-Entity aktualisieren**
```java
@Entity
public class Game {
    // ...
    @Column(name = "release_date")
    private LocalDateTime releaseDate;
}
```

4. **Tests schreiben**
```java
@Test
public void testNewColumn() {
    // Test die neue Spalte
}
```

### API-Endpoint hinzufügen

1. **Entity aktualisieren** (falls nötig)
2. **Repository erstellen/erweitern**
3. **Service schreiben**
4. **Controller hinzufügen**
5. **Tests schreiben**
6. **Dokumentation aktualisieren**

### Bug beheben

1. **Test schreiben, der Bug reproduziert**
```java
@Test
public void testBugName_Condition_ExpectedBehavior() {
    // Dieser Test sollte zuerst fehlschlagen
}
```

2. **Bug fixen**
3. **Test sollte nun bestehen**
4. **Regression-Tests hinzufügen**

## Performance-Überlegungen

### Queries optimieren

```java
// ❌ Schlecht: N+1 Problem
List<User> users = userRepository.findAll();
users.forEach(u -> u.getGames().size()); // N separate Queries

// ✅ Gut: Eager Loading
@Query("SELECT u FROM User u JOIN FETCH u.games")
List<User> findAllWithGames();
```

### Datenbankindizes

```sql
-- Index für häufige Queries
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_games_price ON games(price);
```

## Security-Überlegungen

- [ ] Keine SQL-Injection möglich (Prepared Statements)
- [ ] Input-Validierung durchgeführt
- [ ] Authentifizierung/Autorisierung vorhanden
- [ ] Sensible Daten sind verschlüsselt
- [ ] CORS richtig konfiguriert
- [ ] Rate Limiting aktiv

## Troubleshooting

### Build fehlgeschlagen

```bash
# Cache löschen
./gradlew clean

# Neu builden
./gradlew build
```

### Tests fehlgeschlagen

```bash
# Einzelnen Test debuggen
./gradlew test --tests ClassName.methodName -i

# Debug-Output
./gradlew test -d
```

### Git-Konflikt

```bash
# Konflikt anzeigen
git status

# Manuell auflösen und dann
git add <datei>
git commit -m "Konflikt aufgelöst"
```

## Community-Richtlinien

- 👉 Respektvolles Feedback geben und nehmen
- 📚 Anderen helfen zu lernen
- 🤝 Code Reviews konstruktiv gestalten
- 🐛 Bugs promptly reportieren
- 📝 Issues und Discussions nutzen für Diskussionen

## Hilfe und Fragen

- **Fragen zum Code:** Erstelle eine Issue oder diskutiere in PR Comments
- **Projekt-Fragen:** Siehe [README.de.md](README.de.md)
- **Setup-Probleme:** Siehe [INSTALLATION.de.md](INSTALLATION.de.md)

## Danke! 🎉

Vielen Dank für deinen Beitrag zum Projekt!
