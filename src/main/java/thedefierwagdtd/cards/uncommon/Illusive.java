package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.actions.ChooseCardToRemoveFromDeckAction;
import thedefierwagdtd.actions.ChooseCardToRetainAndDiscountAction;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.CardStats;

public class Illusive extends BaseCard {
    public static final String ID = makeID(Illusive.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            1
    );

    public Illusive() {
        super(ID, info);
        tags.add(CustomTag.RECKLESS);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            tags.remove(CustomTag.RECKLESS);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChooseCardToRemoveFromDeckAction(1));
        AbstractDungeon.player.masterDeck.removeCard(this.cardID);
        this.purgeOnUse = true;
    }
}
