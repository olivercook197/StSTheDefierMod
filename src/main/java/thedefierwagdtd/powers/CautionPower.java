package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class CautionPower extends BasePower {
    public static final String POWER_ID = makeID(CautionPower.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public CautionPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (damage <= 0) return damage;

        float reductionMultiplier = 1.0f - (this.amount / 100.0f);
        return damage * Math.max(0f, reductionMultiplier);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);

        if (this.amount <= 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        } else {
            updateDescription();
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (owner.hasPower(UninterruptedCautionPower.POWER_ID)) {
            return;
        }

        flash();
        this.amount--;

        if (this.amount <= 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        } else {
            updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    @Override
    public void reducePower(int reduceAmount) {
        this.amount -= reduceAmount;

        if (this.amount <= 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        } else {
            updateDescription();
        }
    }

    public int modifyRecklessDamage(int damage) {
        int reduced = damage - this.amount;
        return Math.max(0, reduced);
    }
}