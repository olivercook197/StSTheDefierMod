package thedefierwagdtd.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import javassist.CtBehavior;
import thedefierwagdtd.relics.EndOfActBounty;
import thedefierwagdtd.ui.campfire.EndOfActBountyOption;

import java.util.ArrayList;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class AddEndOfActBountyCampfireOptionPatch {
    @SpirePatch(clz = CampfireUI.class, method = "initializeButtons")
    public static class AddKeys {
        @SpireInsertPatch(locator = AddEndOfActBountyCampfireOptionPatch.Locator.class)
        public static void patch(CampfireUI __instance, ArrayList<AbstractCampfireOption> ___buttons) {
            Boolean active = Boolean.valueOf(true);

            if (AbstractDungeon.player.hasRelic(makeID(EndOfActBounty.class.getSimpleName()))) {
                ___buttons.add(new EndOfActBountyOption());
            }
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
            return LineFinder.findInOrder(ctBehavior, (Matcher)fieldAccessMatcher);
        }
    }
}
