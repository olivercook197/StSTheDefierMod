package thedefierwagdtd.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ChooseCardToDecreaseCostAndGainDamage extends AbstractGameAction {
    private final int damageIncrease;
    private final int costDecrease;

    public ChooseCardToDecreaseCostAndGainDamage(int damageIncrease, int costDecrease) {
        this.damageIncrease = damageIncrease;
        this.costDecrease = costDecrease;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer p = AbstractDungeon.player;

            if (p.hand.isEmpty()) {
                isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(
                    "decrease cost and increase damage.",
                    1,
                    false,
                    false
            );

            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {

                if (c.cost >= 0 && c.costForTurn >= 0) {
                    c.modifyCostForCombat(-costDecrease);
                    c.superFlash(Color.GOLD.cpy());
                }

                c.baseDamage += damageIncrease;
                c.isDamageModified = true;

                c.applyPowers();
                AbstractDungeon.player.hand.addToTop(c);
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            isDone = true;
        }

        tickDuration();
    }
}
