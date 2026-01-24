package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thedefierwagdtd.actions.ReduceDebuffsAction;
import thedefierwagdtd.powers.LionsHeartBuff;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class MouldedPellets extends BaseRelic {
    private static final String NAME = "MouldedPellets";
    public static final String RELIC_ID = makeID(MouldedPellets.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.SHOP;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static boolean SKILL = false, POWER = false, ATTACK = false;

    public MouldedPellets() {
        super(RELIC_ID, NAME, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atTurnStart() {
        SKILL = false;
        POWER = false;
        ATTACK = false;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            ATTACK = true;
        } else if (card.type == AbstractCard.CardType.SKILL) {
            SKILL = true;
        } else if (card.type == AbstractCard.CardType.POWER) {
            POWER = true;
        }
        if (ATTACK && SKILL && POWER) {
            flash();
            addToBot((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature) AbstractDungeon.player, this));
            addToBot((AbstractGameAction)new ReduceDebuffsAction((AbstractCreature)AbstractDungeon.player, 2));
            addToBot(new ApplyPowerAction(
                    AbstractDungeon.player,
                    AbstractDungeon.player,
                    new LionsHeartBuff(AbstractDungeon.player, 2),
                    2
            ));
            SKILL = false;
            POWER = false;
            ATTACK = false;
        }
    }

    public AbstractRelic makeCopy() {
        return new MouldedPellets();
    }

}
