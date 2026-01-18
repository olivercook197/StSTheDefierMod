package thedefierwagdtd.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thedefierwagdtd.TheDefierModWAGDTD;
import thedefierwagdtd.powers.LionsHeartBuff;

public class GainLionsHeartModifier extends AbstractCardModifier {
    public static final String ID = "thedefierwagdtd:GainLionsHeartModifier";

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + (CardCrawlGame.languagePack.getUIString(TheDefierModWAGDTD.makeID("CardModifiers"))).TEXT[4];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new LionsHeartBuff(AbstractDungeon.player, 4), 4)
        );
    }

    public AbstractCardModifier makeCopy() {
        return new GainLionsHeartModifier();
    }
}
