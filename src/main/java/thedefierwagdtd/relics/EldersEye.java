package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.CautionPower;

import java.util.ArrayList;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class EldersEye extends BaseRelic {
    private static final String NAME = "EldersEye";
    public static final String RELIC_ID = makeID(EldersEye.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public EldersEye() {
        super(RELIC_ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public boolean canSpawn() {
        return AbstractDungeon.player instanceof TheDefier;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        flash();
        addToTop(new ApplyPowerAction(
                AbstractDungeon.player,
                AbstractDungeon.player,
                new CautionPower(AbstractDungeon.player, 1),
                1
        ));

        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public void atTurnStart() {
        flash();
        addToTop(new ApplyPowerAction(
                AbstractDungeon.player,
                AbstractDungeon.player,
                new CautionPower(AbstractDungeon.player, 1),
                1
        ));
    }
}
