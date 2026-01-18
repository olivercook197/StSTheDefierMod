package thedefierwagdtd.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.powers.CautionPower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static thedefierwagdtd.TheDefierModWAGDTD.makeID;

public class Deaths extends BaseRelic {
    private static final String NAME = "Deaths";
    public static final String RELIC_ID = makeID(Deaths.class.getSimpleName());
    private static final RelicTier RARITY = RelicTier.SPECIAL;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public Deaths() {
        super(RELIC_ID, NAME, TheDefier.Meta.CARD_COLOR, RARITY, SOUND);
        this.counter = 1;
    }
    public int max_counter = 9;
    AbstractPlayer p = AbstractDungeon.player;

    public void onEquip() {
        updateDescription();
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[11];
    }

    public void onVictory() {

        if (this.counter >= 1) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    ArrayList<AbstractCard> curseCards = new ArrayList<>();
                    for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                        if (c.type == AbstractCard.CardType.CURSE) {
                            curseCards.add(c);
                        }
                    }

                    if (!curseCards.isEmpty()) {
                        Collections.shuffle(curseCards, new Random(AbstractDungeon.miscRng.randomLong()));
                        AbstractCard curse = curseCards.get(0);

                        AbstractDungeon.player.masterDeck.removeCard(curse);
                        CardCrawlGame.sound.play("CARD_EXHAUST");
                        AbstractDungeon.topLevelEffects.add(
                                new PurgeCardEffect(
                                        curse,
                                        Settings.WIDTH / 2f,
                                        Settings.HEIGHT / 2f
                                )
                        );
                    }

                    isDone = true;
                }
            });
        }


        if (this.counter >= 3) {
            p.increaseMaxHp(2, false);
        }

        if (this.counter >= 8) {
            ArrayList<AbstractCard> upgradableCards = new ArrayList<>();
            for (AbstractCard c : p.masterDeck.group) {
                if (c.canUpgrade())
                    upgradableCards.add(c);
            }
            Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
            if (!upgradableCards.isEmpty()) {
                ((AbstractCard) upgradableCards.get(0)).upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard) upgradableCards
                        .get(0)).makeStatEquivalentCopy()));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            }
        }

        if (this.counter >= 9) {
            p.maxHealth = 1;
        }
    }

    public void atBattleStart() {
        updateDescription();
        if (this.counter >= 2) {
            flash();
            addToTop(new ApplyPowerAction(p, p, new CautionPower(p, 5), 5));
        }

        if (this.counter >= 5) {
            flash();
            addToTop(new ApplyPowerAction(p, p, new MetallicizePower(p, 3)));
        }

        if (this.counter >= 6) {
            flash();
            addToTop(new ApplyPowerAction(p, p, new RegenPower(p, 3)));
        }

    }
    public void atTurnStart() {
        if (this.counter >= 4) {
            flash();
            addToBot(new DrawCardAction(p, 1));
        }

        if (this.counter >= 7) {
            flash();
            addToBot(new GainEnergyAction(1));
        }
    }

    public AbstractRelic makeCopy() {
        return new Deaths();
    }

    public void incrementCounter() {
        if (this.counter < max_counter) {
            this.counter++;
            updateDescription();
        }
    }

    public void updateDescription() {
        if (DESCRIPTIONS == null) {
            return;
        }
        if (this.counter == 1) {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + DESCRIPTIONS[1] + DESCRIPTIONS[3];
        }
        else if (this.counter == 2) {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + DESCRIPTIONS[3] + DESCRIPTIONS[1] + DESCRIPTIONS[4];
        }
        else if (this.counter == 3) {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + DESCRIPTIONS[3] + DESCRIPTIONS[4] + DESCRIPTIONS[1]
                    + DESCRIPTIONS[5];
        }
        else if (this.counter == 4) {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + DESCRIPTIONS[3] + DESCRIPTIONS[4] + DESCRIPTIONS[5]
                    + DESCRIPTIONS[1] + DESCRIPTIONS[6];
        }
        else if (this.counter == 5) {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + DESCRIPTIONS[3] + DESCRIPTIONS[4] + DESCRIPTIONS[5]
                    + DESCRIPTIONS[6] + DESCRIPTIONS[1] + DESCRIPTIONS[7];
        }
        else if (this.counter == 6) {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + DESCRIPTIONS[3] + DESCRIPTIONS[4] + DESCRIPTIONS[5]
                    + DESCRIPTIONS[6] + DESCRIPTIONS[7] + DESCRIPTIONS[1] + DESCRIPTIONS[8];
        }
        else if (this.counter == 7) {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + DESCRIPTIONS[3] + DESCRIPTIONS[4] + DESCRIPTIONS[5]
                    + DESCRIPTIONS[6] + DESCRIPTIONS[7] + DESCRIPTIONS[8] + DESCRIPTIONS[1] + DESCRIPTIONS[9];
        }
        else if (this.counter == 8) {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + DESCRIPTIONS[3] + DESCRIPTIONS[4] + DESCRIPTIONS[5]
                    + DESCRIPTIONS[6] + DESCRIPTIONS[7] + DESCRIPTIONS[8] + DESCRIPTIONS[9] + DESCRIPTIONS[1] + DESCRIPTIONS[10];
        }
        else if (this.counter == 9) {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + DESCRIPTIONS[3] + DESCRIPTIONS[4] + DESCRIPTIONS[5]
                    + DESCRIPTIONS[6] + DESCRIPTIONS[7] + DESCRIPTIONS[8] + DESCRIPTIONS[9] + DESCRIPTIONS[10];
        }
    }
}
