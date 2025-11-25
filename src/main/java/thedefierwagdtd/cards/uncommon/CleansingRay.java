package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ModifyBlockAction;
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

    private static final int BASE_BLOCK = 4;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 2;

    public CleansingRay() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);

        this.baseBlock = BASE_BLOCK;
        this.misc = BASE_BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));

        int recklessCount = (int) p.hand.group.stream()
                .filter(c -> c.hasTag(CustomTag.RECKLESS))
                .count();

        addToBot((AbstractGameAction)new ModifyBlockAction(this.uuid, recklessCount * this.magicNumber));
    }

    @Override
    public void applyPowers() {
        this.baseBlock = this.misc;
        super.applyPowers();
    }
}
