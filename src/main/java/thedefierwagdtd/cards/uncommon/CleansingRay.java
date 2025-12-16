package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ModifyBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.CardStats;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


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
    private static final int MAGIC_UPG = 2;

    public CleansingRay() {
        super(ID, info);
        setMagic(MAGIC, MAGIC_UPG);

        this.misc = BASE_BLOCK;
        this.baseBlock = BASE_BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int recklessCount = (int) p.hand.group.stream()
                .filter(c -> c.hasTag(CustomTag.RECKLESS))
                .count();

        int bonus = recklessCount * this.magicNumber;

        addToBot(new GainBlockAction(p, p, this.block));

        if (bonus > 0) {
            this.misc += bonus;
            this.baseBlock = this.misc;
            applyPowers();
        }
    }

    @Override
    public void applyPowers() {
        AbstractPlayer p = AbstractDungeon.player;

        int recklessCount = (int) p.hand.group.stream()
                .filter(c -> c.hasTag(CustomTag.RECKLESS))
                .count();

        this.baseBlock = this.misc + recklessCount * this.magicNumber;

        super.applyPowers();
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.baseBlock = this.misc;
        this.block = this.baseBlock;
        this.isBlockModified = false;
        initializeDescription();
    }
}