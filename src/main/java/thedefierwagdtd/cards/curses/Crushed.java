package thedefierwagdtd.cards.curses;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.powers.LionsHeartBuff;
import thedefierwagdtd.util.CardStats;

public class Crushed extends BaseCard {
    public static final String ID = makeID(Crushed.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.CURSE,
            CardType.CURSE,
            CardRarity.CURSE,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 6;

    public Crushed() {
        super(ID, info);
        setMagic(MAGIC);
        tags.add(CustomTag.RECKLESS);
        tags.add(CustomTag.DEATH_CURSE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster randomEnemy = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (randomEnemy != null && !randomEnemy.isDeadOrEscaped()) {
            addToBot(new ApplyPowerAction(randomEnemy, p,
                    new StrengthPower(randomEnemy, 2), 2));
            addToBot(new ApplyPowerAction(randomEnemy, p,
                    new LoseStrengthPower(randomEnemy, 2), 2));
        }
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                (AbstractPower)new LionsHeartBuff((AbstractCreature)p, this.magicNumber), this.magicNumber));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return true;
    }

    public boolean canSpawn() {
        return false;
    }
}