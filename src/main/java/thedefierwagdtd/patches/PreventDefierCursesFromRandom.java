package thedefierwagdtd.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thedefierwagdtd.cards.curses.*;

import java.util.HashSet;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "returnRandomCurse"
)
public class PreventDefierCursesFromRandom {

    private static final HashSet<String> DEFIER_CURSES = new HashSet<>();

    static {
        DEFIER_CURSES.add(Burned.ID);
        DEFIER_CURSES.add(Choked.ID);
        DEFIER_CURSES.add(Crushed.ID);
        DEFIER_CURSES.add(Exploded.ID);
        DEFIER_CURSES.add(Humiliated.ID);
        DEFIER_CURSES.add(Mauled.ID);
        DEFIER_CURSES.add(Plummeted.ID);
        DEFIER_CURSES.add(Trampled.ID);
    }

    @SpirePostfixPatch
    public static AbstractCard replace(AbstractCard __result) {

        // If vanilla curse, allow it
        if (!DEFIER_CURSES.contains(__result.cardID)) {
            return __result;
        }

        // Otherwise reroll until non-Defier curse
        AbstractCard replacement;
        do {
            replacement = AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE).makeCopy();
        } while (DEFIER_CURSES.contains(replacement.cardID));

        return replacement;
    }
}
