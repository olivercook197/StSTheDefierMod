package thedefierwagdtd.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.ui.FtueTip;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.DefierTutorialTracker;
import thedefierwagdtd.util.TheDefierTutorial;

@SpirePatch(
        clz = MonsterRoom.class,
        method = "onPlayerEntry"
)
public class DefierTutorialPatch {

    @SpirePostfixPatch
    public static void Postfix() {

        if (!(AbstractDungeon.player instanceof TheDefier)) return;
        if (DefierTutorialTracker.hasSeen()) return;
        if (AbstractDungeon.isScreenUp) return;

        AbstractDungeon.ftue = new TheDefierTutorial();

        DefierTutorialTracker.markSeen();
    }
}



