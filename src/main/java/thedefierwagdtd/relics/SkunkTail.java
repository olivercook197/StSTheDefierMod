package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import thedefierwagdtd.character.TheDefier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class SkunkTail extends BaseRelic {
    private static final String NAME = "SkunkTail";
    public static final String RELIC_ID = makeID(SkunkTail.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.SPECIAL;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public SkunkTail() {
        super(RELIC_ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        ArrayList<AbstractCard> curseCards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.type == AbstractCard.CardType.CURSE)
                curseCards.add(c);
        }
        Collections.shuffle(curseCards, new Random(AbstractDungeon.miscRng.randomLong()));
        AbstractCard curse1;
        AbstractCard curse2;
        AbstractCard curse3;
        if (!curseCards.isEmpty())
            if (curseCards.size() >= 3) {
                curse1 = curseCards.get(0);
                curse2 = curseCards.get(1);
                curse3 = curseCards.get(2);
                AbstractDungeon.player.masterDeck.removeCard(curse1);
                AbstractDungeon.player.masterDeck.removeCard(curse2);
                AbstractDungeon.player.masterDeck.removeCard(curse3);

                CardCrawlGame.sound.play("CARD_EXHAUST");
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(curse1, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(curse2, Settings.WIDTH, Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(curse3, Settings.WIDTH * 2.0F, Settings.HEIGHT / 2.0F));
            }
            else if (curseCards.size() == 2) {
                curse1 = curseCards.get(0);
                curse2 = curseCards.get(1);

                AbstractDungeon.player.masterDeck.removeCard(curse1);
                AbstractDungeon.player.masterDeck.removeCard(curse2);

                CardCrawlGame.sound.play("CARD_EXHAUST");
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(curse1, Settings.WIDTH / 1.5F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(curse2, Settings.WIDTH * 1.5F, Settings.HEIGHT / 2.0F));
            }
            else if (curseCards.size() == 1) {
                curse1 = curseCards.get(0);

                AbstractDungeon.player.masterDeck.removeCard(curse1);

                CardCrawlGame.sound.play("CARD_EXHAUST");
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(curse1, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            }
    }

    public AbstractRelic makeCopy() {
        return new SkunkTail();
    }
}
