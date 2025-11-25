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
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.CautionPower;
import thedefierwagdtd.powers.LionsHeartBuff;
import thedefierwagdtd.util.CardStats;

public class FloatAndFall extends BaseCard {
    public static final String ID = makeID(FloatAndFall.class.getSimpleName());

    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            0
    );

    private static final int MAGIC_NUMBER = 15;
    private static final int UPG_MAGIC_NUMBER = -5;

    public FloatAndFall() {
        super(ID, info);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower lionsheart = p.getPower(LionsHeartBuff.POWER_ID);
        int amount = (lionsheart == null ? 0 : lionsheart.amount);

        int strengthLoss = amount / this.magicNumber;

        if (strengthLoss > 0) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDeadOrEscaped()) {
                    addToBot(new ApplyPowerAction(
                            mo, p,
                            new StrengthPower(mo, -strengthLoss),
                            -strengthLoss
                    ));
                }
            }
        }

        addToBot(new ApplyPowerAction((AbstractCreature) AbstractDungeon.player, (AbstractCreature) AbstractDungeon.player,
                (AbstractPower) new LionsHeartBuff((AbstractCreature) AbstractDungeon.player, 5), 5));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        AbstractPower lionsheart = AbstractDungeon.player.getPower(LionsHeartBuff.POWER_ID);
        int amount = (lionsheart == null ? 0 : lionsheart.amount);

        int strengthLoss = amount / this.magicNumber;

        this.rawDescription = cardStrings.DESCRIPTION
                + cardStrings.EXTENDED_DESCRIPTION[0]
                + strengthLoss
                + cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }
}
