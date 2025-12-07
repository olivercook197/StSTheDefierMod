package thedefierwagdtd.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import javassist.CtBehavior;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.relics.EndOfActBounty;

@SpirePatch(clz = AbstractRoom.class, method = "update")
public class AddRelicToBossChest {

    @SpireInsertPatch(locator = Locator.class)
    public static void insert(AbstractRoom __instance) {
        if (!(__instance instanceof MonsterRoomBoss)) return;
        if (!(AbstractDungeon.player instanceof TheDefier)) return;

        // Only add once, when reward list has just been created
        boolean justOpenedRewards = !__instance.rewards.isEmpty();
        boolean doesNotAlreadyContain = __instance.rewards.stream()
                .noneMatch(r -> r.relic instanceof EndOfActBounty);

        if (justOpenedRewards && doesNotAlreadyContain)
            __instance.addRelicToRewards(new EndOfActBounty());
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ct) throws Exception {
            return LineFinder.findInOrder(
                    ct,
                    new Matcher.MethodCallMatcher(AbstractRoom.class, "addPotionToRewards")
            );
        }
    }
}