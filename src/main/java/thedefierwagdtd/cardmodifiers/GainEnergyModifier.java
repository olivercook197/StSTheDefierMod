package thedefierwagdtd.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thedefierwagdtd.TheDefierModWAGDTD;
import thedefierwagdtd.powers.UnfazedPower;

public class GainEnergyModifier extends AbstractCardModifier {

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + (CardCrawlGame.languagePack.getUIString(TheDefierModWAGDTD.makeID("CardModifiers"))).TEXT[2];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(
                new GainEnergyAction(1));
    }

    public AbstractCardModifier makeCopy() {
        return new GainEnergyModifier();
    }
}
