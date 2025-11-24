package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.BackAlleyPower;
import thedefierwagdtd.powers.BalancedCalmPower;
import thedefierwagdtd.util.CardStats;

public class BackAlley extends BaseCard {
    public static final String ID = makeID(BackAlley.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            AbstractCard.CardType.POWER,
            AbstractCard.CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            0
    );
    private static final int MAGIC_NUMBER = 1;
    private static final int MAGIC_NUMBER_UPG = 1;

    public BackAlley() {
        super(ID, info);
        setMagic(MAGIC_NUMBER, MAGIC_NUMBER_UPG);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                (AbstractPower)new BackAlleyPower((AbstractCreature)p, this.magicNumber),
                this.magicNumber));
    }
}
