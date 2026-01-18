package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class SalubriousShinePower extends BasePower {
    public static final String POWER_ID = makeID(SalubriousShinePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public SalubriousShinePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.priority = 1;
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        flash();
        owner.increaseMaxHp(1, true);
        addToBot((AbstractGameAction)new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        if (this.amount == 0) {
            addToBot((AbstractGameAction) new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
