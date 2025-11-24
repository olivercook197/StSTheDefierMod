package thedefierwagdtd.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

import thedefierwagdtd.powers.CustomDexterity;


@SpirePatch(clz = GainBlockAction.class, method = "update")
public class CustomDexterityPatch {
    @SpirePrefixPatch
    public static void Prefix(GainBlockAction __instance) {
        AbstractCreature target = ReflectionHacks.getPrivate(__instance, AbstractGameAction.class, "target");
        int block = ReflectionHacks.getPrivate(__instance, AbstractGameAction.class, "amount");

        if (target != null && target.hasPower(CustomDexterity.POWER_ID)) {
            CustomDexterity dex = (CustomDexterity) target.getPower(CustomDexterity.POWER_ID);
            float modified = dex.modifyBlock(block);
            ReflectionHacks.setPrivate(__instance, AbstractGameAction.class, "amount", (int) modified);
        }
    }
}