package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.badlogic.gdx.math.MathUtils.floor;
import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class LionsHeartBuff extends BasePower {
    public static final String POWER_ID = makeID(LionsHeartBuff.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public LionsHeartBuff(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.priority = 99;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + floor((float) this.amount /8) + DESCRIPTIONS[2];
    }

    public void onVictory() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth > 0)
            p.heal(floor((float) this.amount /8));
    }
}
