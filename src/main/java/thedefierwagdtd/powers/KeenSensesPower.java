package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import thedefierwagdtd.CustomTags.CustomTag;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class KeenSensesPower extends BasePower {
    public static final String POWER_ID = makeID(KeenSensesPower.class.getSimpleName());

    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public KeenSensesPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.priority = 99;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.CURSE) {
            flash();
            addToTop((AbstractGameAction)new GainBlockAction(owner, this.amount));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
