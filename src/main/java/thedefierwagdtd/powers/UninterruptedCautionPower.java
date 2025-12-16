package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class UninterruptedCautionPower extends BasePower {
    public static final String POWER_ID = makeID(UninterruptedCautionPower.class.getSimpleName());

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public UninterruptedCautionPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void stackPower(int stackAmount) {
        this.amount = 1;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
