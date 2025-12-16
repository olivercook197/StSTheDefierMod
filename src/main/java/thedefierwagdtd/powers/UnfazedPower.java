package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class UnfazedPower extends BasePower {
    public static final String POWER_ID = makeID(UnfazedPower.class.getSimpleName());

    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = true;

    public UnfazedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void atEndOfRound() {
        if (this.amount == 0) {
            addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        } else {
            addToBot((AbstractGameAction)new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
    }

    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL && owner.hasPower(VulnerablePower.POWER_ID)) {
            if (owner instanceof AbstractPlayer && ((AbstractPlayer) owner).hasRelic("Odd Mushroom")) {
                damage /= 1.25f;
            } else {
                damage /= 1.5f;
            }
        }
        return damage;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}

