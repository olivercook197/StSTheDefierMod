package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thedefierwagdtd.CustomTags.CustomTag;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class FangsBaredPower extends BasePower {
    public static final String POWER_ID = makeID(FangsBaredPower.class.getSimpleName());

    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public FangsBaredPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card != null
                && card.hasTag(CustomTag.RECKLESS)
                && type == DamageInfo.DamageType.NORMAL) {

            return damage + this.amount;
        }
        return damage;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(CustomTag.RECKLESS)) {
            flash();
            addToBot((AbstractGameAction)new DamageRandomEnemyAction(new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS),
                    AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
