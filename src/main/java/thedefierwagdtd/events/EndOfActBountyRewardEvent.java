package thedefierwagdtd.events;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlackStar;

import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.stats.CampfireChoice;
import thedefierwagdtd.relics.*;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class EndOfActBountyRewardEvent extends AbstractImageEvent {
    public static final String ID = "thedefierwagdtd:EndOfActBountyRewardEvent";
    public static boolean START_TRIGGERED = false;

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurScreen screen = CurScreen.INTRO;

    private enum PoolA1Option { TRANSFORM_AND_UPGRADE, REMOVE_2_CARDS, UPGRADE, UPGRADE_2_RANDOM, REMOVE_3_CURSES, OBTAIN_RARE_CARD }
    private enum PoolA2Option { MODIFY_REDUCE_COST_OF_CARD, MODIFY_APPLY_VULNERABLE_A, MODIFY_APPLY_VULNERABLE_B  }

    private enum PoolBOption { RANDOM_RARE_RELIC, MAX_HP, OBTAIN_BLACK_STAR }

    private PoolA1Option optionA1;
    private PoolA2Option optionA2;
    private PoolBOption optionB;

    private int screenNum = 0;

    private boolean optionPicked = false;
    private Choice choice = null;

    private enum Choice {
        TRANSFORM_AND_UPGRADE, REMOVE_2_CARDS, UPGRADE, UPGRADE_2_RANDOM, REMOVE_3_CURSES, OBTAIN_RARE_CARD,
        MODIFY_REDUCE_COST_OF_CARD, MODIFY_APPLY_VULNERABLE_A, MODIFY_APPLY_VULNERABLE_B,
        RANDOM_RARE_RELIC, MAX_HP, OBTAIN_BLACK_STAR;
    }

    private enum CurScreen {
        INTRO, RESULT;
    }

    public EndOfActBountyRewardEvent() {
        super(eventStrings.NAME, DESCRIPTIONS[0], "thedefierwagdtd/images/events/EndOfAct1BountyRewardEvent.png");

        // POOL A (deck modification)
        optionA1 = PoolA1Option.values()[AbstractDungeon.eventRng.random(PoolA1Option.values().length - 1)];
        optionA2 = PoolA2Option.values()[AbstractDungeon.eventRng.random(PoolA2Option.values().length - 1)];

        // POOL B (misc)
        optionB = PoolBOption.values()[AbstractDungeon.eventRng.random(PoolBOption.values().length - 1)];

        imageEventText.setDialogOption(OPTIONS[0]); // Gain Gold
        imageEventText.setDialogOption(getOptionA1Text(optionA1));
        imageEventText.setDialogOption(getOptionA2Text(optionA2));
        imageEventText.setDialogOption(getOptionBText(optionB));
    }

    private String getOptionA1Text(PoolA1Option opt) {
        switch (opt) {
            case TRANSFORM_AND_UPGRADE:
                return OPTIONS[1];

            case REMOVE_2_CARDS:
                return OPTIONS[2];

            case UPGRADE:
                return OPTIONS[3];

            case UPGRADE_2_RANDOM:
                return OPTIONS[4];

            case REMOVE_3_CURSES:
                return OPTIONS[5];

            case OBTAIN_RARE_CARD:
                return OPTIONS[6];

        }
        return "";
    }

    private String getOptionA2Text(PoolA2Option opt) {
        switch (opt) {
            case MODIFY_REDUCE_COST_OF_CARD:
                return OPTIONS[7];

            case MODIFY_APPLY_VULNERABLE_A:
                return OPTIONS[8];

            case MODIFY_APPLY_VULNERABLE_B:
                return OPTIONS[9];
        }
        return "";
    }

    private String getOptionBText(PoolBOption opt) {
        switch (opt) {
            case RANDOM_RARE_RELIC:
                return OPTIONS[10];

            case MAX_HP:
                return OPTIONS[11];

            case OBTAIN_BLACK_STAR:
                return OPTIONS[12];
        }
        return "";
    }

    @Override
    protected void buttonEffect(int buttonPressed) {

        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        System.out.println("GAIN_GOLD chosen");
                        player.gainGold(150);
                        screenNum = 1;
                        break;

                    case 1:
                        performPoolA1Effect(optionA1);
                        break;

                    case 2:
                        performPoolA2Effect(optionA2);
                        break;

                    case 3:
                        performPoolBEffect(optionB);
                        break;

                }
                this.optionPicked = true;
                this.imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[15]);
                this.screen = CurScreen.RESULT;
                return;
            case RESULT:
                switch (buttonPressed) {
                    case 0:
                        finishEvent();
                }
        }
    }

    private void performPoolA1Effect(PoolA1Option opt) {
        CardGroup purgeable = CardGroup.getGroupWithoutBottledCards(player.masterDeck.getPurgeableCards());

        switch (opt) {
            case TRANSFORM_AND_UPGRADE:
                System.out.println("TRANSFORM_AND_UPGRADE chosen");
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2f, Settings.HEIGHT / 2f,
                        new EmptyWhiskeyBottle());
                screenNum = 2;
                break;

            case REMOVE_2_CARDS:
                System.out.println("REMOVE_2_CARDS chosen");
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2f, Settings.HEIGHT / 2f,
                        new OrbRegalia());
                screenNum = 3;
                break;

            case UPGRADE:
                System.out.println("UPGRADE chosen");
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2f, Settings.HEIGHT / 2f,
                        new GingerbreadHouse());
                screenNum = 4;
                break;

            case UPGRADE_2_RANDOM:
                System.out.println("UPGRADE_2_RANDOM chosen");
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2f, Settings.HEIGHT / 2f,
                        new MagicScreen());
                screenNum = 5;
                break;

            case REMOVE_3_CURSES:
                System.out.println("REMOVE_3_CURSES chosen");
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2f, Settings.HEIGHT / 2f,
                        new SkunkTail());
                screenNum = 6;
                break;

            case OBTAIN_RARE_CARD:
                System.out.println("OBTAIN_RARE_CARD chosen");
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2f, Settings.HEIGHT / 2f,
                        new BlueBowtie());
                screenNum = 7;
                break;
        }
    }

    private void performPoolA2Effect(PoolA2Option opt) {
        CardGroup purgeable = CardGroup.getGroupWithoutBottledCards(player.masterDeck.getPurgeableCards());

        switch (opt) {
            case MODIFY_REDUCE_COST_OF_CARD:
                System.out.println("MODIFY_REDUCE_COST_OF_CARD chosen");
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2f, Settings.HEIGHT / 2f,
                        new BlueBook());
                screenNum = 8;
                break;

            case MODIFY_APPLY_VULNERABLE_A:
                System.out.println("MODIFY_APPLY_VULNERABLE_A chosen");
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2f, Settings.HEIGHT / 2f,
                        new OrangeBook());
                screenNum = 9;
                break;

            case MODIFY_APPLY_VULNERABLE_B:
                System.out.println("MODIFY_APPLY_VULNERABLE_B chosen");
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2f, Settings.HEIGHT / 2f,
                        new CyanBook());
                screenNum = 10;
                break;
        }
    }

    private void performPoolBEffect(PoolBOption opt) {
        switch (opt) {
            case RANDOM_RARE_RELIC:
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                        Settings.WIDTH / 2f, Settings.HEIGHT / 2f,
                        AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.RARE));
                screenNum = 11;
                break;

            case MAX_HP:
                player.increaseMaxHp(15, true);
                screenNum = 12;
                break;

            case OBTAIN_BLACK_STAR:
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2f, Settings.HEIGHT / 2f,
                        new BlackStar());
                screenNum = 13;
                break;
        }
    }

    @Override
    public void update() {
        super.update();

        if (screenNum == 99) {
            openMap();
            return;
        }
    }

    private void finishEvent() {
        AbstractPlayer p = AbstractDungeon.player;

        if (p.hasRelic("thedefierwagdtd:EndOfActBounty")) {
            p.loseRelic("thedefierwagdtd:EndOfActBounty");
        }

        MapRoomNode node = AbstractDungeon.currMapNode;
        node.room = new RestRoom();
        RestRoom rest = (RestRoom) node.room;

        rest.onPlayerEntry();
        rest.campfireUI.reopen();

        AbstractDungeon.rs = AbstractDungeon.RenderScene.CAMPFIRE;

        AbstractDungeon.topPanel.unhoverHitboxes();
        AbstractDungeon.overlayMenu.hideCombatPanels();
    }

    public EndOfActBounty bountyRelic;

}
