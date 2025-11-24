package thedefierwagdtd.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import thedefierwagdtd.TheDefierModWAGDTD;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.util.CardStats;

public class KillingField extends BaseCard {
    public static final String ID = makeID(KillingField.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            3
    );

    private static final int DAMAGE = 24;
    private static final int UPG_DAMAGE = 6;
    private static final int MAGIC_NUMBER = 3;
    private static final int MAGIC_NUMBER_UPG = 2;

    public KillingField() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC_NUMBER, MAGIC_NUMBER_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.RED, true));

        addToBot(new WaitAction(0.2F));

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                if (Settings.FAST_MODE) {
                    addToBot(new VFXAction(
                            new BiteEffect(mo.hb.cX, mo.hb.cY - 40.0F * Settings.scale, Settings.GOLD_COLOR.cpy()),
                            0.1F));
                } else {
                    addToBot(new VFXAction(
                            new BiteEffect(mo.hb.cX, mo.hb.cY - 40.0F * Settings.scale, Settings.GOLD_COLOR.cpy()),
                            0.3F));
                }

                addToBot(new DamageAction(
                        mo,
                        new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.NONE));
            }
        }

        addToBot(new ApplyPowerAction(
                p, p,
                new StrengthPower(p, this.magicNumber),
                this.magicNumber));
    }
}
