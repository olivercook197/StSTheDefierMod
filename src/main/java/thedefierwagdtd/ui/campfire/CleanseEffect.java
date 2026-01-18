package thedefierwagdtd.ui.campfire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.ArrayList;
import java.util.Collections;

public class CleanseEffect extends AbstractGameEffect {
    private static final float DUR = 1.5F;
    private boolean executed = false;
    private Color screenColor = AbstractDungeon.fadeColor.cpy();

    public CleanseEffect() {
        this.duration = DUR;
        this.screenColor.a = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    @Override
    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            updateBlackScreenColor();
        }

        if (!executed && this.duration < 1.0F) {
            executed = true;
            purgeCurses();
        }

        if (this.duration < 0.0F) {
            this.isDone = true;

            if (CampfireUI.hidden) {
                AbstractRoom.waitTimer = 0.0F;
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();
            }
        }
    }

    private void purgeCurses() {
        ArrayList<AbstractCard> curses = new ArrayList<>();

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.type == AbstractCard.CardType.CURSE) {
                curses.add(c);
            }
        }

        if (curses.isEmpty()) return;

        Collections.shuffle(curses, AbstractDungeon.miscRng.random);
        int removeCount = Math.min(2, curses.size());

        for (int i = 0; i < removeCount; i++) {
            AbstractCard curse = curses.get(i);

            AbstractDungeon.player.masterDeck.removeCard(curse);
            CardCrawlGame.sound.play("CARD_EXHAUST");

            AbstractDungeon.topLevelEffects.add(
                    new PurgeCardEffect(
                            curse,
                            Settings.WIDTH / 2f,
                            Settings.HEIGHT / 2f
                    )
            );
        }
    }

    private void updateBlackScreenColor() {
        if (this.duration > 1.0F) {
            this.screenColor.a = com.badlogic.gdx.math.Interpolation.fade.apply(
                    1.0F, 0.0F, (this.duration - 1.0F) * 2.0F
            );
        } else {
            this.screenColor.a = com.badlogic.gdx.math.Interpolation.fade.apply(
                    0.0F, 1.0F, this.duration / DUR
            );
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0, 0, Settings.WIDTH, Settings.HEIGHT);
    }

    @Override
    public void dispose() {}
}
