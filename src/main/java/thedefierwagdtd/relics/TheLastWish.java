package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.character.TheDefier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

import static com.megacrit.cardcrawl.helpers.ShaderHelper.Shader.OUTLINE;
import static javax.swing.text.html.HTML.Tag.IMG;
import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class TheLastWish extends BaseRelic {
    private static final String NAME = "TheLastWish";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.STARTER;
    private static final LandingSound SOUND = LandingSound.CLINK;

    private static final int DAMAGE_THRESHOLD = 9;

    private int damageCount = 0;

    public TheLastWish() {
        super(ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
        this.counter = 0;
    }

    public boolean canSpawn() {
        return AbstractDungeon.player instanceof TheDefier;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onLoseHp(int damageAmount) {
        if (damageAmount <= 0) return;

        this.counter++;

        if (this.counter >= DAMAGE_THRESHOLD) {
            triggerEffect();
        } else if (this.counter == DAMAGE_THRESHOLD - 1) {
            beginLongPulse();
        }
    }

    private void triggerEffect() {
        stopPulse();
        flash();

        AbstractPlayer p = AbstractDungeon.player;
        boolean inCombat = AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;

        int healAmount = (int)(p.maxHealth * 0.2f);

        // Heal 20% max HP
        if (inCombat) {
            addToTop(new HealAction(p, p, healAmount));
        } else {
            // Direct heal outside combat
            p.heal(healAmount);
        }

        // Create a random curse with the tag DEATH_CURSE
        AbstractCard curse = getRandomDeathsCurse();
        if (curse != null) {
            if (inCombat) {
                addToBot(new AddCardToDeckAction(curse));
            } else {
                AbstractDungeon.effectList.add(
                        new ShowCardAndObtainEffect(
                                curse,
                                Settings.WIDTH * 0.5f,
                                Settings.HEIGHT * 0.5f
                        )
                );
            }
        }

        this.flash();
        this.counter = 0;

    }


    private AbstractCard getRandomDeathsCurse() {
        ArrayList<AbstractCard> pool = new ArrayList<>();

        for (AbstractCard proto : CardLibrary.cards.values()) {
            if (proto == null) continue;
            if (proto.type == AbstractCard.CardType.CURSE && proto.hasTag(CustomTag.DEATH_CURSE)) {
                pool.add(proto);
            }
        }
        AbstractCard chosenProto = pool.get(AbstractDungeon.cardRandomRng.random(pool.size() - 1));
        return chosenProto.makeCopy();
    }

    @Override
    public void atBattleStart() {
        if (this.counter == 8) {
            beginPulse();
            this.pulse = true;
        }
    }

    @Override
    public void onVictory() {
        stopPulse();
    }
}
