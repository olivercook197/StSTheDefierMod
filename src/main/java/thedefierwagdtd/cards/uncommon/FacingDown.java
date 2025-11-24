package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.cards.common.BringerOfBadLuck;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.FacingDownPower;
import thedefierwagdtd.powers.UnstablePower;
import thedefierwagdtd.powers.VentingPower;
import thedefierwagdtd.util.CardStats;

public class FacingDown extends BaseCard {
    public static final String ID = makeID(FacingDown.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 4;
    private static final int RETAIN_AMOUNT = 1;

    public FacingDown() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(RETAIN_AMOUNT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p,
                this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING));
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                (AbstractPower)new FacingDownPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
    }
}
