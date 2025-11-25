package thedefierwagdtd.cards.common;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.CardStats;

public class StaticCharge extends BaseCard {
    public static final String ID = makeID(StaticCharge.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );
    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;

    public StaticCharge() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(
                "Exhaust to deal damage.",
                (cards) -> {
                    for (AbstractCard c : cards) {
                        int effectiveCost = c.costForTurn;
                        if (effectiveCost < 0) effectiveCost = 0;

                        c.superFlash(Color.YELLOW.cpy());

                        addToBot(new DamageAction(m, new DamageInfo(p, damage * (effectiveCost + 1), DamageInfo.DamageType.NORMAL),
                                AbstractGameAction.AttackEffect.LIGHTNING));

                        addToBot(new ExhaustSpecificCardAction(c, p.hand));
                    }
                }
        ));
    }
}
