package thedefierwagdtd.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import javassist.CtBehavior;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.ui.campfire.CleanseOption;

import java.util.ArrayList;

@SpirePatch(
        clz = CampfireUI.class,
        method = "initializeButtons"
)
public class DefierCleanseCampfirePatch {

    @SpireInsertPatch(locator = Locator.class)
    public static void insert(CampfireUI __instance, ArrayList<AbstractCampfireOption> ___buttons) {

        if (AbstractDungeon.player instanceof TheDefier) {
            ___buttons.add(new CleanseOption());
        }
    }


    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            return LineFinder.findInOrder(
                    ctBehavior,
                    new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics")
            );
        }
    }
}
