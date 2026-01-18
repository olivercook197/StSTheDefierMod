package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import static thedefierwagdtd.TheDefierModWAGDTD.makeID;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thedefierwagdtd.relics.Deaths;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class EdgeOfDeathPower extends BasePower{
    public static final String POWER_ID = makeID(EdgeOfDeathPower.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public EdgeOfDeathPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.priority = 99;
    }

    @Override
    public void onInitialApplication() {
        AbstractRelic deaths = AbstractDungeon.player.getRelic(Deaths.RELIC_ID);

        if (deaths == null) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                    Settings.WIDTH / 2f,
                    Settings.HEIGHT / 2f,
                    new Deaths()
            );
        } else {
            if (deaths.counter < 9) {
                ((Deaths) deaths).incrementCounter();
                deaths.flash();
            }
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
