package thedefierwagdtd.cards.rare;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.cards.common.ShakeOff;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.CardStats;

public class AncientTeachings extends BaseCard {
    public static final String ID = makeID(AncientTeachings.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            0
    );

    private static final int TEMP_STR_PER_COST = 5;
    private static final int UPG_TEMP_STR_PER_COST = 3;


    public AncientTeachings() {
        super(ID, info);
        setMagic(TEMP_STR_PER_COST, UPG_TEMP_STR_PER_COST);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(
                1,
                "Exhaust a card to gain Temporary Strength.",
                false, false,
                (AbstractCard c) -> true,
                (cards) -> {
                    for (AbstractCard c : cards) {
                        int cost = Math.max(0, c.costForTurn);

                        int totalTempStr = (cost * this.magicNumber);
                        if (totalTempStr > 0) {

                            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, totalTempStr), totalTempStr));
                            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, totalTempStr), totalTempStr));

                        }

                        addToBot(new ExhaustSpecificCardAction(c, p.hand));
                    }
                }
        ));
    }
}
