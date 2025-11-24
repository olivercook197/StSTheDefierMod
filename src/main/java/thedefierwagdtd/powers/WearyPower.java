package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;


public class WearyPower extends BasePower {
    public static final String POWER_ID = makeID(WearyPower.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;

    private static final boolean TURN_BASED = true;

    private static final int EFFECTIVENESS_STRING = 15;

    public WearyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.priority = 99;
    }

    public void atEndOfRound() {
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * 0.85F;
        }
        return damage;
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + EFFECTIVENESS_STRING + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + EFFECTIVENESS_STRING + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3];
        }
    }
}
