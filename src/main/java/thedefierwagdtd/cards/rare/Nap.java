package thedefierwagdtd.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.NapPower;
import thedefierwagdtd.util.CardStats;

public class Nap extends BaseCard {
    public static final String ID = makeID(Nap.class.getSimpleName());

    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.RARE,
            AbstractCard.CardTarget.SELF,
            0
    );

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 5;
    private static final int WEAK = 1;
    private static final int UPG_WEAK = 1;
    private static final int TEMP_STRENGTH = 5;
    private static final int UPG_TEMP_STRENGTH = 2;

    public Nap() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(WEAK, UPG_WEAK);
        setCustomVar("STRENGTH", TEMP_STRENGTH, UPG_TEMP_STRENGTH);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p,
                new NapPower(p, block, magicNumber, customVar("STRENGTH"))));
        addToBot((AbstractGameAction)new PressEndTurnButtonAction());
    }
}