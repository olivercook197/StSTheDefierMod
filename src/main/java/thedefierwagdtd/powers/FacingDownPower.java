package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import thedefierwagdtd.actions.ChooseCardToRetainAndDiscountAction;
import thedefierwagdtd.cards.uncommon.FacingDown;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class FacingDownPower extends BasePower{
    public static final String POWER_ID = makeID(FacingDownPower.class.getSimpleName());

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public FacingDownPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            addToBot(new ChooseCardToRetainAndDiscountAction(this.amount));
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }

    public void updateDescription() {
        if (this.amount > 1) {this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];}
        else{
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
    }
}
