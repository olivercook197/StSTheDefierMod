package thedefierwagdtd.ui.campfire;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import thedefierwagdtd.cards.common.SmoulderingSneer;
import thedefierwagdtd.events.EndOfActBountyRewardEvent;
import thedefierwagdtd.vfx.campfire.CampfireBountyEffect;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class EndOfActBountyOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("thedefierwagdtd:EndOfActBountyOption");
    public static final String[] TEXT = uiStrings.TEXT;

    public EndOfActBountyOption() {
        this.label = TEXT[0];
        this.description = TEXT[1];
        this.img = ImageMaster.CAMPFIRE_TRAIN_BUTTON;
        this.usable = true;
    }

    @Override
    public void useOption() {
        AbstractDungeon.effectList.add(new CampfireBountyEffect());
    }
}
