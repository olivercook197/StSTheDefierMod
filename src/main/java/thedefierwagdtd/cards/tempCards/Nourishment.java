package thedefierwagdtd.cards.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.cards.uncommon.CoughUpFurball;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.CardStats;

import java.util.ArrayList;

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

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.player.increaseMaxHp(this.magicNumber, false);

        if (AbstractDungeon.cardRandomRng.random(99) < 20) {
            AbstractMonster randomEnemy = AbstractDungeon.getMonsters()
                    .getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

            if (randomEnemy != null) {
                addToBot(new ApplyPowerAction(
                        randomEnemy,
                        p,
                        new GainStrengthPower(randomEnemy, 2),
                        2
                ));
            }
        }
    }
}
