package thedefierwagdtd.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ChooseCardToAddBlock extends AbstractGameAction {
    private final int blockIncrease;

    public ChooseCardToAddBlock(int blockIncrease) {
        this.blockIncrease = blockIncrease;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        if (this.duration == Settings.ACTION_DUR_FAST) {

            CardGroup blockableCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : p.hand.group) {
                if (c.baseBlock > 0) {
                    blockableCards.addToTop(c);
                }
            }

            if (blockableCards.isEmpty()) {
                isDone = true;
                return;
            }

            AbstractDungeon.gridSelectScreen.open(
                    blockableCards,
                    1,
                    "Select a card to increase Block.",
                    false,
                    false,
                    false,
                    false
            );

            tickDuration();
            return;
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {

                c.baseBlock += blockIncrease;
                c.applyPowers();
                c.superFlash(Color.GOLD.cpy());

                AbstractCard master = AbstractDungeon.player.masterDeck.getSpecificCard(c);
                if (master != null) {
                    master.baseBlock += blockIncrease;
                    master.applyPowers();
                }
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            isDone = true;
        }

        tickDuration();
    }
}
