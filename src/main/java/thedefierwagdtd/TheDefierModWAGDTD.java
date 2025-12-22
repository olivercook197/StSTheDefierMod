package thedefierwagdtd;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.interfaces.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import thedefierwagdtd.CustomTags.CustomTag;
import thedefierwagdtd.actions.RecklessAction;
import thedefierwagdtd.cards.BaseCard;
import thedefierwagdtd.cards.uncommon.BrushWithDeath;
import thedefierwagdtd.cards.uncommon.StartSmall;
import thedefierwagdtd.character.TheDefier;
import thedefierwagdtd.events.EndOfActBountyRewardEvent;
import thedefierwagdtd.powers.*;
import thedefierwagdtd.relics.BaseRelic;
import thedefierwagdtd.relics.EndOfActBounty;
import thedefierwagdtd.rooms.CustomEventRoom;
import thedefierwagdtd.util.GeneralUtils;
import thedefierwagdtd.util.KeywordInfo;
import thedefierwagdtd.util.Sounds;
import thedefierwagdtd.util.TextureLoader;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static basemod.BaseMod.addEvent;


@SpireInitializer
public class TheDefierModWAGDTD implements
        EditRelicsSubscriber,
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        AddAudioSubscriber,
        PostInitializeSubscriber,
        OnCardUseSubscriber,
        PostBattleSubscriber {
    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this
    static { loadModInfo(); }
    private static final String resourcesFolder = checkResourcesPath();
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.

    //This is used to prefix the IDs of various objects like cards and relics,
    //to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new TheDefierModWAGDTD();

        TheDefier.Meta.registerColor();
    }

    public TheDefierModWAGDTD() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
    }

    @Override
    public void receivePostInitialize() {
        //This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));
        //Set up the mod information displayed in the in-game mods menu.
        //The information used is taken from your pom.xml file.

        //If you want to set up a config panel, that will be done here.
        //You can find information about this on the BaseMod wiki page "Mod Config and Panel".
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, null);

        BaseMod.addEvent(
                new AddEventParams.Builder(
                        EndOfActBountyRewardEvent.ID,
                        EndOfActBountyRewardEvent.class
                )
                        .eventType(EventUtils.EventType.ONE_TIME)
                        .spawnCondition(() ->
                                AbstractDungeon.player != null &&
                                        AbstractDungeon.player.hasRelic(EndOfActBounty.RELIC_ID)
                        )
                        .create()
        );

    }

    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString()
    {
        return Settings.language.name().toLowerCase();
    }
    private static final String defaultLanguage = "eng";

    public static final Map<String, KeywordInfo> keywords = new HashMap<>();

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            }
            catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
    }

    @Override
    public void receiveEditKeywords()
    {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            keyword.prep();
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try
            {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            }
            catch (Exception e)
            {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
        if (!info.ID.isEmpty())
        {
            keywords.put(info.ID, info);
        }
    }

    @Override
    public void receiveAddAudio() {
        loadAudio(Sounds.class);
    }

    private static final String[] AUDIO_EXTENSIONS = { ".ogg", ".wav", ".mp3" }; //There are more valid types, but not really worth checking them all here
    private void loadAudio(Class<?> cls) {
        try {
            Field[] fields = cls.getDeclaredFields();
            outer:
            for (Field f : fields) {
                int modifiers = f.getModifiers();
                if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers) && f.getType().equals(String.class)) {
                    String s = (String) f.get(null);
                    if (s == null) { //If no defined value, determine path using field name
                        s = audioPath(f.getName());

                        for (String ext : AUDIO_EXTENSIONS) {
                            String testPath = s + ext;
                            if (Gdx.files.internal(testPath).exists()) {
                                s = testPath;
                                BaseMod.addAudio(s, s);
                                f.set(null, s);
                                continue outer;
                            }
                        }
                        throw new Exception("Failed to find an audio file \"" + f.getName() + "\" in " + resourcesFolder + "/audio; check to ensure the capitalization and filename are correct.");
                    }
                    else { //Otherwise, load defined path
                        if (Gdx.files.internal(s).exists()) {
                            BaseMod.addAudio(s, s);
                        }
                        else {
                            throw new Exception("Failed to find audio file \"" + s + "\"; check to ensure this is the correct filepath.");
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error("Exception occurred in loadAudio: ", e);
        }
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String audioPath(String file) {
        return resourcesFolder + "/audio/" + file;
    }
    public static String imagePath(String file) {
        return resourcesFolder + "/images/" + file;
    }
    public static String characterPath(String file) {
        return resourcesFolder + "/images/character/" + file;
    }
    public static String powerPath(String file) {
        return resourcesFolder + "/images/powers/" + file;
    }
    public static String relicPath(String file) {
        return resourcesFolder + "/images/relics/" + file;
    }

    /**
     * Checks the expected resources path based on the package name.
     */
    private static String checkResourcesPath() {
        String name = TheDefierModWAGDTD.class.getName(); //getPackage can be iffy with patching, so class name is used instead.
        int separator = name.indexOf('.');
        if (separator > 0)
            name = name.substring(0, separator);

        FileHandle resources = new LwjglFileHandle(name, Files.FileType.Internal);

        if (!resources.exists()) {
            throw new RuntimeException("\n\tFailed to find resources folder; expected it to be at  \"resources/" + name + "\"." +
                    " Either make sure the folder under resources has the same name as your mod's package, or change the line\n" +
                    "\t\"private static final String resourcesFolder = checkResourcesPath();\"\n" +
                    "\tat the top of the " + TheDefierModWAGDTD.class.getSimpleName() + " java file.");
        }
        if (!resources.child("images").exists()) {
            throw new RuntimeException("\n\tFailed to find the 'images' folder in the mod's 'resources/" + name + "' folder; Make sure the " +
                    "images folder is in the correct location.");
        }
        if (!resources.child("localization").exists()) {
            throw new RuntimeException("\n\tFailed to find the 'localization' folder in the mod's 'resources/" + name + "' folder; Make sure the " +
                    "localization folder is in the correct location.");
        }

        return name;
    }

    /**
     * This determines the mod's ID based on information stored by ModTheSpire.
     */
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(TheDefierModWAGDTD.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    @Override
    public void receiveEditCharacters() {
        TheDefier.Meta.registerCharacter();
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseCard.class) //In the same package as this class
                .setDefaultSeen(true) //And marks them as seen in the compendium
                .cards(); //Adds the cards
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseRelic.class) //In the same package as this class
                .any(BaseRelic.class, (info, relic) -> { //Run this code for any classes that extend this class
                    if (relic.pool != null)
                        BaseMod.addRelicToCustomPool(relic, relic.pool); //Register a custom character specific relic
                    else
                        BaseMod.addRelic(relic, relic.relicType); //Register a shared or base game character specific relic

                    //If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
                    //If you want all your relics to be visible by default, just remove this if statement.
                    if (info.seen)
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                });
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if (!abstractCard.hasTag(CustomTag.RECKLESS)) {
            return;
        }

        AbstractPlayer p = AbstractDungeon.player;

        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                // Brush With Death
                if (abstractCard.cardID.equals(BrushWithDeath.ID)) {
                    boolean hasCurse = p.hand.group.stream()
                            .anyMatch(c -> c.type == AbstractCard.CardType.CURSE);

                    if (!hasCurse) {
                        addToBot(new RecklessAction(p));
                    }

                    isDone = true;
                    return;
                }

//                // Ferocious Swipe
//                if (abstractCard.cardID.equals(FerociousSwipe.ID)) {
//                    AbstractPower lionsHeart = p.getPower(LionsHeartBuff.POWER_ID);
//                    int lhAmount = (lionsHeart == null ? 0 : lionsHeart.amount);
//
//                    if (lhAmount < 30) {
//                        addToBot(new RecklessAction(p));
//                    }
//
//                    isDone = true;
//                    return;
//                }

                addToBot(new RecklessAction(p));
                isDone = true;
            }
        });
    }

    @Override
    public void receivePostBattle(AbstractRoom room) {
        // guard: player can be null (for safety)
        if (AbstractDungeon.player == null) {
            return;
        }

        // iterate the master deck so we modify the permanent card data
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c instanceof StartSmall) {
                ((StartSmall) c).checkGrowth();
            }
        }
    }

    @SpirePatch(
            clz = ApplyPowerAction.class,
            method = "update"
    )
    public static class OpportunityPowerApplyPatch {
        // cache reflection lookup to avoid repeated exceptions
        private static Field powerField;
        private static boolean triedLookup = false;

        private static AbstractPower getAppliedPower(ApplyPowerAction action) {
            if (!triedLookup) {
                triedLookup = true;
                try {
                    powerField = ApplyPowerAction.class.getDeclaredField("powerToApply");
                    powerField.setAccessible(true);
                } catch (NoSuchFieldException e1) {
                    try {
                        powerField = ApplyPowerAction.class.getDeclaredField("power");
                        powerField.setAccessible(true);
                    } catch (NoSuchFieldException e2) {
                        powerField = null; // can't find field; we'll bail gracefully
                    }
                }
            }
            if (powerField == null) return null;

            try {
                Object val = powerField.get(action);
                if (val instanceof AbstractPower) return (AbstractPower) val;
            } catch (IllegalAccessException e) {
                // shouldn't happen after setAccessible(true), but ignore safely
                return null;
            }
            return null;
        }

        @SpirePostfixPatch
        public static void afterApply(ApplyPowerAction __instance) {
            if (!__instance.isDone) {
                return;
            }

            AbstractPower applied = getAppliedPower(__instance);
            if (applied == null) return;

            if (!applied.ID.equals(com.megacrit.cardcrawl.powers.VulnerablePower.POWER_ID)) return;

            if (AbstractDungeon.player == null) return;

            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof OpportunityPower) {
                    ((OpportunityPower) p).trigger(applied, applied.owner);
                }
                if (p instanceof RainyDayPower) {
                    ((RainyDayPower) p).trigger(applied);
                }
            }
        }
    }

    public static MapRoomNode TeleportEvent(String eventID) {
        System.out.println("Clearing prior room: " + AbstractDungeon.lastCombatMetricKey);
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        AbstractDungeon.player.resetControllerValues();
        AbstractDungeon.effectList.clear();
        AbstractDungeon.topLevelEffects.clear();
        AbstractDungeon.topLevelEffectsQueue.clear();
        AbstractDungeon.effectsQueue.clear();
        AbstractDungeon.dungeonMapScreen.dismissable = true;
        AbstractDungeon.dungeonMapScreen.map.legend.isLegendHighlighted = false;
        AbstractDungeon.player.orbs.clear();
        AbstractDungeon.player.animX = 0.0F;
        AbstractDungeon.player.animY = 0.0F;
        AbstractDungeon.player.hideHealthBar();
        AbstractDungeon.player.hand.clear();
        AbstractDungeon.player.powers.clear();
        AbstractDungeon.player.drawPile.clear();
        AbstractDungeon.player.discardPile.clear();
        AbstractDungeon.player.exhaustPile.clear();
        AbstractDungeon.player.limbo.clear();
        AbstractDungeon.player.loseBlock(true);
        AbstractDungeon.player.damagedThisCombat = 0;
        GameActionManager.turn = 1;
        AbstractDungeon.actionManager.monsterQueue.clear();
        AbstractDungeon.actionManager.monsterAttacksQueued = true;
        AbstractDungeon.actionManager.clear();
        MapRoomNode cur = AbstractDungeon.currMapNode;
        MapRoomNode node = new MapRoomNode(cur.x, cur.y);
        node.room = (AbstractRoom)new CustomEventRoom(EventHelper.getEvent(eventID));
        ArrayList<MapEdge> curEdges = cur.getEdges();
        for (MapEdge edge : curEdges)
            node.addEdge(edge);
        AbstractDungeon.player.releaseCard();
        AbstractDungeon.overlayMenu.hideCombatPanels();
        AbstractDungeon.previousScreen = null;
        AbstractDungeon.dynamicBanner.hide();
        AbstractDungeon.dungeonMapScreen.closeInstantly();
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.topPanel.unhoverHitboxes();
        AbstractDungeon.fadeIn();
        AbstractDungeon.effectList.clear();
        AbstractDungeon.topLevelEffects.clear();
        AbstractDungeon.topLevelEffectsQueue.clear();
        AbstractDungeon.effectsQueue.clear();
        AbstractDungeon.dungeonMapScreen.dismissable = true;
        AbstractDungeon.nextRoom = node;
        node.taken = true;
        AbstractDungeon.setCurrMapNode(node);
        AbstractDungeon.getCurrRoom().onPlayerEntry();
        AbstractDungeon.scene.nextRoom(node.room);
        AbstractDungeon.rs = (node.room.event instanceof com.megacrit.cardcrawl.events.AbstractImageEvent) ? AbstractDungeon.RenderScene.EVENT : AbstractDungeon.RenderScene.NORMAL;
        return node;
    }

    public void receiveEditEvents() {
        BaseMod.addEvent(
                EndOfActBountyRewardEvent.ID,
                EndOfActBountyRewardEvent.class
        );
    }



}