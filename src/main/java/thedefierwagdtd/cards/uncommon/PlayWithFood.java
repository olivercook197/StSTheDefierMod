package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.KeenSensesPower;
import thedefierwagdtd.util.CardStats;

public class PlayWithFood extends BaseCard {
    public static final String ID = makeID(PlayWithFood.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            0
    );

    private static final int MAGIC_NUMBER = 2;
    private static final int BLOCK = 4;

    public PlayWithFood() {
        super(ID, info);
        setMagic(MAGIC_NUMBER);
        setBlock(BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded){
            addToBot((AbstractGameAction)new GainBlockAction(p, p, this.block));
        }
        addToBot((AbstractGameAction) new GainEnergyAction(this.magicNumber));
        AbstractMonster randomEnemy = AbstractDungeon.getMonsters()
                .getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        addToBot((AbstractGameAction)new ApplyPowerAction(randomEnemy, p,
                (AbstractPower)new GainStrengthPower(randomEnemy, 1), 1));
    }
}
