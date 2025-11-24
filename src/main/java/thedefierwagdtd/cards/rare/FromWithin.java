package thedefierwagdtd.cards.rare;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.LionsHeartBuff;
import thedefierwagdtd.util.CardStats;

public class FromWithin extends BaseCard {
    public static final String ID = makeID(FromWithin.class.getSimpleName());

    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 0;
    private static final int MAGIC_NUMBER = 3;
    private static final int UPG_MAGIC_NUMBER = -1;

    public FromWithin() {
        super(ID, info);
        setDamage(DAMAGE);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower lionsHeart = p.getPower(LionsHeartBuff.POWER_ID);
        int heartAmount = lionsHeart != null ? lionsHeart.amount : 0;

        int tier = 0;
        if (heartAmount >= 50) tier = 4;
        else if (heartAmount >= 25) tier = 3;
        else if (heartAmount >= 10) tier = 2;
        else if (heartAmount >= 1) tier = 1;

        int waves = Math.max(1, tier);

        Color auraColor;
        float roarVolume;
        switch (tier) {
            case 4:
                auraColor = new Color(1.0F, 0.95F, 0.8F, 1.0F);
                roarVolume = 1.0F;
                break;

            case 3:
                auraColor = new Color(1.0F, 0.85F, 0.3F, 1.0F);
                roarVolume = 0.8F;
                break;

            case 2:
                auraColor = new Color(1.0F, 0.75F, 0.2F, 1.0F);
                roarVolume = 0.6F;
                break;

            case 1:
                auraColor = new Color(1.0F, 0.65F, 0.1F, 1.0F);
                roarVolume = 0.4F;
                break;

            default:
                auraColor = Color.GOLD.cpy();
                roarVolume = 0.3F;
                break;
        }

        addToBot(new VFXAction(new BorderFlashEffect(auraColor, true)));
        addToBot(new VFXAction(new InflameEffect(p)));
        addToBot(new SFXAction("POWER_STRENGTH", 0.2F));

        if (tier >= 4) {
            addToBot(new SFXAction("MONSTER_GUARDIAN_ROAR", roarVolume));
            addToBot(new VFXAction(new BorderFlashEffect(Color.WHITE.cpy(), true)));
            addToBot(new VFXAction(new ExplosionSmallEffect(p.hb.cX, p.hb.cY)));
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.LONG, false);
        } else if (tier >= 3) {
            addToBot(new SFXAction("MONSTER_GUARDIAN_ROAR", roarVolume));
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
        }

        for (int i = 0; i < waves; i++) {
            addToBot(new VFXAction(
                    new ShockWaveEffect(p.hb.cX, p.hb.cY, auraColor.cpy(), ShockWaveEffect.ShockWaveType.ADDITIVE),
                    0.15F
            ));
        }

        addToBot(new DamageAction(
                m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        ));

        addToBot(new ApplyPowerAction(p, p,
                new VulnerablePower(p, 2, false), 2));
    }

    public void applyPowers() {
        AbstractPower lionsheart = AbstractDungeon.player.getPower(LionsHeartBuff.POWER_ID);
        int amount = (lionsheart == null ? 0 : lionsheart.amount);

        this.baseDamage = amount / this.magicNumber;
        super.applyPowers();
        if (this.upgraded) {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }
        else {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void onMoveToDiscard() {
        if (this.upgraded) {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        } else {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower lionsheart = AbstractDungeon.player.getPower(LionsHeartBuff.POWER_ID);
        int amount = (lionsheart == null ? 0 : lionsheart.amount);

        this.baseDamage = amount / this.magicNumber;

        super.calculateCardDamage(mo);

        if (this.upgraded) {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        } else {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void displayUpgrades() {
        super.displayUpgrades();

        if (upgraded) {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION
                    .replace("half", "[#7fff00]half[]");
            initializeDescription();
        }
    }
}
