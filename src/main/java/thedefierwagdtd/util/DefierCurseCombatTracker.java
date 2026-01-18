package thedefierwagdtd.util;

import java.util.HashSet;

public class DefierCurseCombatTracker {
    public static final HashSet<String> playedThisCombat = new HashSet<>();
    public static boolean powerApplied = false;

    public static void reset() {
        playedThisCombat.clear();
        powerApplied = false;
    }
}
