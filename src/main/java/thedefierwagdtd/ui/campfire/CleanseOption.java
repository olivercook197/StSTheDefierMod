package thedefierwagdtd.ui.campfire;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.TextureLoader;

public class CleanseOption extends AbstractCampfireOption {

    private static final UIStrings uiStrings =
            CardCrawlGame.languagePack.getUIString("thedefierwagdtd:CampfireCleanseEffect");

    private static final Texture IMG =
            TextureLoader.getTexture("thedefierwagdtd/images/ui/Cleanse.png");

    public CleanseOption() {
        this.label = uiStrings.TEXT[0];
        this.description = uiStrings.TEXT[1];
        this.img = IMG;
        this.usable = AbstractDungeon.player instanceof TheDefier;
    }

    @Override
    public void useOption() {
        if (this.usable) {
            CleanseEffect e = new CleanseEffect();
            AbstractDungeon.effectList.add(e);
        }
    }
}

