package thedefierwagdtd.rooms;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.rooms.EventRoom;

public class CustomEventRoom extends EventRoom {

    AbstractEvent anEvent;

    public CustomEventRoom(AbstractEvent event) {
        super();                    // MUST call superclass
        this.anEvent = event;
    }

    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.event = this.anEvent;
        this.event.onEnterRoom();   // Crashed because event was null
    }
}