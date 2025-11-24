package thedefierwagdtd.cardmodifiers;


import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class BlockBoostModifier extends AbstractCardModifier {
    private final int amount;

    public BlockBoostModifier(int amount) {
        this.amount = amount;
    }

    @Override
    public String identifier(AbstractCard card) {
        return "thedefier:BlockBoostModifier";
    }

    @Override
    public float modifyBlock(float block, AbstractCard card) {
        return block + amount;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BlockBoostModifier(amount);
    }
}