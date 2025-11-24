package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.badlogic.gdx.math.MathUtils.floor;
import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class HeartfadePower extends BasePower{
    public static final String POWER_ID = makeID(HeartfadePower.class.getSimpleName());

    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public HeartfadePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void onEnergyRecharge() {

        AbstractPower lionsheart = owner.getPower(LionsHeartBuff.POWER_ID);
        int lionsheartamount = (lionsheart == null ? 0 : lionsheart.amount);

        if (lionsheartamount < 1) {
            return;
        }
        else if (lionsheartamount < amount) {
            addToBot(new ApplyPowerAction((AbstractCreature) AbstractDungeon.player, (AbstractCreature) AbstractDungeon.player,
                    (AbstractPower) new LionsHeartBuff((AbstractCreature) AbstractDungeon.player, -lionsheartamount), -lionsheartamount));
            addToBot((AbstractGameAction) new DrawCardAction(lionsheartamount));
        }
        else {
            addToBot(new ApplyPowerAction((AbstractCreature) AbstractDungeon.player, (AbstractCreature) AbstractDungeon.player,
                    (AbstractPower) new LionsHeartBuff((AbstractCreature) AbstractDungeon.player, -this.amount), -this.amount));
            addToBot((AbstractGameAction) new DrawCardAction(this.amount));
        }
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
        else{
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3];
        }
    }
}
