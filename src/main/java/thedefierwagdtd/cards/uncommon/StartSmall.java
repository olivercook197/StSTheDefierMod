package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.LionsHeartBuff;
import thedefierwagdtd.powers.StartSmallPower;
import thedefierwagdtd.util.CardStats;

public class StartSmall extends BaseCard {
    public static final String ID = makeID(StartSmall.class.getSimpleName());

    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            AbstractCard.CardType.POWER,
            AbstractCard.CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int BASE_LIONSHEART = 1;   // starting per-turn gain
    private static final int BASE_THRESHOLD  = 10;  // threshold for first growth

    public StartSmall() {
        super(ID, info);

        this.misc = 0;

        syncGainFromMisc();

        updateCardDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        syncGainFromMisc();

        addToBot(new ApplyPowerAction(p, p,
                new StartSmallPower(p, this.magicNumber),
                this.magicNumber));

        if (this.upgraded) {
            addToBot(new DrawCardAction(p, 1));
        }
        updateCardDescription();
    }

    public void checkGrowth() {
        AbstractPower lionsheart = AbstractDungeon.player.getPower(LionsHeartBuff.POWER_ID);
        int amount = (lionsheart == null ? 0 : lionsheart.amount);

        int threshold = computeThreshold();

        if (amount >= threshold) {
            this.misc++;

            syncGainFromMisc();

            updateCardDescription();
        }
    }

    @Override
    public void applyPowers() {
        syncGainFromMisc();
        super.applyPowers();
    }

    @Override
    public AbstractCard makeCopy() {
        StartSmall copy = (StartSmall) super.makeCopy();
        copy.misc = this.misc;
        copy.syncGainFromMisc();
        copy.updateCardDescription();
        return copy;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            updateCardDescription();
        }
    }

    private void syncGainFromMisc() {
        this.baseMagicNumber = BASE_LIONSHEART + this.misc;
        this.magicNumber = this.baseMagicNumber;
        updateCardDescription();
    }

    private int computeThreshold() {
        return BASE_THRESHOLD + (this.misc * 10);
    }


    private void updateCardDescription() {
        int threshold = computeThreshold();

        if (this.upgraded) {
            this.rawDescription = cardStrings.DESCRIPTION
                    + cardStrings.EXTENDED_DESCRIPTION[0]
                    + threshold
                    + cardStrings.EXTENDED_DESCRIPTION[1]
                    + cardStrings.EXTENDED_DESCRIPTION[2];
        } else {
            this.rawDescription = cardStrings.DESCRIPTION
                    + cardStrings.EXTENDED_DESCRIPTION[0]
                    + threshold
                    + cardStrings.EXTENDED_DESCRIPTION[1];
        }
        initializeDescription();
    }
}
