package thedefierwagdtd.cards.common;

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
import thedefierwagdtd.powers.CautionPower;
import thedefierwagdtd.util.CardStats;

import static thedefierwagdtd.CustomTags.CustomTag.RECKLESS;

public class FearScent extends BaseCard {
    public static final String ID = makeID(FearScent.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );
    private static final int MAGIC_NUMBER = 4;
    private static final int UPG_MAGIC_NUMBER = 2;


    public FearScent() {
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

        if (recklessCards > 0) {
            addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                    (AbstractPower)new CautionPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
        }

        addToTop((AbstractGameAction)new DrawCardAction((AbstractCreature)p, 1));
    }

    @Override
    public void triggerOnGlowCheck() {
        AbstractPlayer p = AbstractDungeon.player;

        if (p != null && p.hand.group.stream().anyMatch(c -> c.hasTag(RECKLESS))) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
}
