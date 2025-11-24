package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class BalancedCalmPower extends BasePower {
    public static final String POWER_ID = makeID(BalancedCalmPower.class.getSimpleName());

    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public BalancedCalmPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.priority = 99;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL) {
            flash();
            addToBot(new ApplyPowerAction((AbstractCreature) AbstractDungeon.player, (AbstractCreature) AbstractDungeon.player,
                    (AbstractPower) new LionsHeartBuff((AbstractCreature) AbstractDungeon.player, this.amount), this.amount));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
