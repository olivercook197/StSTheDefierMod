package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.BalancedCalmPower;
import thedefierwagdtd.powers.HeartfadePower;
import thedefierwagdtd.powers.WearyPower;
import thedefierwagdtd.util.CardStats;

public class BorrowedTime extends BaseCard {
    public static final String ID = makeID(BorrowedTime.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            AbstractCard.CardType.POWER,
            AbstractCard.CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            0
    );
    private static final int MAGIC_NUMBER = 3;
    private static final int MAGIC_NUMBER_UPG = -3;

    public BorrowedTime() {
        super(ID, info);
        setMagic(MAGIC_NUMBER, MAGIC_NUMBER_UPG);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded){
            addToBot(new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                    (AbstractPower)new WearyPower((AbstractCreature)p, 1),
                    1));
        }
        addToBot(new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                (AbstractPower)new HeartfadePower((AbstractCreature)p, 1),
                1));
    }
}
