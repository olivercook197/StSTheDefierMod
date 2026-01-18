package thedefierwagdtd.relics;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.CautionPower;

import java.util.ArrayList;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class TotemOfLife extends BaseRelic {
    private static final String NAME = "TotemOfLife";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.CLINK;

    private static final int DAMAGE_THRESHOLD = 9;

    private int damageCount = 0;

    public TotemOfLife() {
        super(ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
        this.counter = 0;
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(TheLastWish.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(TheLastWish.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public String getUpdatedDescription() {
        String name = new TheLastWish().name;
        StringBuilder sb = new StringBuilder();
        Color wordColour = new Color(175f/255f, 87f/255f, 0f/255f, 1f);
        for (String word : name.split(" ")) {
            sb.append("[#").append(wordColour.toString()).append("]").append(word).append("[] ");
        }
        sb.setLength(sb.length() - 1);
        sb.append("[#").append(wordColour.toString()).append("]");

        return DESCRIPTIONS[0] + sb + DESCRIPTIONS[1];
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(TheLastWish.ID);
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

        int healAmount = (int)(AbstractDungeon.player.maxHealth * 0.2f);
        addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, healAmount));

        AbstractCard curse = getRandomDeathsCurse();
        if (curse != null) {
            addToBot(new AddCardToDeckAction(curse));
        }

        addToTop(new ApplyPowerAction(
                AbstractDungeon.player,
                AbstractDungeon.player,
                new IntangiblePlayerPower(AbstractDungeon.player, 1),
                1
        ));

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
