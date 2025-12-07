package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import thedefierwagdtd.character.TheDefier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class MagicScreen extends BaseRelic{
    private static final String NAME = "MagicScreen";
    public static final String RELIC_ID = makeID(MagicScreen.class.getSimpleName());
    private static final AbstractRelic.RelicTier RARITY = AbstractRelic.RelicTier.SPECIAL;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK;

    public MagicScreen() {
        super(RELIC_ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        ArrayList<AbstractCard> upgradableCards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade())
                upgradableCards.add(c);
        }
        Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
        if (!upgradableCards.isEmpty())
            if (upgradableCards.size() == 1) {
                ((AbstractCard)upgradableCards.get(0)).upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard)upgradableCards
                        .get(0)).makeStatEquivalentCopy()));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            } else {
                ((AbstractCard)upgradableCards.get(0)).upgrade();
                ((AbstractCard)upgradableCards.get(1)).upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(1));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard)upgradableCards

                        .get(0)).makeStatEquivalentCopy(), Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale, Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard)upgradableCards

                        .get(1)).makeStatEquivalentCopy(), Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 20.0F * Settings.scale, Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            }
    }

    public AbstractRelic makeCopy() {
        return new MagicScreen();
    }
}
