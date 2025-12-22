package thedefierwagdtd.cards.basic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.RetainEnergyPower;
import thedefierwagdtd.util.CardStats;

public class Surge extends BaseCard {
    public static final String ID = makeID(Surge.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1
    );
    private static final int MAGIC_NUMBER = 3;
    private static final int UPG_MAGIC_NUMBER = 1;

    public Surge() {
        super(ID, info);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, 1));
        addToBot(new GainEnergyAction(this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new RetainEnergyPower(p), 1));
    }
}
