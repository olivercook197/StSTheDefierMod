package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.CardStats;

import java.util.ArrayList;

import static thedefierwagdtd.CustomTags.CustomTag.RECKLESS;

public class LingeringTingle extends BaseCard {
    public static final String ID = makeID(LingeringTingle.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    public LingeringTingle() {
        super(ID, info);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> recklessAttacks = new ArrayList<>();

        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (c.type != CardType.CURSE && c.hasTag(RECKLESS)) {
                recklessAttacks.add(c.makeStatEquivalentCopy());
            }
        }

        if (!recklessAttacks.isEmpty()) {
            AbstractCard randomCard = recklessAttacks.get(AbstractDungeon.cardRandomRng.random(recklessAttacks.size() - 1));

            if (this.upgraded && randomCard.canUpgrade()) {
                randomCard.upgrade();
            }

            addToBot(new MakeTempCardInHandAction(randomCard, 1));
        }
    }
}