package thedefierwagdtd.util;

import java.util.HashSet;
import thedefierwagdtd.cards.curses.*;

public class DefierCurseHelper {
    public static final HashSet<String> REQUIRED_CURSES = new HashSet<>();

    static {
        REQUIRED_CURSES.add(Burned.ID);
        REQUIRED_CURSES.add(Choked.ID);
        REQUIRED_CURSES.add(Crushed.ID);
        REQUIRED_CURSES.add(Exploded.ID);
        REQUIRED_CURSES.add(Humiliated.ID);
        REQUIRED_CURSES.add(Mauled.ID);
        REQUIRED_CURSES.add(Plummeted.ID);
        REQUIRED_CURSES.add(Trampled.ID);
    }
}
