package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class ShadowDancerPower extends BasePower {
    public static final String POWER_ID = makeID(ShadowDancerPower.class.getSimpleName());

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ShadowDancerPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void onCardDraw(AbstractCard c) {
        applyToCard(c);
    }

    private void applyToCard(AbstractCard c) {
        if (isPlayableCurse(c)) {
            c.setCostForTurn(c.cost + this.amount);
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (isPlayableCurse(card)) {
            flash();
            addToBot(new GainEnergyAction(this.amount));
            addToBot(new DrawCardAction(this.amount));
        }
    }

    private boolean isPlayableCurse(AbstractCard c) {
        return c.type == AbstractCard.CardType.CURSE && c.cost >= 0;
    }

    public void updateDescription() {
        StringBuilder repeated = new StringBuilder();

        for (int i = 0; i < this.amount; i++) {
            repeated.append(DESCRIPTIONS[2]);
        }

        if (this.amount == 1) {
            this.description =
                    DESCRIPTIONS[0]
                            + repeated
                            + DESCRIPTIONS[1]
                            + repeated
                            + DESCRIPTIONS[3]
                            + this.amount
                            + DESCRIPTIONS[4];
        } else {
            this.description =
                    DESCRIPTIONS[0]
                            + repeated
                            + DESCRIPTIONS[1]
                            + repeated
                            + DESCRIPTIONS[3]
                            + this.amount
                            + DESCRIPTIONS[5];
        }
    }
}
