package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import thedefierwagdtd.character.TheDefier;

import java.util.ArrayList;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class GingerbreadHouse extends BaseRelic {
    private static final String NAME = "GingerbreadHouse";
    public static final String RELIC_ID = makeID(GingerbreadHouse.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.SPECIAL;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private boolean cardsSelected = true;

    public GingerbreadHouse() {
        super(RELIC_ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        this.cardsSelected = false;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : (AbstractDungeon.player.masterDeck.getUpgradableCards()).group)
            tmp.addToTop(card);
        if (tmp.group.isEmpty()) {
            this.cardsSelected = true;
            return;
        }
        if (tmp.group.size() <= 1) {
            giveCards(tmp.group);
        } else if (!AbstractDungeon.isScreenUp) {
            AbstractDungeon.gridSelectScreen.open(tmp, 1, this.DESCRIPTIONS[1] + this.name + LocalizedStrings.PERIOD, false, false, false, false);
        } else {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
            AbstractDungeon.gridSelectScreen.open(tmp, 1, this.DESCRIPTIONS[1] + this.name + LocalizedStrings.PERIOD, false, false, false, false);
        }
    }

    public void update() {
        super.update();
        if (!this.cardsSelected &&
                AbstractDungeon.gridSelectScreen.selectedCards.size() == 1)
            giveCards(AbstractDungeon.gridSelectScreen.selectedCards);
    }

    public void giveCards(ArrayList<AbstractCard> group) {
        this.cardsSelected = true;
        float displayCount = 0.0F;
        for (AbstractCard card : group) {
            card.untip();
            card.unhover();
            ((AbstractCard)group.get(0)).upgrade();

            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard)group
                    .get(0)).makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        }
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
    }

    public AbstractRelic makeCopy() {
        return new GingerbreadHouse();
    }

}
