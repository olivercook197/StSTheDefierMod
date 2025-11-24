package thedefierwagdtd.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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

            if (p.hand.isEmpty()) {
                isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(
                    "to increase Block.",
                    1,
                    false,
                    false
            );

            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {

                c.baseBlock += blockIncrease;
                c.applyPowers();
                c.superFlash(Color.GOLD.cpy());

                AbstractCard master = AbstractDungeon.player.masterDeck.getSpecificCard(c);

                if (master != null) {
                    master.misc += 1;
                    master.baseBlock += master.misc;
                }

                if (master != null) {
                    c.misc = master.misc;
                    c.baseBlock = c.misc;
                }

                c.applyPowers();

                p.hand.addToTop(c);
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.clear();
            isDone = true;
        }

        tickDuration();
    }
}
