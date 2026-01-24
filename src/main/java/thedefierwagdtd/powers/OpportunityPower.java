package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thedefierwagdtd.CustomTags.CustomTag;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class OpportunityPower extends BasePower {

    public static final String POWER_ID = makeID(OpportunityPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public OpportunityPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void trigger(AbstractPower appliedPower, AbstractCreature target) {
        if (!VulnerablePower.POWER_ID.equals(appliedPower.ID)) {
            return;
        }

        if (target.hasPower(ArtifactPower.POWER_ID)
                && target.getPower(ArtifactPower.POWER_ID).amount > 0) {
            return;
        }

        if (target.isDeadOrEscaped()) {
            return;
        }

        flash();

        int totalDamage = appliedPower.amount * this.amount;

        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped()) {
                addToBot(new DamageAction(
                        m,
                        new DamageInfo(owner, totalDamage, DamageInfo.DamageType.THORNS),
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
                ));
            }
        }
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}