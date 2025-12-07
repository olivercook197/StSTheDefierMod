package thedefierwagdtd.relics;


import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import thedefierwagdtd.TheDefierModWAGDTD;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.events.EndOfActBountyRewardEvent;

import static thedefierwagdtd.TheDefierModWAGDTD.TeleportEvent;
import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class EndOfActBounty extends BaseRelic {
    private static final String NAME = "EndOfActBounty";
    public static final String RELIC_ID = makeID(EndOfActBounty.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.SPECIAL;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public AbstractRoom.RoomPhase previousRoomPhase;

    public AbstractEvent previousEvent;

    public AbstractRoom previousRoom;

    public MapRoomNode previousNode;

    public boolean activate;

    public EndOfActBounty() {
        super(RELIC_ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
        this.activate = false;
    }

    public void update() {
        super.update();
        if (this.activate) {
            System.out.println(RELIC_ID + " obtained");
            flash();
            AbstractDungeon.currMapNode.taken = true;
            this.previousRoomPhase = (AbstractDungeon.getCurrRoom()).phase;
            this.previousEvent = (AbstractDungeon.getCurrRoom()).event;
            this.previousRoom = AbstractDungeon.currMapNode.room;
            this.previousNode = AbstractDungeon.currMapNode;

            TeleportEvent(EndOfActBountyRewardEvent.ID);
            ((EndOfActBountyRewardEvent)(AbstractDungeon.getCurrRoom()).event).bountyRelic = this;

            this.activate = false;
        }
    }

    public boolean canSpawn() {
        return AbstractDungeon.player instanceof TheDefier;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onEquip() {
        this.activate = true;
    }
}
