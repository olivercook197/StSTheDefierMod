package thedefierwagdtd.util;

import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.helpers.Prefs;

public class DefierTutorialTracker {

    private static final String PREF_KEY = "TheDefierTutorialSeen";
    private static Prefs prefs;

    public static void init() {
        prefs = SaveHelper.getPrefs("TheDefierTutorial");
    }

    public static boolean hasSeen() {
        return prefs.getBoolean(PREF_KEY, false);
    }

    public static void markSeen() {
        prefs.putBoolean(PREF_KEY, true);
        prefs.flush();
    }
}
