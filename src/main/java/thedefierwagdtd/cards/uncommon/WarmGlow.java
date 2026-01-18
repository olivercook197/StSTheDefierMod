package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.CautionPower;
import thedefierwagdtd.powers.LionsHeartBuff;
import thedefierwagdtd.powers.LoseCautionPower;
import thedefierwagdtd.util.CardStats;

public class WarmGlow extends BaseCard {
    public static final String ID = makeID(WarmGlow.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC_NUMBER = 2;

    public WarmGlow() {
        super(ID, info);
        setMagic(MAGIC_NUMBER);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        long timesPlayedThisCombat =
                AbstractDungeon.actionManager.cardsPlayedThisCombat
                        .stream()
                        .filter(c -> c.cardID.equals(this.cardID))
                        .count();

        if (timesPlayedThisCombat == 1) {
            addToBot(new ApplyPowerAction(
                    p, p,
                    new RegenPower(p, magicNumber),
                    magicNumber
            ));
        }

        addToBot(new ApplyPowerAction(
                p, p,
                new CautionPower(p, 10), 10
        ));
        addToBot(new ApplyPowerAction(
                p, p,
                new LoseCautionPower(p, 10), 10
        ));
    }



    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}


