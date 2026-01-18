package thedefierwagdtd.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.LionsHeartBuff;
import thedefierwagdtd.powers.SalubriousShinePower;
import thedefierwagdtd.powers.ShadowDancerPower;
import thedefierwagdtd.util.CardStats;

public class ShadowDancer extends BaseCard {
    public static final String ID = makeID(ShadowDancer.class.getSimpleName());

    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            AbstractCard.CardType.POWER,
            AbstractCard.CardRarity.RARE,
            AbstractCard.CardTarget.SELF,
            0
    );

    private static final int MAGIC_NUMBER = 1;

    public ShadowDancer() {
        super(ID, info);
        setMagic(MAGIC_NUMBER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower shadowDancer = p.getPower(ShadowDancerPower.POWER_ID);
        int amount = (shadowDancer == null ? 0 : shadowDancer.amount) + 1;

        addToBot(new ApplyPowerAction(p,p, new ShadowDancerPower(p,this.magicNumber)));

        if (upgraded) {
            addToBot(new DrawCardAction(p, 1));
        }

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            applyToCard(c, amount);
        }
    }

    private void applyToCard(AbstractCard c, int amount) {
        if (isPlayableCurse(c)) {
            c.setCostForTurn(c.cost + amount);
        }
    }

    private boolean isPlayableCurse(AbstractCard c) {
        return c.type == AbstractCard.CardType.CURSE && c.cost >= 0;
    }
}
