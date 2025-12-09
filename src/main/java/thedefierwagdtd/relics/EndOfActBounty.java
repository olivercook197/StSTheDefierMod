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

    public boolean activate;

    public EndOfActBounty() {
        super(RELIC_ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
        this.activate = false;
    }

    public boolean canSpawn() {
        return AbstractDungeon.player instanceof TheDefier;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
