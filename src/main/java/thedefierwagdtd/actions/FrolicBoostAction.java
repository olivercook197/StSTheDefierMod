package thedefierwagdtd.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class FrolicBoostAction extends AbstractGameAction {
    private final AbstractCard card;
    private final int amount;

    public FrolicBoostAction(AbstractCard card, int amount) {
        this.card = card;
        this.amount = amount;
    }

    @Override
    public void update() {
        boolean changed = false;

        // Boost damage if the card has any
        if (card.baseDamage > 0) {
            card.baseDamage += amount;
            changed = true;
        }

        // Boost block if the card has any
        if (card.baseBlock > 0) {
            card.baseBlock += amount;
            changed = true;
        }

        if (changed) {
            card.applyPowers();
            card.initializeDescription();
            card.superFlash();
        }

        this.isDone = true;
    }
}
