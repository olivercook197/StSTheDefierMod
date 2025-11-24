package thedefierwagdtd.powers;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;



public class RainyDayPower extends BasePower {
    public static final String POWER_ID = makeID(RainyDayPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private static final int THRESHOLD = 3;
    private int counter = 0;

    public RainyDayPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, null, amount);
        this.amount = amount;
        updateDescription();
    }

    public void trigger(AbstractPower appliedPower) {
        if (appliedPower.owner == AbstractDungeon.player && appliedPower.ID.equals(VulnerablePower.POWER_ID)) {
            counter++;
            flash();

            if (counter >= THRESHOLD) {
                counter = 0;
                addToBot(new DrawCardAction(AbstractDungeon.player, this.amount));
            }
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
        updateDescription();
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
        else{
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        Color stackColor = Color.GREEN.cpy();
        stackColor.a = c.a;

        Color counterColor = Color.GREEN.cpy();
        counterColor.a = c.a;

        BitmapFont font = FontHelper.powerAmountFont;

        FontHelper.renderFontRightTopAligned(
                sb, font, Integer.toString(this.amount),
                x - 30.0F * Settings.scale, y,
                this.fontScale, stackColor
        );

        FontHelper.renderFontRightTopAligned(
                sb, font, Integer.toString(counter),
                x, y,
                this.fontScale, counterColor
        );
    }
}


