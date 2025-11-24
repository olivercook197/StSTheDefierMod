package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.cards.tempCards.Nourishment;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.AgilityPower;
import thedefierwagdtd.powers.DinDinsPower;
import thedefierwagdtd.powers.UnphasedPower;
import thedefierwagdtd.util.CardStats;

public class DinDins extends BaseCard {
    public static final String ID = makeID(DinDins.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            AbstractCard.CardType.POWER,
            AbstractCard.CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            1
    );
    private static final int MAGIC_NUMBER = 1;

    public DinDins() {
        super(ID, info);
        setMagic(MAGIC_NUMBER);

        this.cardsToPreview = new Nourishment();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                (AbstractPower)new DinDinsPower((AbstractCreature)p, this.magicNumber),
                this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
