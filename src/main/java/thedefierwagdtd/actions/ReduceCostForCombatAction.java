package thedefierwagdtd.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ReduceCostForCombatAction extends AbstractGameAction {

    private AbstractPlayer p;
    private int decreaseAmount;

    public ReduceCostForCombatAction(int decreaseAmount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.decreaseAmount = decreaseAmount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            boolean betterPossible = false;
            boolean possible = false;

            for(AbstractCard c : this.p.hand.group) {
                if (c.costForTurn > 0) {
                    betterPossible = true;
                } else if (c.cost > 0) {
                    possible = true;
                }
            }

            if (betterPossible || possible) {
                this.findAndModifyCard(betterPossible, decreaseAmount);
            }
        }

        this.tickDuration();
    }

    private void findAndModifyCard(boolean better, int decreaseAmount) {
        AbstractCard c = this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
        if (better) {
            if (c.costForTurn > 0) {
                c.cost -= decreaseAmount;
                c.costForTurn -= decreaseAmount;
                c.isCostModified = true;
                c.superFlash(Color.GOLD.cpy());
            } else {
                this.findAndModifyCard(better, decreaseAmount);
            }
        } else if (c.cost > 0) {
            c.cost -= decreaseAmount;
            c.costForTurn -= decreaseAmount;
            c.isCostModified = true;
            c.superFlash(Color.GOLD.cpy());
        } else {
            this.findAndModifyCard(better, decreaseAmount);
        }

    }
}