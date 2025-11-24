package thedefierwagdtd.relics;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
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

    public AncestralCall() {
        super(ID, TIER, SOUND);
    }

    public boolean canSpawn() {
        return AbstractDungeon.player instanceof TheDefier;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {

        if (target == AbstractDungeon.player && power.ID.equals(StrengthPower.POWER_ID) && power.amount > 0) {

            flash();

            int amount = power.amount;

            addToBot(new RelicAboveCreatureAction(target, this));
            addToBot(new ApplyPowerAction(target, target, new LionsHeartBuff(target, amount), amount));
        }

        return true;
    }
}