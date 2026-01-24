package thedefierwagdtd.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.CautionPower;
import thedefierwagdtd.powers.UninterruptedCautionPower;
import thedefierwagdtd.util.CardStats;

public class CautiousImpulse extends BaseCard {
    public static final String ID = makeID(CautiousImpulse.class.getSimpleName());

    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.RARE,
            AbstractCard.CardTarget.SELF,
            0
    );

    private static final int MAGIC_NUMBER = 6;

    public CautiousImpulse() {
        super(ID, info);
        setMagic(MAGIC_NUMBER);
        this.exhaust = true;

        tags.add(CustomTag.RECKLESS);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                (AbstractPower)new CautionPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
        addToTop((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                (AbstractPower)new UninterruptedCautionPower((AbstractCreature)p, 0), 0));
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isInnate = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
