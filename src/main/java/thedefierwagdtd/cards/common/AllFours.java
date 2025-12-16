package thedefierwagdtd.cards.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import thedefierwagdtd.actions.ChooseCardToIncreaseCost;
import thedefierwagdtd.actions.ChooseCardToRetainAndDiscountAction;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.CardStats;

public class AllFours extends BaseCard {
    public static final String ID = makeID(AllFours.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );
    private static final int BLOCK = 6;
    private static final int BLOCK_UPG = 4;

    public AllFours() {
        super(ID, info);
        setBlock(BLOCK, BLOCK_UPG);
    }

    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot((AbstractGameAction)new GainBlockAction((AbstractCreature)p, (AbstractCreature)p, this.block));
        if (p.hand.size() == 2) {
            AbstractCard otherCard = null;
            for (AbstractCard c : p.hand.group) {
                if (c != this) {
                    otherCard = c;
                    break;
                }
            }

            if (otherCard != null && otherCard.cost != -2 && otherCard.cost != -1) {
                otherCard.costForTurn += 1;
                if (otherCard.costForTurn < 0) otherCard.costForTurn = 0;
                otherCard.isCostModifiedForTurn = true;
                otherCard.superFlash(Color.RED.cpy());
            }
        } else {
            addToBot(new ChooseCardToIncreaseCost(1));
        }
    }
}
