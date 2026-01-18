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
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.BloodOfKingsPower;
import thedefierwagdtd.util.CardStats;

public class BloodOfKings extends BaseCard {
    public static final String ID = makeID(BloodOfKings.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            AbstractCard.CardType.POWER,
            AbstractCard.CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            1
    );
    private static final int MAGIC_NUMBER = 2;

    public BloodOfKings() {
        super(ID, info);
        tags.add(CardTags.HEALING);
        setMagic(MAGIC_NUMBER);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                (AbstractPower)new BloodOfKingsPower((AbstractCreature)p, this.magicNumber),
                this.magicNumber));
        if (upgraded) {
            addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                    (AbstractPower)new VulnerablePower((AbstractCreature)p, 4, false), 4));
        }
    }
}
