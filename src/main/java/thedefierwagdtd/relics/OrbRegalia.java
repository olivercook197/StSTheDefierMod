package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.CautionPower;
import thedefierwagdtd.powers.LionsHeartBuff;

import java.util.ArrayList;
import java.util.Iterator;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class OrbRegalia extends BaseRelic {

    private static final String NAME = "OrbRegalia";
    public static final String RELIC_ID = makeID(OrbRegalia.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public OrbRegalia() {
        super(RELIC_ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atTurnStart() {
        AbstractPower vulnerableAmount = AbstractDungeon.player.getPower(VulnerablePower.POWER_ID);
        int amount = (vulnerableAmount == null ? 0 : vulnerableAmount.amount);
        if (amount > 0){
            flash();
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new StrengthPower(AbstractDungeon.player, 2), 2));
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new LoseStrengthPower(AbstractDungeon.player, 2), 2));
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new DexterityPower(AbstractDungeon.player, 2), 2));
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new LoseDexterityPower(AbstractDungeon.player, 2), 2));
        }

    }

    public AbstractRelic makeCopy() {
        return new OrbRegalia();
    }
}
