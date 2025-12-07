package thedefierwagdtd.cards.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.LionsHeartBuff;
import thedefierwagdtd.util.CardStats;

public class MarkOfGreatness extends BaseCard {
    public static final String ID = makeID(MarkOfGreatness.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheDefier.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 1;
    private static final int MAGIC_NUMBER = 5;
    private static final int UPG_MAGIC_NUMBER = 4;

    public MarkOfGreatness() {
        super(ID, info);
        setDamage(DAMAGE);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot((AbstractGameAction) new VFXAction((AbstractGameEffect) new ClawEffect(m.hb.cX, m.hb.cY, Color.CYAN, Color.WHITE), 0.1F));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.NONE));
        addToBot(new ApplyPowerAction((AbstractCreature) AbstractDungeon.player, (AbstractCreature) AbstractDungeon.player,
                (AbstractPower) new LionsHeartBuff((AbstractCreature) AbstractDungeon.player, magicNumber), magicNumber));
    }

}
