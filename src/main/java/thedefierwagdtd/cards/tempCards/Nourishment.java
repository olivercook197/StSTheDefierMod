package thedefierwagdtd.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.patches.NourishmentCounterField;
import thedefierwagdtd.util.CardStats;

public class Nourishment extends BaseCard {
    public static final String ID = makeID(Nourishment.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            2
    );

    private static final int MAGIC_NUMBER = 2;
    private static final int MAGIC_NUMBER_UPG = 2;

    public Nourishment() {
        super(ID, info);
        setMagic(MAGIC_NUMBER, MAGIC_NUMBER_UPG);
        this.exhaust = true;
        this.isEthereal = true;
        this.tags.add(AbstractCard.CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = NourishmentCounterField.nourishmentCount.get(p);
        if (NourishmentCounterField.nourishmentCount.get(p) != 0) {
            NourishmentCounterField.nourishmentCount.set(AbstractDungeon.player, 0);
        }
        NourishmentCounterField.nourishmentCount.set(p, count + 1);

        p.increaseMaxHp(this.magicNumber, false);

        if (AbstractDungeon.cardRandomRng.random(99) < 20) {
            AbstractMonster randomEnemy =
                    AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

            if (randomEnemy != null && !randomEnemy.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(
                        randomEnemy, p,
                        new GainStrengthPower(randomEnemy, 2), 2
                ));
            }
        }
    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        }

        int count = NourishmentCounterField.nourishmentCount.get(p);
        if (count >= 10) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }

        return true;
    }

}
