package thedefierwagdtd.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "preBattlePrep"
)
public class ResetNourishmentAtCombatStart {

    @SpirePostfixPatch
    public static void reset(AbstractPlayer __instance) {
        NourishmentCounterField.nourishmentCount.set(__instance, 0);
    }
}
