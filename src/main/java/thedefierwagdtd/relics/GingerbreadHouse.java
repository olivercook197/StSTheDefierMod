package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import thedefierwagdtd.character.TheDefier;

import java.util.ArrayList;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class GingerbreadHouse extends BaseRelic {
    private static final String NAME = "GingerbreadHouse";
    public static final String RELIC_ID = makeID(GingerbreadHouse.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.CLINK;

    private boolean drawNextTurn = false;
    private boolean spentEnergyThisTurn = false;

    public GingerbreadHouse() {
        super(RELIC_ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        drawNextTurn = false;
        spentEnergyThisTurn = false;
        stopPulse();
    }

    @Override
    public void atTurnStart() {
        spentEnergyThisTurn = false;

        if (drawNextTurn) {
            drawNextTurn = false;
            stopPulse();
            flash();
            addToBot(new DrawCardAction(2));
        }
    }

    @Override
    public void onPlayerEndTurn() {
        if (EnergyPanel.totalCount >= AbstractDungeon.player.energy.energyMaster) {
            flash();
            drawNextTurn = true;
        }
    }
}
