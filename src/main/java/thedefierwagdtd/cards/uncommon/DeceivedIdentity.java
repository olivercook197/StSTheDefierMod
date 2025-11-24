package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.cards.optionCards.Mirrors;
import thedefierwagdtd.cards.optionCards.Smoke;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.LionsHeartBuff;
import thedefierwagdtd.util.CardStats;

import java.util.ArrayList;

public class DeceivedIdentity extends BaseCard {
    public static final String ID = makeID(DeceivedIdentity.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;
    private static final int LIONSHEART_COST = 8;
    private static final int LIONSHEART_COST_UPG = -3;

    public DeceivedIdentity() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(LIONSHEART_COST, LIONSHEART_COST_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> options = new ArrayList<>();
        options.add(new Smoke(m, this.damage));
        options.add(new Mirrors(m, magicNumber, this.damage));

        if (upgraded) {
            for (AbstractCard c : options) c.upgrade();
        }

        addToBot(new ChooseOneAction(options));
    }
}
