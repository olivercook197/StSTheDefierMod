package thedefierwagdtd.cards.optionCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.powers.LionsHeartBuff;
import thedefierwagdtd.util.CardStats;

public class Mirrors extends BaseCard {
    public static final String ID = makeID(Mirrors.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            -2
    );

    private AbstractMonster target;

    public Mirrors() {
        this(null, 8, 0);
    }

    public Mirrors(AbstractMonster m, int lionsHeartCost, int damageAmount) {
        super(ID, info);
        setMagic(lionsHeartCost, 0);
        this.purgeOnUse = true;
        this.target = m;
        this.damage = damageAmount;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        onChoseThisOption();
    }

    @Override
    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;

        // confirm enough Lion’s Heart
        AbstractPower lh = p.getPower(LionsHeartBuff.POWER_ID);
        if (lh == null || lh.amount < this.magicNumber) {
            AbstractDungeon.effectList.add(
                    new com.megacrit.cardcrawl.vfx.ThoughtBubble(p.dialogX, p.dialogY, 3.0F, "Not enough Lion's Heart!", true)
            );
            return;
        }

        addToBot(new ReducePowerAction(p, p, LionsHeartBuff.POWER_ID, this.magicNumber));

        if (target != null) {
            addToBot(new DamageAction(
                    target,
                    new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL
            ));
        }

        addToBot(new ApplyPowerAction(p, p, new LionsHeartBuff(p, 9), 9));
    }

    @Override
    public AbstractCard makeCopy() { return new Mirrors(null, 8, damage); }
}
