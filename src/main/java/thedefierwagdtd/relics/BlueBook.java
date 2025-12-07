package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import thedefierwagdtd.character.TheDefier;

import java.util.ArrayList;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class BlueBook extends BaseRelic{
    private static final String NAME = "BlueBook";
    public static final String RELIC_ID = makeID(BlueBook.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.SPECIAL;
    private static final LandingSound SOUND = LandingSound.SOLID;

    private boolean waitingForSelection = false;

    public BlueBook() {
        super(RELIC_ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        waitingForSelection = true;

        CardGroup group = CardGroup.getGroupWithoutBottledCards(
                AbstractDungeon.player.masterDeck.getPurgeableCards()
        );

        AbstractDungeon.gridSelectScreen.open(
                group,
                1,
                "Choose a card to permanently reduce its cost by 1",
                false, false, false, false
        );
    }

    @Override
    public void update() {
        super.update();

        if (waitingForSelection && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

            AbstractCard chosen = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            if (chosen.cost > 0) {
                chosen.cost--;
                chosen.costForTurn = chosen.cost;
                chosen.isCostModified = true;
            }

            flash();
            CardCrawlGame.sound.play("UPGRADE_CARD");

            AbstractDungeon.effectList.add(
                    new ShowCardBrieflyEffect(chosen.makeStatEquivalentCopy())
            );

            // Clear selection
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            waitingForSelection = false;
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BlueBook();
    }
}
