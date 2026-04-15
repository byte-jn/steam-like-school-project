package Service;

import Entites.DLC;
import Entites.Games;
import Entites.User;

import java.util.List;

/**
 * Stellt zentrale Suchfunktionen für Benutzer, Spiele und DLCs bereit.
 *
 * @author Jannis Lauer (jannis280@outlook.de)
 */
public final class LookupService {
    /**
     * Verhindert Instanziierung der Utility-Klasse.
     */
    private LookupService() {
    }

    /**
     * Sucht einen Benutzer anhand seines Benutzernamens.
     *
     * @param users Benutzerliste
     * @param username gesuchter Benutzername
     * @return gefundener Benutzer oder {@code null}
     */
    public static User findUserByUsername(List<User> users, String username) {
        if (username == null) {
            return null;
        }

        String normalized = username.trim();
        for (User currentUser : users) {
            if (currentUser.getUsername() != null && currentUser.getUsername().equalsIgnoreCase(normalized)) {
                return currentUser;
            }
        }
        return null;
    }

    /**
     * Sucht ein Spiel anhand seiner ID.
     *
     * @param games Spieleliste
     * @param gameId gesuchte Spiel-ID
     * @return gefundenes Spiel oder {@code null}
     */
    public static Games findGameById(List<Games> games, String gameId) {
        if (gameId == null) {
            return null;
        }

        String normalized = gameId.trim();
        for (Games game : games) {
            if (game.getId().equals(normalized)) {
                return game;
            }
        }
        return null;
    }

    /**
     * Sucht ein Spiel anhand seines Titels.
     *
     * @param games Spieleliste
     * @param title gesuchter Titel
     * @return gefundenes Spiel oder {@code null}
     */
    public static Games findGameByTitle(List<Games> games, String title) {
        if (title == null) {
            return null;
        }

        String normalized = title.trim();
        for (Games game : games) {
            if (game.getTitel() != null && game.getTitel().equalsIgnoreCase(normalized)) {
                return game;
            }
        }
        return null;
    }

    /**
     * Sucht ein DLC anhand seiner ID.
     *
     * @param dlcs DLC-Liste
     * @param dlcId gesuchte DLC-ID
     * @return gefundenes DLC oder {@code null}
     */
    public static DLC findDlcById(List<DLC> dlcs, String dlcId) {
        if (dlcId == null) {
            return null;
        }

        String normalized = dlcId.trim();
        for (DLC dlc : dlcs) {
            if (dlc.getId().equals(normalized)) {
                return dlc;
            }
        }
        return null;
    }
}

