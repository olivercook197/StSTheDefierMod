package thedefierwagdtd.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thedefierwagdtd.TheDefierModWAGDTD;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.UnfazedPower;


import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class MagicScreen extends BaseRelic implements OnReceivePowerRelic {
    private static final String NAME = "MagicScreen";
    public static final String RELIC_ID = makeID(MagicScreen.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;

    private static final int UNFAZED_AMT = 3;

    private boolean triggeredThisCombat = false;

    public MagicScreen() {
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
    public void atBattleStart() {
        triggeredThisCombat = false;
        grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new MagicScreen();
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature) {
        if (triggeredThisCombat) return true;
        if (abstractPower.ID.equals(VulnerablePower.POWER_ID)) {
            triggeredThisCombat = true;
            grayscale = true;
            flash();

            addToBot(new ApplyPowerAction(
                    AbstractDungeon.player,
                    AbstractDungeon.player,
                    new UnfazedPower(AbstractDungeon.player, UNFAZED_AMT),
                    UNFAZED_AMT
            ));
        }
        return true;
    }
}
