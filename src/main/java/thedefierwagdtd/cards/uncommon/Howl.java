package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.CardStats;

public class Howl extends BaseCard {
    public static final String ID = makeID(Howl.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            0
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;

    public Howl() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        addToBot(new DrawCardAction(p, 1));

        addToBot(new WaitAction(0.15F));

        addToBot(new AbstractGameAction() {
            public void update() {
                if (!DrawCardAction.drawnCards.isEmpty()) {
                    AbstractCard drawnCard = DrawCardAction.drawnCards.get(DrawCardAction.drawnCards.size() - 1);
                    if (drawnCard != null) {
                        drawnCard.setCostForTurn(2);
                        drawnCard.isCostModifiedForTurn = true;

                        drawnCard.superFlash();
                        drawnCard.applyPowers();
                    }

                    DrawCardAction.drawnCards.clear();
                }
                this.isDone = true;
            }
        });
    }
}
