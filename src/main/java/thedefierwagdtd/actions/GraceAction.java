package thedefierwagdtd.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static com.badlogic.gdx.math.MathUtils.floor;

public class GraceAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final boolean freeToPlayOnce;
    private final int energyOnUse;
    private final int magicNumber;
    private final int lionsHeart;

    public GraceAction(AbstractPlayer p, boolean freeToPlayOnce, int energyOnUse, int magicNumber, int lionsHeart) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.energyOnUse = energyOnUse;
        this.magicNumber = magicNumber;
        this.lionsHeart = lionsHeart;

        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        int x = EnergyPanel.totalCount;
        if (energyOnUse != -1) {
            x = energyOnUse;
        }

        // Chemical X bonus
        if (p.hasRelic("Chemical X")) {
            x += 2;
            p.getRelic("Chemical X").flash();
        }

        // Determine how much Dex to apply per repeat
        int dexPer = (int) Math.floor((float) lionsHeart / Math.max(1, magicNumber));

        if (dexPer > 0) {
            int repeats = x + 1;
            for (int i = 0; i < repeats; i++) {
                addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, dexPer), dexPer));
            }
        }

        // Consume energy normally
        if (!freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }

        this.isDone = true;
    }
}