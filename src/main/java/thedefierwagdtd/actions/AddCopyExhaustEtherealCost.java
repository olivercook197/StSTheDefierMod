package thedefierwagdtd.actions;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class AddCopyExhaustEtherealCost extends AbstractCardModifier {

    int amount;

    public AddCopyExhaustEtherealCost(int amount) {
        this.amount = amount;
    }

    public void onUpdate(AbstractCard card) {
        if (!card.isEthereal) {
            card.isEthereal = true;
            card.exhaust = true;
            card.selfRetain = false;
            card.rawDescription = card.rawDescription + " NL Ethereal. Exhaust.";
            card.initializeDescription();
        }
    }

    public boolean shouldApply(AbstractCard card) {
        return (card.cost != -1);
    }

    public void onInitialApplication(AbstractCard card) {
        if (card.freeToPlayOnce) {
            card.freeToPlayOnce = false;
            card.setCostForTurn(1);
        } else {
            card.updateCost(this.amount);
        }
    }

    public String identifier(AbstractCard card) {
        return "thedefierwagdtd:AddCopyExhaustEtherealCost";
    }

    public AbstractCardModifier makeCopy() {
        return new AddCopyExhaustEtherealCost(this.amount);
    }
}
