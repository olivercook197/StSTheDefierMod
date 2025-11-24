package thedefierwagdtd.cards.curses;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.util.CardStats;

import java.util.ArrayList;

public class Choked extends BaseCard {
    public static final String ID = makeID(Choked.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.CURSE,
            CardType.CURSE,
            CardRarity.CURSE,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 5;

    public Choked() {
        super(ID, info);
        setMagic(MAGIC);
        tags.add(CustomTag.RECKLESS);
        tags.add(CustomTag.DEATH_CURSE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction) new GainGoldAction(this.magicNumber));

        ArrayList<AbstractCard> playable = new ArrayList<>();
        for (AbstractCard c : p.hand.group) {
            if (c.costForTurn >= 0 && c != this) {
                playable.add(c);
            }
        }

        if (!playable.isEmpty()) {
            AbstractCard chosen = playable.get(AbstractDungeon.cardRandomRng.random(playable.size() - 1));
            
            chosen.costForTurn += 1;
            chosen.isCostModifiedForTurn = true;

            chosen.superFlash(Color.RED.cpy());
        }
    }
}
