package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class CloseTheGapPower extends BasePower {
    public static final String POWER_ID = makeID(CloseTheGapPower.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = true;

    public CloseTheGapPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void atStartOfTurnPostDraw() {
        flash();
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(
                        mo, owner,
                        new StrengthPower(mo, -1), -1,
                        true, AbstractGameAction.AttackEffect.NONE
                ));
                addToBot(new ApplyPowerAction(
                        mo, owner,
                        new CustomDexterity(mo, -1), -1,
                        true, AbstractGameAction.AttackEffect.NONE
                ));
            }
        }
        this.amount--;
        if (this.amount <= 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        } else {
            updateDescription();
        }
    }

    public void updateDescription() {
        if (this.amount > 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[3];
        }
    }
}
