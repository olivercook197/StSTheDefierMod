package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.BackAlleyPower;
import thedefierwagdtd.powers.CautionPower;

import java.util.ArrayList;
import java.util.Iterator;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class EmptyWhiskeyBottle extends BaseRelic {
    private static final String NAME = "EmptyWhiskeyBottle";
    public static final String RELIC_ID = makeID(EmptyWhiskeyBottle.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private boolean cardsSelected = true;

    public EmptyWhiskeyBottle() {
        super(RELIC_ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        flash();
        addToTop(new ApplyPowerAction(
                AbstractDungeon.player,
                AbstractDungeon.player,
                new BackAlleyPower(AbstractDungeon.player, 1),
                1
        ));

        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public AbstractRelic makeCopy() {
        return new EmptyWhiskeyBottle();
    }
}
