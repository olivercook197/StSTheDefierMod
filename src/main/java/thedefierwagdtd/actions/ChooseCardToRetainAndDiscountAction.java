package thedefierwagdtd.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ChooseCardToRetainAndDiscountAction extends AbstractGameAction {
    private final int amount;

    public ChooseCardToRetainAndDiscountAction(int amount) {
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.isEmpty() || amount <= 0) {
                isDone = true;
                return;
            }

            int selectable = Math.min(amount, AbstractDungeon.player.hand.size());
            if (selectable <= 0) {
                isDone = true;
                return;
            }

            String message = "retain & reduce cost";

            AbstractDungeon.handCardSelectScreen.open(
                    message,
                    selectable,
                    true,  //AnyNumber
                    true              //CanPickZero
            );

            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                c.retain = true;
                if (c.cost > 0) {
                    c.modifyCostForCombat(-1);
                }
                c.superFlash();

                // FIX: put the card back into the hand
                AbstractDungeon.player.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            isDone = true;
        }

        tickDuration();
    }
}
