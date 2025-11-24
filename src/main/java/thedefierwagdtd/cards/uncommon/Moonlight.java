package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.CardStats;

public class Moonlight extends BaseCard {
    public static final String ID = makeID(Moonlight.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );
    private static final int BLOCK = 8;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPG_MAGIC_NUMBER = 2;

    public Moonlight() {
        super(ID, info);
        setBlock(BLOCK);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        addToBot((AbstractGameAction)new GainBlockAction(p, p, this.block));
    }

    public void applyPowers() {
        int totalEnergyCost  = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c != this && c.costForTurn > 0) { // ignore free/unplayable cards
                totalEnergyCost += c.costForTurn;
            }
        }
        int totalBaseBlock = BLOCK + (totalEnergyCost * this.magicNumber);
        this.baseBlock = totalBaseBlock;

        super.applyPowers();

        String colorTag;
        if (this.block > totalBaseBlock) {
            colorTag = "[#7fff00]";
        } else if (this.block < totalBaseBlock) {
            colorTag = "[#ff6565]";
        } else {
            colorTag = "";
        }

        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + BLOCK + cardStrings.EXTENDED_DESCRIPTION[1] +
                cardStrings.EXTENDED_DESCRIPTION[2] + colorTag + this.block + "[]" + cardStrings.EXTENDED_DESCRIPTION[3];
        initializeDescription();

        this.baseBlock = BLOCK;
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }
}
