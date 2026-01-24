package thedefierwagdtd.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.CautionPower;
import thedefierwagdtd.powers.CustomDexterity;
import thedefierwagdtd.util.CardStats;

public class Dupe extends BaseCard {
    public static final String ID = makeID(Dupe.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int MAGIC_NUMBER = 1;
    private static final int CAUTION_AMOUNT = 3;

    public Dupe() {
        super(ID, info);
        setMagic(MAGIC_NUMBER);
        this.misc = CAUTION_AMOUNT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)p,
                (AbstractPower)new CustomDexterity((AbstractCreature)m, -this.magicNumber), -this.magicNumber));
        addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) m, (AbstractCreature) p,
                (AbstractPower) new StrengthPower((AbstractCreature) m, -this.magicNumber), -this.magicNumber));

        if (upgraded) {
            addToBot(new ApplyPowerAction(p, p, new CautionPower(p, this.misc), this.misc));
        }
    }
}
