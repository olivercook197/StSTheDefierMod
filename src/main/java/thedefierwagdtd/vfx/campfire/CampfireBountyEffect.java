package thedefierwagdtd.vfx.campfire;

import basemod.CustomEventRoom;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import thedefierwagdtd.events.EndOfActBountyRewardEvent;

import java.util.ArrayList;

public class CampfireBountyEffect extends AbstractGameEffect {
    private static final float DUR = 2.0F;

    private Color screenColor;

    public CampfireBountyEffect() {
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 2.0F;
        this.screenColor.a = 0.0F;
        if (AbstractDungeon.getCurrRoom() instanceof RestRoom)
            ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
    }

    public static String generateEventName() {
        return EndOfActBountyRewardEvent.ID;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        updateBlackScreenColor();
        if (this.duration < 1.0F) {
            this.isDone = true;
            CardCrawlGame.music.unsilenceBGM();
            String eventName = generateEventName();
            AbstractDungeon.eventList.add(0, eventName);
            MapRoomNode cur = AbstractDungeon.currMapNode;
            MapRoomNode node = new MapRoomNode(cur.x, cur.y);
            node.room = (AbstractRoom)new CustomEventRoom();
            ArrayList<MapEdge> curEdges = cur.getEdges();
            for (MapEdge edge : curEdges)
                node.addEdge(edge);
            AbstractDungeon.previousScreen = null;
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.dungeonMapScreen.closeInstantly();
            AbstractDungeon.closeCurrentScreen();
            AbstractDungeon.topPanel.unhoverHitboxes();
            AbstractDungeon.fadeIn();
            AbstractDungeon.dungeonMapScreen.dismissable = true;
            AbstractDungeon.setCurrMapNode(AbstractDungeon.nextRoom = node);
            AbstractDungeon.getCurrRoom().onPlayerEntry();
            AbstractDungeon.scene.nextRoom(node.room);
            AbstractDungeon.rs = AbstractDungeon.RenderScene.EVENT;
        }
    }

    private void updateBlackScreenColor() {
        if (this.duration > 1.5F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.5F) * 2.0F);
        } else if (this.duration < 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration);
        } else {
            this.screenColor.a = 1.0F;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }

    public void dispose() {}
}
