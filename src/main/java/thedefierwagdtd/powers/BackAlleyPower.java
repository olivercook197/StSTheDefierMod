package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class BackAlleyPower extends BasePower {
    public static final String POWER_ID = makeID(BackAlleyPower.class.getSimpleName());

    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    private int cardsReducedThisTurn = 0;

    public BackAlleyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atStartOfTurn() {
        cardsReducedThisTurn = 0;
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (cardsReducedThisTurn < this.amount) {
            if (card.costForTurn > 0) {
                flash();
                addToTop(new ReduceCostForTurnAction(card, 1));
                cardsReducedThisTurn++;
            }
            else {
                flash();
                cardsReducedThisTurn++;
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        cardsReducedThisTurn = 0;
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[3];
        } else {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        }
    }
}
