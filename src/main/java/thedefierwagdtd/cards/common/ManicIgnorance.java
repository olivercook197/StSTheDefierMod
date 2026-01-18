package thedefierwagdtd.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.actions.FrolicBoostAction;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.CardStats;

public class ManicIgnorance extends BaseCard {
    public static final String ID = makeID(ManicIgnorance.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            3
    );
    private static final int BLOCK = 18;
    private static final int BLOCK_UPG = 8;
    private static final int MAGIC_NUMBER = 3;

    public ManicIgnorance() {
        super(ID, info);
        setBlock(BLOCK, BLOCK_UPG);
        setMagic(MAGIC_NUMBER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyEffects(p);
    }

    @Override
    public void triggerOnExhaust() {
        applyEffects(AbstractDungeon.player);
    }

    private void applyEffects(AbstractPlayer p) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new DrawCardAction(p, this.magicNumber));
    }
}
