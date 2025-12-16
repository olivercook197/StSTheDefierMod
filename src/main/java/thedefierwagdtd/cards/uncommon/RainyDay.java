package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.RainyDayPower;
import thedefierwagdtd.powers.UnfazedPower;
import thedefierwagdtd.util.CardStats;

public class RainyDay extends BaseCard {
    public static final String ID = makeID(RainyDay.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            AbstractCard.CardType.POWER,
            AbstractCard.CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            1
    );

    public RainyDay() {
        super(ID, info);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new RainyDayPower(p, 1), 1));
        if (upgraded) {
            addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p, 1, false), 1));
            addToBot(new ApplyPowerAction(p, p, new UnfazedPower(p, 1), 1));
        }
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
