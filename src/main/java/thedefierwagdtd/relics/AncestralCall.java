package thedefierwagdtd.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.LionsHeartBuff;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class AncestralCall extends BaseRelic implements OnApplyPowerRelic {
    public static final String ID = makeID(AncestralCall.class.getSimpleName());
    private static final RelicTier TIER = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int MAX_PER_COMBAT = 50;

    public AncestralCall() {
        super(ID, TIER, SOUND);
        this.counter = 0;
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player instanceof TheDefier;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {

        if (target == AbstractDungeon.player
                && power.ID.equals(StrengthPower.POWER_ID)
                && power.amount > 0
                && this.counter < MAX_PER_COMBAT) {

            int remaining = MAX_PER_COMBAT - this.counter;
            int amountToApply = Math.min(power.amount, remaining);

            if (amountToApply <= 0) {
                return true;
            }

            flash();
            this.counter += amountToApply;

            addToBot(new RelicAboveCreatureAction(target, this));
            addToBot(new ApplyPowerAction(
                    target,
                    target,
                    new LionsHeartBuff(target, amountToApply),
                    amountToApply
            ));
        }

        return true;
    }
}
