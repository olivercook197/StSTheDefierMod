package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import thedefierwagdtd.character.TheDefier;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;
//How can I update this relic so that the card reward obtained is a rare card?
public class BlueBowtie extends BaseRelic {
    private static final String NAME = "BlueBowtie";
    public static final String RELIC_ID = makeID(BlueBowtie.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.SPECIAL;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public BlueBowtie() {
        super(RELIC_ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    public void onEquip() {
        RewardItem rareReward = new RewardItem();
        rareReward.cards.clear();
        for (int i = 0; i < 3; i++) {
            rareReward.cards.add(AbstractDungeon.getCard(AbstractCard.CardRarity.RARE));
        }

        AbstractDungeon.getCurrRoom().rewards.add(rareReward);

        AbstractDungeon.combatRewardScreen.open(this.DESCRIPTIONS[1]);

        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.0F;
    }
}
