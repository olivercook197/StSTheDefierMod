package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thedefierwagdtd.actions.ReduceDebuffsAction;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.LionsHeartBuff;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class ToyMouse extends BaseRelic {
    private static final String NAME = "ToyMouse";
    public static final String RELIC_ID = makeID(ToyMouse.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.SHOP;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public ToyMouse() {
        super(RELIC_ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onPlayerEndTurn() {
        AbstractPlayer p = AbstractDungeon.player;

        if (p.hasPower(VulnerablePower.POWER_ID)) {
            flash();

            addToBot(new ApplyPowerAction(
                    p,
                    p,
                    new VulnerablePower(p, 2, false),
                    2
            ));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ToyMouse();
    }
}

