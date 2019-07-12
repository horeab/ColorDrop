package libgdx.screens.game;

import libgdx.preferences.PreferencesService;


public class StoreService {

    private static String SHARED_PREFS_NAME = "ColorDropStore";

    private PreferencesService preferencesService = new PreferencesService(SHARED_PREFS_NAME);

    private static String GAMES_PLAYED = "GAMES_PLAYED";
    private static String RECORD_SCORE = "RECORD_SCORE";


    public int getGamesPlayed() {
        return preferencesService.getPreferences().getInteger(GAMES_PLAYED, 0);
    }

    public int getRecordScore() {
        return preferencesService.getPreferences().getInteger(RECORD_SCORE, 0);
    }

    public void incrementGamesPlayed() {
        putValue(GAMES_PLAYED, getGamesPlayed() + 1);
    }

    public void putRecordScore(int score) {
        putValue(RECORD_SCORE, score);
    }

    private void putValue(String fieldName, boolean value) {
        preferencesService.putBoolean(fieldName, value);
    }

    private void putValue(String fieldName, int value) {
        preferencesService.putInteger(fieldName, value);
    }
}
