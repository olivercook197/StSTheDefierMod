package thedefierwagdtd.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thedefierwagdtd.TheDefierModWAGDTD;
import thedefierwagdtd.powers.CautionPower;
import thedefierwagdtd.powers.UnfazedPower;

public class GainCautionModifier extends AbstractCardModifier {
    public static final String ID = "thedefierwagdtd:GainCautionModifier";

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + (CardCrawlGame.languagePack.getUIString(TheDefierModWAGDTD.makeID("CardModifiers"))).TEXT[3];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new CautionPower(AbstractDungeon.player, 3), 3)
        );
    }

    public AbstractCardModifier makeCopy() {
        return new GainCautionModifier();
    }
}
