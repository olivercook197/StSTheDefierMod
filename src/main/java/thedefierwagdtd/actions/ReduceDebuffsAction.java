package thedefierwagdtd.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ReduceDebuffsAction extends AbstractGameAction {
    private AbstractCreature c;
    private int reduceAmount;

    public ReduceDebuffsAction(AbstractCreature c, int reduceAmount) {
        this.c = c;
        this.duration = 0.5F;
        this.reduceAmount = reduceAmount;
    }

    public void update() {
        for(AbstractPower p : this.c.powers) {
            if (p.type == AbstractPower.PowerType.DEBUFF) {
                this.addToTop(new ReducePowerAction(this.c, this.c, p,reduceAmount));
            }
        }

        this.isDone = true;
    }
}