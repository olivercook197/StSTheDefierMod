package thedefierwagdtd.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;

public class DecreaseCardsCost extends AbstractGameAction {
    private final AbstractPlayer player;
    private final int amount;
    private final boolean permanent;  // true = rest of combat, false = this turn

    public DecreaseCardsCost(AbstractPlayer player, int amount, boolean permanent) {
        this.player = player;
        this.amount = amount;
        this.permanent = permanent;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST) {
            ArrayList<AbstractCard> candidates = new ArrayList<>();

            for (AbstractCard c : player.hand.group) {

                if (permanent) {
                    if (c.cost > 0) {
                        candidates.add(c);
                    }
                }
                else {
                    if (c.costForTurn > 0) {
                        candidates.add(c);
                    }
                }
            }

            if (!candidates.isEmpty()) {
                Collections.shuffle(candidates, AbstractDungeon.cardRandomRng.random);
                int reduceCount = Math.min(this.amount, candidates.size());

                for (int i = 0; i < reduceCount; i++) {
                    AbstractCard c = candidates.get(i);

                    if (permanent) {
                        c.cost = Math.max(0, c.cost - 1);
                        c.isCostModified = true;
                        c.costForTurn = c.cost;
                    } else {
                        c.costForTurn = Math.max(0, c.costForTurn - 1);
                        c.isCostModifiedForTurn = true;
                    }

                    c.superFlash(Color.GOLD.cpy());
                }
            }

            this.isDone = true;
        }

        tickDuration();
    }
}
