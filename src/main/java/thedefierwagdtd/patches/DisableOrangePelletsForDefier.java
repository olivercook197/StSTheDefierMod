package thedefierwagdtd.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.OrangePellets;
import thedefierwagdtd.character.TheDefier;

@SpirePatch(
        clz = AbstractRelic.class,
        method = "canSpawn"
)
public class DisableOrangePelletsForDefier {

    @SpirePostfixPatch
    public static boolean blockOrangePellets(boolean __result, AbstractRelic __instance) {
        AbstractPlayer p = AbstractDungeon.player;

        if (p instanceof TheDefier && __instance instanceof OrangePellets) {
            return false;
        }

        return __result;
    }
}