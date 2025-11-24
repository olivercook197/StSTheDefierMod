package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class BloodOfKingsPower extends BasePower {
    public static final String POWER_ID = makeID(BloodOfKingsPower.class.getSimpleName());

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public BloodOfKingsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void onVictory() {

        AbstractPlayer p = AbstractDungeon.player;

        AbstractPower vulnerable = p.getPower(VulnerablePower.POWER_ID);
        int vulnerableAmount = (vulnerable == null ? 0 : vulnerable.amount);

        int healAmount = vulnerableAmount * this.amount;

        if (healAmount > 0) {
            owner.heal(healAmount);
        }

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
