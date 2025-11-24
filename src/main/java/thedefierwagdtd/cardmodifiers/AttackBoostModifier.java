package thedefierwagdtd.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AttackBoostModifier extends AbstractCardModifier {
    private final int amount;

    public AttackBoostModifier(int amount) {
        this.amount = amount;
    }

    @Override
    public String identifier(AbstractCard card) {
        return "thedefier:AttackBoostModifier";
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + amount;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new AttackBoostModifier(amount);
    }
}