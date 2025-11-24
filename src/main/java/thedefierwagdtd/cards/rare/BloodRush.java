package thedefierwagdtd.cards.rare;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.LionsHeartBuff;
import thedefierwagdtd.util.CardStats;

public class BloodRush extends BaseCard {
    public static final String ID = makeID(BloodRush.class.getSimpleName());

    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 1;
    private static final int MAGIC_NUMBER = 6;
    private static final int UPG_MAGIC_NUMBER = -1;

    public BloodRush() {
        super(ID, info);
        setDamage(DAMAGE);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new BorderFlashEffect(Color.SCARLET.cpy(), true)));
        addToBot(new VFXAction(new InflameEffect(p)));
        addToBot(new VFXAction(new ShockWaveEffect(
                p.hb.cX, p.hb.cY,
                Color.RED.cpy(),
                ShockWaveEffect.ShockWaveType.ADDITIVE
        )));

        AbstractPower lionsHeart = p.getPower(LionsHeartBuff.POWER_ID);
        int heartAmount = (lionsHeart != null ? lionsHeart.amount : 0);
        int hits = heartAmount / this.magicNumber;

        if (hits >= 5) {
            for (int i = 0; i < 3; i++) {
                addToBot(new VFXAction(new ExplosionSmallEffect(
                        m.hb.cX + AbstractDungeon.miscRng.random(-30F, 30F),
                        m.hb.cY + AbstractDungeon.miscRng.random(-20F, 20F)
                ), 0.1F));
            }
        }

        for (int i = -1; i < hits; i++) {
            addToBot(new DamageAction(
                    m,
                    new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL
            ));
        }

        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p, 3, false), 3));
    }

    public void applyPowers() {
        super.applyPowers();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractPower lionsHeart = p.getPower(LionsHeartBuff.POWER_ID);
        int heartAmount = lionsHeart != null ? lionsHeart.amount : 0;
        int adjustedHits = heartAmount / this.magicNumber + 1;

        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + adjustedHits + cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }
}
