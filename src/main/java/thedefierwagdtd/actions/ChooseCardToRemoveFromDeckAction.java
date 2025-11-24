package thedefierwagdtd.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class ChooseCardToRemoveFromDeckAction extends AbstractGameAction {

    public ChooseCardToRemoveFromDeckAction(int amount) {
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.isEmpty()) {
                isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(
                    "permanently remove from your deck.",
                    this.amount,
                    false, // not any number
                    false  // must pick at least one
            );

            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractDungeon.player.masterDeck.removeCard(c.cardID);
                c.superFlash();
                AbstractDungeon.effectList.add(
                        new com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect(c)
                );
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c));
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            isDone = true;
        }

        tickDuration();
    }
}
