package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class SmoulderingPower extends BasePower {
    public static final String POWER_ID = makeID(SmoulderingPower.class.getSimpleName());

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public SmoulderingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 999)
            this.amount = 999;
    }

    public void onEnergyRecharge() {
        flash();
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)owner, (AbstractCreature)owner,
                (AbstractPower)new DexterityPower((AbstractCreature)owner, this.amount), this.amount));
        addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
