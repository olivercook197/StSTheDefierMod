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

public class TwoVulnerableToEveryoneModifier extends AbstractCardModifier {
    public static final String ID = "thedefierwagdtd:TwoVulnerableToEveryoneModifier";

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + (CardCrawlGame.languagePack.getUIString(TheDefierModWAGDTD.makeID("CardModifiers"))).TEXT[0];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {

        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped()) {
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(m, AbstractDungeon.player,
                                new VulnerablePower(m, 2, false), 2)
                );
            }
        }

        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new VulnerablePower(AbstractDungeon.player, 2, false), 2)
        );
    }

    public AbstractCardModifier makeCopy() {
        return new TwoVulnerableToEveryoneModifier();
    }
}