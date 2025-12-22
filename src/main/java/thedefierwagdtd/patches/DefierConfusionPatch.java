package thedefierwagdtd.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import thedefierwagdtd.character.TheDefier;

@SpirePatch(
        clz = ConfusionPower.class,
        method = "onCardDraw"
)
public class DefierConfusionPatch {

    @SpirePrefixPatch
    public static SpireReturn<Void> prefix(ConfusionPower __instance, AbstractCard c) {

        if (AbstractDungeon.player instanceof TheDefier) {

            int newCost = AbstractDungeon.cardRandomRng.random(0, 2);

            c.setCostForTurn(newCost);

            return SpireReturn.Return(null);
        }

        return SpireReturn.Continue();
    }
}