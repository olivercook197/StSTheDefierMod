package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.character.TheDefier;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class CorinthianLeatherBoots extends BaseRelic {
    private static final String NAME = "CorinthianLeatherBoots";
    public static final String RELIC_ID = makeID(CorinthianLeatherBoots.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.MAGICAL;

    public CorinthianLeatherBoots() {
        super(RELIC_ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(CustomTag.RECKLESS)) {
            flash();
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,new StrengthPower(AbstractDungeon.player, 3)));
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,new LoseStrengthPower(AbstractDungeon.player, 3)));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CorinthianLeatherBoots();
    }
}
