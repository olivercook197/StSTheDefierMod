package thedefierwagdtd.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import java.util.ArrayList;

public class ChooseCardToIncreaseCost extends AbstractGameAction {
    private final int amount;

    private final ArrayList<AbstractCard> removedCards = new ArrayList<>();

    public ChooseCardToIncreaseCost(int amount) {
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {

        // --- OPEN SELECTION ---
        if (this.duration == Settings.ACTION_DUR_FAST) {

            CardGroup hand = AbstractDungeon.player.hand;

            // Remove ineligible cards BEFORE opening selection screen
            for (AbstractCard c : new ArrayList<>(hand.group)) {
                if (!isEligible(c)) {
                    removedCards.add(c);
                    hand.group.remove(c);
                }
            }

            // If nothing selectable → restore and exit
            if (hand.isEmpty() || amount <= 0) {
                restoreExcludedCards();
                isDone = true;
                return;
            }

            int maxSelect = Math.min(amount, hand.size());

            AbstractDungeon.handCardSelectScreen.open(
                    "increase cost",
                    maxSelect,
                    false,
                    false,
                    false,
                    false,
                    false
            );

            tickDuration();
            return;
        }

        // --- PROCESS SELECTION ---
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {

                c.costForTurn += 1;
                if (c.costForTurn < 0)
                    c.costForTurn = 0;

                c.isCostModifiedForTurn = true;

                c.superFlash(Color.RED.cpy());
                AbstractDungeon.player.hand.addToTop(c);
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();

            // Restore removed cards
            restoreExcludedCards();

            isDone = true;
        }

        tickDuration();
    }

    private boolean isEligible(AbstractCard c) {
        // Unplayable cards (Curse/Status or cost == -2)
        if (c.cost == -2) return false;

        // Optional: forbid Curses or Status
        if (c.type == AbstractCard.CardType.CURSE) return false;
        if (c.type == AbstractCard.CardType.STATUS) return false;

        return true;
    }

    private void restoreExcludedCards() {
        AbstractDungeon.player.hand.group.addAll(removedCards);
        removedCards.clear();
    }
}
