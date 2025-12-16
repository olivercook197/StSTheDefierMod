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
import thedefierwagdtd.powers.LionsHeartBuff;
import thedefierwagdtd.util.CardStats;

public class Valiance extends BaseCard {
    public static final String ID = makeID(Valiance.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int BLOCK = 0;
    private static final int BLOCK_UPG = 4;
    private static final int MAGIC_NUMBER = 2;


    public Valiance() {
        super(ID, info);
        setBlock(BLOCK, BLOCK_UPG);
        setMagic(MAGIC_NUMBER);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower str = p.getPower(StrengthPower.POWER_ID);
        int amount = (str == null ? 0 : str.amount);
        addToBot((AbstractGameAction)new GainBlockAction((AbstractCreature)p, (AbstractCreature)p,
                this.block + amount * this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new RegenPower(p, 2)));
    }

    public void applyPowers() {
        AbstractPlayer p = AbstractDungeon.player;

        AbstractPower str = p.getPower(StrengthPower.POWER_ID);
        int strengthAmount = (str == null ? 0 : str.amount);

        super.applyPowers();

        if (!upgraded) {
            this.rawDescription = cardStrings.DESCRIPTION;
        } else {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }

        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + (this.block + strengthAmount * this.magicNumber)
                + cardStrings.EXTENDED_DESCRIPTION[1];

        initializeDescription();
    }
}
