package thedefierwagdtd.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.BattleFormPower;
import thedefierwagdtd.powers.CloseTheGapPower;
import thedefierwagdtd.powers.CustomDexterity;
import thedefierwagdtd.util.CardStats;

public class CloseTheGap extends BaseCard {
    public static final String ID = makeID(CloseTheGap.class.getSimpleName());

    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            AbstractCard.CardType.POWER,
            AbstractCard.CardRarity.RARE,
            AbstractCard.CardTarget.SELF,
            1
    );

    private static final int MAGIC_NUMBER = 4;

    public CloseTheGap() {
        super(ID, info);
        setMagic(MAGIC_NUMBER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                (AbstractPower)new CloseTheGapPower((AbstractCreature)p, this.magicNumber),
                this.magicNumber));

        if (upgraded) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDeadOrEscaped()) {
                    addToBot(new ApplyPowerAction(
                            mo, p,
                            new StrengthPower(mo, -1), -1,
                            true, AbstractGameAction.AttackEffect.NONE
                    ));
                    addToBot(new ApplyPowerAction(
                            mo, p,
                            new CustomDexterity(mo, -1), -1,
                            true, AbstractGameAction.AttackEffect.NONE
                    ));
                }
            }
        }
    }

}
