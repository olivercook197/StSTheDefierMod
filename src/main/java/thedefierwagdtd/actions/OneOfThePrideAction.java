package thedefierwagdtd.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import thedefierwagdtd.powers.LionsHeartBuff;

public class OneOfThePrideAction extends AbstractGameAction {
    private DamageInfo info;

    public OneOfThePrideAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        setValues(target, info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }

    public void update() {
        if (shouldCancelAction()) {
            this.isDone = true;
            return;
        }
        tickDuration();
        if (this.isDone) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.FIRE, false));
            this.target.damage(this.info);
            if (this.target.lastDamageTaken > 0) {
                addToTop(new ApplyPowerAction((AbstractCreature) AbstractDungeon.player, (AbstractCreature) AbstractDungeon.player,
                        (AbstractPower) new LionsHeartBuff((AbstractCreature) AbstractDungeon.player, this.target.lastDamageTaken), this.target.lastDamageTaken));
                if (this.target.hb != null)
                    addToBot(new VFXAction(new VerticalAuraEffect(Color.GOLD, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
            }
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            } else {
                addToTop((AbstractGameAction)new WaitAction(0.1F));
            }
        }
    }
}
