package thedefierwagdtd.cards.curses;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.CardStats;

import java.util.ArrayList;

public class Burned extends BaseCard {
    public static final String ID = makeID(Burned.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.CURSE,
            CardType.CURSE,
            CardRarity.CURSE,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 2;

    public Burned() {
        super(ID, info);
        setMagic(MAGIC);
        tags.add(CustomTag.RECKLESS);
        tags.add(CustomTag.DEATH_CURSE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WeakPower(p, 1, false), 1));

        ArrayList<AbstractCard> attackCards = new ArrayList<>();
        for (AbstractCard c : p.hand.group) {
            if (c.type == CardType.ATTACK) {
                attackCards.add(c);
            }
        }

        int toBuff = Math.min(2, attackCards.size());
        for (int i = 0; i < toBuff; i++) {
            AbstractCard chosen = attackCards.remove(AbstractDungeon.cardRandomRng.random(attackCards.size() - 1));

            chosen.baseDamage += this.magicNumber;
            chosen.applyPowers();

            chosen.superFlash(Color.ORANGE.cpy());
            AbstractDungeon.effectList.add(
                    new com.megacrit.cardcrawl.vfx.UpgradeShineEffect(
                            p.hb.cX, p.hb.cY
                    )
            );
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return true;
    }
}