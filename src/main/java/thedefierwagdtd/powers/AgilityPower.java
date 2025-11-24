package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class AgilityPower extends BasePower {
    public static final String POWER_ID = makeID(AgilityPower.class.getSimpleName());

    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public AgilityPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        AbstractPower vulnerable = AbstractDungeon.player.getPower(VulnerablePower.POWER_ID);
        int amount = (vulnerable == null ? 0 : vulnerable.amount) * this.amount;
        flash();
        addToBot((AbstractGameAction)new GainBlockAction(this.owner, this.owner, amount));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
