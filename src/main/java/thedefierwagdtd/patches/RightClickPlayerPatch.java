package thedefierwagdtd.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import thedefierwagdtd.actions.ReduceCostForCombatAction;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.LionsHeartBuff;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "update"
)
public class RightClickPlayerPatch {

    @SpirePostfixPatch
    public static void postfix(AbstractPlayer __instance) {
        if (!(__instance instanceof TheDefier)) return;
        if (!InputHelper.justClickedRight) return;
        if (!__instance.hb.hovered) return;
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) return;

        if (AbstractDungeon.isScreenUp) return;

        handleRightClick(__instance);
    }

    private static void handleRightClick(AbstractPlayer p) {

        AbstractPower lionsHeart = p.getPower(LionsHeartBuff.POWER_ID);
        if (lionsHeart == null || lionsHeart.amount < 15) {
            return;
        }

        AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(p, p, lionsHeart, 15)
        );

        if (!p.hand.isEmpty()) {
            AbstractDungeon.actionManager.addToBottom(
                    new ReduceCostForCombatAction( 1)
            );
        }
    }
}
