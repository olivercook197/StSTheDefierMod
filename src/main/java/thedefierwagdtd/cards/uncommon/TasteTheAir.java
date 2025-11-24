package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.LionsHeartBuff;
import thedefierwagdtd.util.CardStats;

public class TasteTheAir extends BaseCard {
    public static final String ID = makeID(TasteTheAir.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int BASE_AMOUNT = 2;
    private static final int GROWTH = 2;
    private static final int UPG_GROWTH = 3;

    public TasteTheAir() {
        super(ID, info);
        this.exhaust = true;

        this.misc = BASE_AMOUNT;
        this.magicNumber = this.baseMagicNumber = this.misc;
    }

    @Override
    public void applyPowers() {
        this.magicNumber = this.baseMagicNumber = this.misc;
        super.applyPowers();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));

        int increase = this.upgraded ? UPG_GROWTH : GROWTH;
        addToBot(new IncreaseMiscAction(this.uuid, this.misc, increase));
    }
}