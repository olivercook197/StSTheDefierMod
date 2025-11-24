package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class NapPower extends BasePower {

    public static final String POWER_ID = makeID(NapPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    private int turnCounter = 0;
    private final int blockAmount;
    private final int weakAmount;
    private final int tempStrength;

    public NapPower(AbstractCreature owner, int block, int weak, int tempStrength) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
        this.blockAmount = block;
        this.weakAmount = weak;
        this.tempStrength = tempStrength;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        turnCounter++;

        AbstractPlayer p = AbstractDungeon.player;

        // Turn 1
        if (turnCounter == 1) {
            addToBot(new ApplyPowerAction(p, p, new NoDrawPower(p)));

            addToBot(new GainBlockAction(p, p, blockAmount));

            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDeadOrEscaped()) {
                    addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, weakAmount, false), weakAmount));
                }
            }

            addToBot((AbstractGameAction)new PressEndTurnButtonAction());
        }

        // Turn 2
        else if (turnCounter == 2) {
            int healAmount = 0;
            AbstractPower heart = p.getPower(LionsHeartBuff.POWER_ID);
            if (heart != null) {
                healAmount = heart.amount / 2;
            }

            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, tempStrength), tempStrength));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, tempStrength), tempStrength));
            addToBot(new HealAction(p, p, healAmount));

            addToBot(new RemoveSpecificPowerAction(p, p, POWER_ID));
        }

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (turnCounter == 0) {
            this.description = DESCRIPTIONS[0] + blockAmount +
                    DESCRIPTIONS[1] + weakAmount + DESCRIPTIONS[2];
        } else if (turnCounter == 1) {
            this.description = DESCRIPTIONS[0] + tempStrength +
                    DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[4];
        }
    }
}
