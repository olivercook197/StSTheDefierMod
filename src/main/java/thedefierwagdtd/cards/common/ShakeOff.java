package thedefierwagdtd.cards.common;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.CardStats;

public class ShakeOff extends BaseCard {
    public static final String ID = makeID(ShakeOff.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;

    public ShakeOff() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(
                "Exhaust to gain Block.",
                (cards) -> {
                    for (AbstractCard c : cards) {
                        int effectiveCost = c.costForTurn;
                        if (effectiveCost < 0) effectiveCost = 0;

                        addToBot(new GainBlockAction(p, p, block * effectiveCost));

                        addToBot(new ExhaustSpecificCardAction(c, p.hand));
                    }
                }
        ));
    }
}
