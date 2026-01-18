package thedefierwagdtd.actions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class AddCopyExhaustEtherealCost extends AbstractCardModifier {

    private static final String ID = "thedefierwagdtd:AddCopyExhaustEtherealCost";
    private final int amount;

    public AddCopyExhaustEtherealCost(int amount) {
        this.amount = amount;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.updateCost(amount);
        card.isCostModifiedForTurn = true;

        if (!card.isEthereal) {
            card.isEthereal = true;
            card.exhaust = true;
            card.selfRetain = false;

            card.rawDescription += " NL Ethereal. Exhaust.";
            card.initializeDescription();
        }
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return card.cost != -1;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new AddCopyExhaustEtherealCost(amount);
    }
}

