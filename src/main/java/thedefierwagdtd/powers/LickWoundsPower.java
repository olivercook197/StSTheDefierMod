package thedefierwagdtd.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import thedefierwagdtd.actions.ChooseCardToRetainAndDiscountAction;
import thedefierwagdtd.cards.uncommon.LickWounds;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class LickWoundsPower extends BasePower {
    public static final String POWER_ID = makeID(LickWoundsPower.class.getSimpleName());

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public LickWoundsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void onVictory() {
        int missingHealth = owner.maxHealth - owner.currentHealth;

        int healAmount = (int)Math.floor(missingHealth * (amount / 100.0f));

        if (healAmount > 0) {
            owner.heal(healAmount);
        }

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}
