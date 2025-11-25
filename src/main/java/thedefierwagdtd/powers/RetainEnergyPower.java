package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class RetainEnergyPower extends BasePower {
    public static final String POWER_ID = makeID(RetainEnergyPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public RetainEnergyPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer) return;

        int leftover = EnergyPanel.totalCount;

        if (leftover > 0) {
            flash();
            addToBot(new ApplyPowerAction(
                    owner,
                    owner,
                    new ClowderPower(owner, leftover),
                    leftover
            ));
        }

        addToBot(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
