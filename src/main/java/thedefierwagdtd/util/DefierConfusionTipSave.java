package thedefierwagdtd.util;

import basemod.abstracts.CustomSavable;

public class DefierConfusionTipSave implements CustomSavable<Boolean> {

    public static boolean shown = false;

    @Override
    public Boolean onSave() {
        return shown;
    }

    @Override
    public void onLoad(Boolean value) {
        shown = value != null && value;
    }
}
