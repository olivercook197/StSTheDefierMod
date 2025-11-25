package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thedefierwagdtd.actions.GraceAction;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.FacingDownPower;
import thedefierwagdtd.powers.LionsHeartBuff;
import thedefierwagdtd.util.CardStats;

public class Grace extends BaseCard {
    public static final String ID = makeID(Grace.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -1 // X cost
    );

    private static final int MAGIC_NUMBER = 10;
    private static final int MAGIC_NUMBER_UPG = -4;

    public Grace() {
        super(ID, info);
        setMagic(MAGIC_NUMBER, MAGIC_NUMBER_UPG);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower lionsHeart = p.getPower(LionsHeartBuff.POWER_ID);
        int lhAmount = (lionsHeart == null ? 0 : lionsHeart.amount);

        addToBot(new GraceAction(
                p,
                this.freeToPlayOnce,
                this.energyOnUse,
                this.magicNumber,
                lhAmount
        ));
    }


    @Override
    public void applyPowers() {
        super.applyPowers();

        if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(this)) {
            AbstractPower lionsHeart = AbstractDungeon.player.getPower(LionsHeartBuff.POWER_ID);
            int lhAmount = (lionsHeart == null ? 0 : lionsHeart.amount);
            int divisor = this.magicNumber;
            int dexGain = (int) Math.floor((float) lhAmount / Math.max(1, divisor));

            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + dexGain + cardStrings.EXTENDED_DESCRIPTION[1];
            initializeDescription();
        } else {
            this.rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
        }
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }
}
