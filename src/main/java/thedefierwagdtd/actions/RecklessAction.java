package thedefierwagdtd.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import thedefierwagdtd.cards.uncommon.BrushWithDeath;
import thedefierwagdtd.powers.CautionPower;

import static java.lang.Math.max;

public class RecklessAction extends AbstractGameAction {
    private final AbstractCreature player;
    private final AbstractCard sourceCard;

    public RecklessAction(AbstractCreature player) {
        this(player, null);
    }

    public RecklessAction(AbstractCreature player, AbstractCard sourceCard) {
        this.player = player;
        this.sourceCard = sourceCard;
    }

    @Override
    public void update() {
        int damage = (int) Math.floor(player.currentHealth * 0.1);

        // Apply Caution mitigation if present
        AbstractPower caution = player.getPower(CautionPower.POWER_ID);
        if (caution instanceof CautionPower) {
            damage = Math.max(((CautionPower) caution).modifyRecklessDamage(damage), 0);
        }

        // 🔑 Special case: Brush With Death ignores damage if you have a curse in hand
        if (sourceCard != null && sourceCard.cardID.equals(BrushWithDeath.ID)) {
            boolean hasCurse = AbstractDungeon.player.hand.group.stream()
                    .anyMatch(c -> c.type == AbstractCard.CardType.CURSE);

            if (hasCurse) {
                this.isDone = true; // skip damage entirely
                return;
            }
        }

        if (player.hasPower(IntangiblePower.POWER_ID)){
            AbstractDungeon.actionManager.addToTop(
                    new DamageAction(player, new DamageInfo(player, 1, DamageInfo.DamageType.THORNS), AttackEffect.FIRE));
        }
        else {
            AbstractDungeon.actionManager.addToTop(
                    new DamageAction(player, new DamageInfo(player, damage, DamageInfo.DamageType.THORNS), AttackEffect.FIRE));
        }
        this.isDone = true;
    }
}