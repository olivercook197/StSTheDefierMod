package thedefierwagdtd.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.LionsHeartBuff;
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

    private static final int MAGIC_NUMBER = 4;
    private static final int UPG_MAGIC_NUMBER = 4;

    public LingeringTingle() {
        super(ID, info);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction((AbstractCreature) AbstractDungeon.player, (AbstractCreature) AbstractDungeon.player,
                (AbstractPower) new LionsHeartBuff((AbstractCreature) AbstractDungeon.player, magicNumber), magicNumber));

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