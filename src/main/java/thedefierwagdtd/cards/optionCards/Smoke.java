package thedefierwagdtd.cards.optionCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.powers.LionsHeartBuff;
import thedefierwagdtd.util.CardStats;

public class Smoke extends BaseCard {
    public static final String ID = makeID(Smoke.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            -2
    );

    private AbstractMonster target;
    private int damageAmount;

    public Smoke() {
        this(null, 0);
    }

    public Smoke(AbstractMonster m, int damageAmount) {
        super(ID, info);
        this.purgeOnUse = true;
        this.target = m;
        this.damage = damageAmount;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return EnergyPanel.totalCount >= 1;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        onChoseThisOption();
    }

    @Override
    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;

        if (target != null) {
            if (EnergyPanel.totalCount < 1) {
                AbstractDungeon.effectList.add(
                        new com.megacrit.cardcrawl.vfx.ThoughtBubble(p.dialogX, p.dialogY, 3.0F, "Not enough Energy!", true)
                );
            }
            else {
                EnergyPanel.useEnergy(1);

                addToBot(new DamageAction(
                        target,
                        new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT
                ));
                addToBot(new ApplyPowerAction(p, p, new LionsHeartBuff(p, 9), 9));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { return new Smoke(target, damage); }
}
