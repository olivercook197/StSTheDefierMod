package thedefierwagdtd.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
public class NourishmentCounterField {
    public static SpireField<Integer> nourishmentCount =
            new SpireField<>(() -> 0);
}