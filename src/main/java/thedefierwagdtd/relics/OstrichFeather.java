package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thedefierwagdtd.TheDefierModWAGDTD;
import thedefierwagdtd.character.TheDefier;


import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class OstrichFeather extends BaseRelic{
    private static final String NAME = "OstrichFeather";
    public static final String RELIC_ID = makeID(OstrichFeather.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.MAGICAL;

    public OstrichFeather() {
        super(RELIC_ID, NAME, RARITY, SOUND);
    }

    @Override
    public boolean canSpawn() {
        if (AbstractDungeon.player instanceof TheDefier) {
            return true;
        }

        return TheDefierModWAGDTD.enableOtherCharRelics;
    }


    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        this.counter = 0;
    }

    public void onVictory() {
        int curseCount = this.counter;

        if (curseCount > 0) {
            flash();
            AbstractDungeon.player.gainGold(curseCount);
        }
    }

    public void onMasterDeckChange() {
        this.counter = 0;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.type == AbstractCard.CardType.CURSE)
                this.counter++;
        }
    }
}
