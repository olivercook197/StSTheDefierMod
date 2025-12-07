package thedefierwagdtd.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.CautionPower;
import thedefierwagdtd.util.CardStats;

import static thedefierwagdtd.CustomTags.CustomTag.RECKLESS;

public class SmellTheirFear extends BaseCard {
    public static final String ID = makeID(SmellTheirFear.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );
    private static final int MAGIC_NUMBER = 3;
    private static final int UPG_MAGIC_NUMBER = 1;


    public SmellTheirFear() {
        super(ID, info);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int recklessCards = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c != this && c.hasTag(RECKLESS)) {
                recklessCards += 1;
            }
        }
        for (int i = 0; i < recklessCards; i++) {
            addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                    (AbstractPower)new StrengthPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
            addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                    (AbstractPower)new LoseStrengthPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
        }

        addToTop((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                (AbstractPower)new CautionPower((AbstractCreature)p, 2), 2));

        if (upgraded) {
            addToTop((AbstractGameAction)new DrawCardAction((AbstractCreature)p, 1));
        }
    }
}
