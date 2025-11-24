package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.CardStats;


public class CleansingRay extends BaseCard {
    public static final String ID = makeID(CleansingRay.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int BLOCK = 3;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public CleansingRay() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);

        this.block = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));

        int recklessCount = 0;
        for (AbstractCard c : p.hand.group) {
            if (c.hasTag(CustomTag.RECKLESS)) {
                recklessCount++;
            }
        }
        this.block += recklessCount * this.magicNumber;

        this.baseBlock = this.block;
        applyPowers();
    }

    @Override
    public void applyPowers() {
        this.baseBlock = this.block;

        super.applyPowers();

        this.isBlockModified = (this.block != this.baseBlock);
    }

    @Override
    public void onMoveToDiscard() {
        this.baseBlock = this.block;
    }
}
