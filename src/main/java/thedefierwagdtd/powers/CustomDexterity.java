package thedefierwagdtd.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class CustomDexterity extends BasePower {
    public static final String POWER_ID = makeID(CustomDexterity.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;

    private static final boolean TURN_BASED = false;

    public CustomDexterity(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.loadRegion("dexterity");
        this.canGoNegative = true;
        updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;

        if (this.amount == 0) {
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }

        updateDescription();
    }

    public float modifyBlock(float blockAmount) {
        return Math.max(0, blockAmount + this.amount);
    }


    public void updateDescription() {
        if (this.amount > 0) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
        else {
            this.description = DESCRIPTIONS[1] + Math.abs(this.amount) + DESCRIPTIONS[2];
        }
    }

    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        Color renderColor = this.amount < 0 ? Color.RED.cpy() : c;
        FontHelper.renderFontRightTopAligned(
                sb,
                FontHelper.powerAmountFont,
                Integer.toString(this.amount),
                x,
                y,
                this.fontScale,
                renderColor
        );
    }
}
