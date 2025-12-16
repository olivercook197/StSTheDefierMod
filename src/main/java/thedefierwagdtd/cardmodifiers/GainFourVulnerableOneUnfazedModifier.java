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
import thedefierwagdtd.powers.UnfazedPower;

public class GainFourVulnerableOneUnfazedModifier extends AbstractCardModifier {
    public static final String ID = "thedefierwagdtd:GainFourVulnerableOneUnphasedModifier";

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + (CardCrawlGame.languagePack.getUIString(TheDefierModWAGDTD.makeID("CardModifiers"))).TEXT[1];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new VulnerablePower(AbstractDungeon.player, 4, false), 4)
        );
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new UnfazedPower(AbstractDungeon.player, 1), 1)
        );
    }

    public AbstractCardModifier makeCopy() {
        return new GainFourVulnerableOneUnfazedModifier();
    }
}
