package thedefierwagdtd.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.ui.FtueTip;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.DefierConfusionTipSave;

@SpirePatch(
        clz = AbstractPower.class,
        method = "onInitialApplication"
)
public class DefierConfusionTipPatch {

    private static final UIStrings uiStrings =
            CardCrawlGame.languagePack.getUIString("thedefierwagdtd:DefierConfusionTip");

    public static void Postfix(AbstractPower __instance) {
        
        if (!(__instance instanceof ConfusionPower)) return;

        if (AbstractDungeon.player == null) return;
        if (!(AbstractDungeon.player instanceof TheDefier)) return;

        if (DefierConfusionTipSave.shown) return;

        DefierConfusionTipSave.shown = true;

        AbstractDungeon.ftue = new FtueTip(
                uiStrings.TEXT[0],
                uiStrings.TEXT[1],
                Settings.WIDTH / 2f,
                Settings.HEIGHT / 2f,
                FtueTip.TipType.COMBAT
        );
    }
}
