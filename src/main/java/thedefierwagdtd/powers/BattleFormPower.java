package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class BattleFormPower extends BasePower {
    public static final String POWER_ID = makeID(BattleFormPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private static final int DIVISOR = 8;

    public BattleFormPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    public void atStartOfTurn() {
        AbstractPlayer p = AbstractDungeon.player;

        AbstractPower lionsHeart = p.getPower(LionsHeartBuff.POWER_ID);
        int lhAmount = (lionsHeart == null ? 0 : lionsHeart.amount);

        int powerGain = (int) Math.floor((float) lhAmount * this.amount / DIVISOR);

        if (powerGain > 0) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, powerGain), powerGain));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, powerGain), powerGain));
        }

        updateDescription();
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target == AbstractDungeon.player && power.ID.equals(LionsHeartBuff.POWER_ID)) {
            updateDescription();
        }
    }

    public void onInitialApplication() {
        updateDescription();
    }

    public void onRemove() {
        updateDescription();
    }

    public void updateDescription() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractPower lionsHeart = p != null ? p.getPower(LionsHeartBuff.POWER_ID) : null;
        int lhAmount = (lionsHeart == null ? 0 : lionsHeart.amount);
        int powerGain = (int) Math.floor((float) lhAmount * this.amount / DIVISOR);

        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + powerGain + DESCRIPTIONS[2];
    }
}
