package thedefierwagdtd.cards.curses;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.util.CardStats;

public class Mauled extends BaseCard {
    public static final String ID = makeID(Mauled.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.CURSE,
            CardType.CURSE,
            CardRarity.CURSE,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 5;

    public Mauled() {
        super(ID, info);
        setMagic(MAGIC);
        tags.add(CustomTag.RECKLESS);
        tags.add(CustomTag.DEATH_CURSE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) p, (AbstractCreature) p,
                (AbstractPower) new StrengthPower((AbstractCreature) p, this.magicNumber), this.magicNumber));
        addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) p, (AbstractCreature) p,
                (AbstractPower) new LoseStrengthPower((AbstractCreature) p, this.magicNumber + 1), this.magicNumber + 1));
    }

    public boolean canSpawn() {
        return false;
    }
}
