package thedefierwagdtd.events;

import basemod.BaseMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thedefierwagdtd.relics.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BookEvent extends AbstractImageEvent {
    public static final String ID = "thedefierwagdtd:BookEvent";

    private static final EventStrings eventStrings =
            CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final int GOLD_COST = 25;
    private static final int A15_GOLD_COST = 50;

    private static final int NUM_BOOK_CHOICES = 3;
    private ArrayList<Integer> chosenBooks = new ArrayList<>();


    private enum Screen {
        INTRO,
        COMPLETE
    }

    private Screen screen = Screen.INTRO;

    public BookEvent() {
        super(NAME, DESCRIPTIONS[0], "thedefierwagdtd/images/events/BookEvent.png");

        int goldCost = AbstractDungeon.ascensionLevel >= 15 ? A15_GOLD_COST : GOLD_COST;

        ArrayList<Integer> pool = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            pool.add(i);
        }

        Collections.shuffle(pool, new Random(AbstractDungeon.miscRng.randomLong()));
        chosenBooks.addAll(pool.subList(0, NUM_BOOK_CHOICES));

        for (int index : chosenBooks) {
            imageEventText.setDialogOption(
                    OPTIONS[index],
                    AbstractDungeon.player.gold < goldCost
            );
        }

        imageEventText.setDialogOption(OPTIONS[10]);
    }


    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (screen) {
            case INTRO:
                handleIntroChoice(buttonPressed);
                break;
            case COMPLETE:
                openMap();
                break;
        }
    }

    private void handleIntroChoice(int buttonPressed) {

        if (buttonPressed == chosenBooks.size()) {
            imageEventText.updateBodyText(DESCRIPTIONS[1]);
            screen = Screen.COMPLETE;
            imageEventText.clearAllDialogs();
            imageEventText.setDialogOption(OPTIONS[10]);
            return;
        }

        int bookIndex = chosenBooks.get(buttonPressed);

        switch (bookIndex) {
            case 0:
                obtainRelic(new BlueBook());
                break;
            case 1:
                obtainRelic(new CyanBook());
                break;
            case 2:
                obtainRelic(new OrangeBook());
                break;
            case 3:
                obtainRelic(new GreenBook());
                break;
            case 4:
                obtainRelic(new YellowBook());
                break;
        }

        screen = Screen.COMPLETE;
        imageEventText.clearAllDialogs();
        imageEventText.updateBodyText(DESCRIPTIONS[2]);
        imageEventText.setDialogOption(OPTIONS[10]);
    }


    private void obtainRelic(AbstractRelic relic) {
        int goldCost = AbstractDungeon.ascensionLevel >= 15 ? A15_GOLD_COST : GOLD_COST;

        AbstractDungeon.player.loseGold(goldCost);
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                Settings.WIDTH / 2f,
                Settings.HEIGHT / 2f,
                relic
        );
    }

}

