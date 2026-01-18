package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import thedefierwagdtd.TheDefierModWAGDTD;
import thedefierwagdtd.character.TheDefier;

import java.util.ArrayList;
import java.util.HashMap;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class BlueBowtie extends BaseRelic {
    private static final String NAME = "BlueBowtie";
    public static final String RELIC_ID = makeID(BlueBowtie.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public BlueBowtie() {
        super(RELIC_ID, NAME, RARITY, SOUND);
    }

    @Override
    public boolean canSpawn() {
        if (AbstractDungeon.player instanceof TheDefier) {
            return true;
        }

        return TheDefierModWAGDTD.enableOtherCharRelics;
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onRest() {
        ArrayList<AbstractCard> curses = new ArrayList<>();

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.type == AbstractCard.CardType.CURSE) {
                curses.add(c);
            }
        }

        if (curses.isEmpty()) {
            return;
        }

        AbstractCard curse = curses.get(
                AbstractDungeon.miscRng.random(curses.size() - 1)
        );

        AbstractDungeon.player.masterDeck.removeCard(curse);

        flash();
        CardCrawlGame.sound.play("CARD_EXHAUST");
        AbstractDungeon.topLevelEffects.add(
                new PurgeCardEffect(
                        curse,
                        Settings.WIDTH / 2f,
                        Settings.HEIGHT / 2f
                )
        );
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BlueBowtie();
    }
}

