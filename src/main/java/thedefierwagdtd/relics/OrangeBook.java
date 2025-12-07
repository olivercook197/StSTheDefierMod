package thedefierwagdtd.relics;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import thedefierwagdtd.cardmodifiers.TwoVulnerableToEveryoneModifier;
import thedefierwagdtd.character.TheDefier;

import java.util.UUID;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class OrangeBook extends BaseRelic {
    private static final String NAME = "OrangeBook";
    public static final String RELIC_ID = makeID(OrangeBook.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.SPECIAL;
    private static final LandingSound SOUND = LandingSound.SOLID;

    private boolean waitingForSelection = false;

    public OrangeBook() {
        super(RELIC_ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
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
                "Choose a card: when you play it, apply 2 Vulnerable to EVERYONE.",
                false, false, false, false
        );
    }

    @Override
    public void update() {
        super.update();

        if (waitingForSelection && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

            AbstractCard chosen = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            CardModifierManager.addModifier(chosen, new TwoVulnerableToEveryoneModifier());

            flash();
            AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(chosen.makeStatEquivalentCopy()));
            CardCrawlGame.sound.play("UPGRADE_CARD");

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            waitingForSelection = false;
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new OrangeBook();
    }
}
