package thedefierwagdtd.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.LionsHeartBuff;
import thedefierwagdtd.powers.SalubriousShinePower;
import thedefierwagdtd.util.CardStats;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class SalubriousShine extends BaseCard {
    public static final String ID = makeID(SalubriousShine.class.getSimpleName());

    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.RARE,
            AbstractCard.CardTarget.SELF,
            1
    );

    private static final int MAGIC_NUMBER = 3;
    private static final int UPG_MAGIC_NUMBER = 2;

    public SalubriousShine() {
        super(ID, info);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p, new SalubriousShinePower(p,this.magicNumber)));
    }
}
