package thedefierwagdtd.cards.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ScrapeEffect;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.cards.common.FerociousSwipe;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.CardStats;

public class FerociousSwipeCopy extends BaseCard {
    public static final String ID = makeID(FerociousSwipeCopy.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );
    private static final int DAMAGE = 2;
    private static final int DAMAGE_UPG = 1;
    private static final int MAGIC_NUMBER = 2;

    public FerociousSwipeCopy() {
        super(ID, info);
        setMagic(MAGIC_NUMBER);
        setDamage(DAMAGE, DAMAGE_UPG);
        tags.add(CustomTag.RECKLESS);

        setExhaust(true);
        setEthereal(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null)
            addToBot(new VFXAction(new ScrapeEffect(m.hb.cX, m.hb.cY), 0.1F));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (m != null)
            addToBot(new VFXAction(new ScrapeEffect(m.hb.cX, m.hb.cY), 0.1F));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));

        AbstractCard clone = this.makeStatEquivalentCopy();

        clone.cost++;
        clone.costForTurn = clone.cost;
        clone.isCostModified = true;

        clone.isEthereal = true;
        clone.exhaust = true;

        clone.keywords.add("Ethereal");
        clone.keywords.add("Exhaust");

        clone.rawDescription = clone.rawDescription + " NL Ethereal. NL Exhaust.";
        clone.initializeDescription();

        addToBot(new MakeTempCardInHandAction(clone, 1));
    }
}