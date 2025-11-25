package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.WhiskerWalkerPower;
import thedefierwagdtd.util.CardStats;

public class WhiskerWalker extends BaseCard {
    public static final String ID = makeID(WhiskerWalker.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            AbstractCard.CardType.POWER,
            AbstractCard.CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            1
    );

    private static final int MAGIC_NUMBER = 3;
    private static final int MAGIC_NUMBER_UPG = 2;

    public WhiskerWalker() {
        super(ID, info);
        setMagic(MAGIC_NUMBER, MAGIC_NUMBER_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                (AbstractPower)new WhiskerWalkerPower((AbstractCreature)p, this.magicNumber),
                this.magicNumber));
    }
}
