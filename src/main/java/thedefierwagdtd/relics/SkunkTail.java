package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
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
    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;

    private static final int BLOCK = 2;

    public SkunkTail() {
        super(RELIC_ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.cost < 0 || card.costForTurn < 0) {
            return;
        }

        if (card.costForTurn != card.cost || card.isCostModifiedForTurn) {
            flash();
            addToBot(new GainBlockAction(
                    AbstractDungeon.player,
                    AbstractDungeon.player,
                    BLOCK
            ));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SkunkTail();
    }

}
