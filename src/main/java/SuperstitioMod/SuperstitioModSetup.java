package SuperstitioMod;

import SuperstitioMod.cards.Lupa.AbstractLupaCard;
import SuperstitioMod.cards.Lupa.CardStringsWithFlavor;
import SuperstitioMod.characters.Lupa;
import SuperstitioMod.relics.Sensitive;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SpireInitializer
public class SuperstitioModSetup implements EditStringsSubscriber, EditRelicsSubscriber, EditCardsSubscriber, EditKeywordsSubscriber,
        EditCharactersSubscriber, AddAudioSubscriber, PostInitializeSubscriber, PostExhaustSubscriber, StartGameSubscriber, PostUpdateSubscriber,
        RelicGetSubscriber, PostPowerApplySubscriber, PostBattleSubscriber, PostDungeonInitializeSubscriber,

        OnCardUseSubscriber, OnPowersModifiedSubscriber, PostDrawSubscriber, PostEnergyRechargeSubscriber {

    public static final String MOD_NAME = "Superstitio";
    public static final Logger logger = LogManager.getLogger(SuperstitioModSetup.class.getName());
    public static final Color MY_COLOR = new Color(79.0F / 255.0F, 185.0F / 255.0F, 9.0F / 255.0F, 1.0F);
    //选英雄界面的角色图标、选英雄时的背景图片
    private static final String MY_CHARACTER_BUTTON = getImgFilesPath() + "char/Character_Button.png";
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = getImgFilesPath() + "char/Character_Portrait.png";
    // 攻击牌的背景（小尺寸）
    private static final String BG_ATTACK_512 = getImgFilesPath() + "512/bg_attack_512.png";
    // 能力牌的背景（小尺寸）
    private static final String BG_POWER_512 = getImgFilesPath() + "512/bg_power_512.png";
    // 技能牌的背景（小尺寸）
    private static final String BG_SKILL_512 = getImgFilesPath() + "512/bg_skill_512.png";
    // 在卡牌和遗物描述中的能量图标
    private static final String SMALL_ORB = getImgFilesPath() + "char/small_orb.png";
    // 攻击牌的背景（大尺寸）
    private static final String BG_ATTACK_1024 = getImgFilesPath() + "1024/bg_attack.png";
    // 能力牌的背景（大尺寸）
    private static final String BG_POWER_1024 = getImgFilesPath() + "1024/bg_power.png";
    // 技能牌的背景（大尺寸）
    private static final String BG_SKILL_1024 = getImgFilesPath() + "1024/bg_skill.png";
    // 在卡牌预览界面的能量图标
    private static final String BIG_ORB = getImgFilesPath() + "char/card_orb.png";
    // 小尺寸的能量图标（战斗中，牌堆预览）
    private static final String ENERGY_ORB = getImgFilesPath() + "char/cost_orb.png";
    public static Map<String, CardStringsWithFlavor> cards = new HashMap<>();

    public SuperstitioModSetup() {
        BaseMod.subscribe(this);
        // 这里注册颜色
        BaseMod.addColor(Lupa.Enums.LUPA_CARD, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, BG_ATTACK_512, BG_SKILL_512,
                BG_POWER_512, ENERGY_ORB, BG_ATTACK_1024, BG_SKILL_1024, BG_POWER_1024, BIG_ORB, SMALL_ORB);
    }

    public static void initialize() {
        new SuperstitioModSetup();
    }

    private static String makeLocPath(Settings.GameLanguage language, String filename) {
        String ret = "localization/";
        switch (language) {
            case ZHS:
                ret = ret + "zhs/";
                break;
            case KOR:
                ret = ret + "kor/";
                break;
            default:
                ret = ret + "eng/";
        }

        return getModID() + "Resources/" + ret + filename + ".json";
    }

//    public static String getCardPath(String resourcePath) {
//        return getImgFilesPath()+"cards/" + resourcePath + ".png";
//    }

    private static String getModID() {
        return MOD_NAME + "Mod";
    }

    private static String getImgFilesPath() {
        return getModID() + "Resources/img";
    }

    private static String makeImgFilesPath(String... resourcePaths) {
        StringBuilder totalPath = new StringBuilder();
        for (String resourcePath : resourcePaths)
            totalPath.append("/").append(resourcePath);
        return getImgFilesPath() + totalPath + ".png";
    }

    public static String makeImgFilesPath_LupaCard(String... resourcePaths) {
        StringBuilder totalPath = new StringBuilder();
        for (String resourcePath : resourcePaths)
            totalPath.append("/").append(resourcePath);
        return makeImgFilesPath("cards_Lupa" + totalPath);
    }

//    public static String makeImgFilesPath_LupaCard(String resourcePath1, String resourcePath2) {
//        return makeImgFilesPath("cards_Lupa/" + resourcePath1 + "/" + resourcePath2);
//    }

    public static String makeImgFilesPath_Relic(String resourcePath) {
        return makeImgFilesPath("relics", resourcePath);
    }
    public static String makeImgFilesPath_UI(String resourcePath) {
        return makeImgFilesPath("UI", resourcePath);
    }

    public static String makeImgFilesPath_Character_Lupa(String resourcePath) {
        return makeImgFilesPath("character_lupa", resourcePath);
    }


    public static String makeImgFilesPath_RelicOutline(String resourcePath) {
        return makeImgFilesPath("relics/outline", resourcePath);
    }

    public static String makeImgFilesPath_Orb(String resourcePath) {
        return makeImgFilesPath("orbs", resourcePath);
    }

    public static String makeImgFilesPath_Power(String resourcePath) {
        return makeImgFilesPath("powers", resourcePath);
    }

    public static String makeImgFilesPath_Event(String resourcePath) {
        return makeImgFilesPath("events", resourcePath);
    }

    public static String MakeTextID(String idText) {
        return getModID() + ":" + idText;
    }

    private static void loadCardStringWithFlavorJsonStrings(String filepath) {
        logger.info("loadJsonStrings: " + CardStringsWithFlavor.class.getTypeName());
        String jsonString = Gdx.files.internal(filepath).readString(String.valueOf(StandardCharsets.UTF_8));
        String jsonString2 = null;
        {
            int startIndex = jsonString.indexOf('{');
            int endIndex = jsonString.lastIndexOf('}');

            if (startIndex != -1 && endIndex != -1 && startIndex < endIndex)
                jsonString2 = jsonString.substring(startIndex, endIndex + 1);
        }
        if (jsonString2 == null)
            return;
        logger.info("jsonString: " + jsonString2);
        Type typeToken = new TypeToken<Map<String, CardStringsWithFlavor>>() {
        }.getType();
        Gson gson = new Gson();
        logger.info(gson.fromJson(jsonString2, typeToken).toString());
        Map<String, CardStringsWithFlavor> map = gson.fromJson(jsonString2, typeToken);
        map.forEach((key, cardString) -> {
            logger.info("loadKey: " + key);
            cards.put(key, cardString);
        });

    }

    /**
     * 只输出后面的id，不携带模组信息
     *
     * @param complexId 带有模组信息的ID，如“xxxMod:xxx”
     * @return 只输出冒号后面的部分
     */
    public static String getIdOnly(String complexId) {
        Matcher matcher = Pattern.compile(":(.*)").matcher(complexId);
        if (matcher.find())
            return matcher.group(1).trim();
        return complexId;
    }

    @Override
    public void receiveEditCharacters() {
        //添加角色到MOD中
        BaseMod.addCharacter(new Lupa(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, Lupa.Enums.LUPA_Character);
    }

    @Override
    public void receiveEditCards() {
        //将卡牌添加
        new AutoAdd(MOD_NAME.toLowerCase())
                .packageFilter(AbstractLupaCard.class)
                .setDefaultSeen(true)
                .cards();
    }

    @Override
    public void receiveAddAudio() {
    }

    @Override
    public void receiveEditRelics() {
        // This finds and adds all relics inheriting from CustomRelic that are in the same package
        // as MyRelic, keeping all as unseen except those annotated with @AutoAdd.Seen
        new AutoAdd(MOD_NAME.toLowerCase())
                .packageFilter(Sensitive.class)
                .any(CustomRelic.class, (info, relic) -> {
                    BaseMod.addRelicToCustomPool(relic, Lupa.Enums.LUPA_CARD);
                    if (info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        SuperstitioModSetup.loadCardStringWithFlavorJsonStrings(makeLocPath(Settings.language, "card_Lupa"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class, makeLocPath(Settings.language, "character_Lupa"));
        BaseMod.loadCustomStringsFile(RelicStrings.class, makeLocPath(Settings.language, "relic_Lupa"));
        BaseMod.loadCustomStringsFile(PowerStrings.class, makeLocPath(Settings.language, "power"));
//        BaseMod.loadCustomStringsFile(EventStrings.class, makeLocPath(Settings.language, "DefaultMod-Event-Strings"));
//        BaseMod.loadCustomStringsFile(PotionStrings.class, makeLocPath(Settings.language, "DefaultMod-Potion-Strings"));
//        BaseMod.loadCustomStringsFile(OrbStrings.class, makeLocPath(Settings.language, "DefaultMod-Orb-Strings"));
//        BaseMod.loadCustomStringsFile(UIStrings.class, makeLocPath(Settings.language, "UIStrings"));
        logger.info("Done editing strings");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(makeLocPath(Settings.language, "keyword")).readString(String.valueOf(StandardCharsets
                .UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }

        Gson gsonForDefault = new Gson();
        Keyword[] keywordsForDefault =
                gsonForDefault.fromJson(Gdx.files.internal(makeLocPath(Settings.language, "keywordForDefault")).readString(String.valueOf(StandardCharsets
                        .UTF_8)), Keyword[].class);
        if (keywordsForDefault != null) {
            for (Keyword keyword : keywordsForDefault) {
                GameDictionary.keywords.remove(keyword.PROPER_NAME);
                GameDictionary.keywords.put(keyword.PROPER_NAME, keyword.DESCRIPTION);
            }
        }

    }

    @Override
    public void receivePostExhaust(AbstractCard abstractCard) {
    }

    @Override
    public void receivePostInitialize() {
    }

    @Override
    public void receivePostUpdate() {
    }

    @Override
    public void receiveStartGame() {
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {

    }

    @Override
    public void receivePowersModified() {

    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {

    }

    @Override
    public void receivePostDraw(AbstractCard abstractCard) {

    }

    @Override
    public void receivePostDungeonInitialize() {

    }

    @Override
    public void receivePostEnergyRecharge() {

    }

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature
            abstractCreature1) {

    }

    @Override
    public void receiveRelicGet(AbstractRelic abstractRelic) {

    }


//    private void initializeConfig() {
//        final UIStrings configStrings = CardCrawlGame.languagePack.getUIString("downfall:ConfigMenuText");
//        final Texture badgeTexture = new Texture(assetPath("images/badge.png"));
//        this.settingsPanel = new ModPanel();
//        int configPos = 750;
//        final int configStep = 40;
//        final ModLabeledToggleButton characterCrossoverBtn = new ModLabeledToggleButton(configStrings.TEXT[4], 350.0f, (float)configPos, Settings
//        .CREAM_COLOR, FontHelper.charDescFont, downfallMod.crossoverCharacters, this.settingsPanel, label -> {}, button -> {
//            downfallMod.crossoverCharacters = button.enabled;
//            CardCrawlGame.mainMenuScreen.charSelectScreen.options.clear();
//            CardCrawlGame.mainMenuScreen.charSelectScreen.initialize();
//            saveData();
//            return;
//        });
//        this.settingsPanel.addUIElement((IUIElement)characterCrossoverBtn);
//        configPos -= configStep;
//        final ModLabeledToggleButton characterModCrossoverBtn = new ModLabeledToggleButton(configStrings.TEXT[5], 350.0f, (float)configPos,
//        Settings.CREAM_COLOR, FontHelper.charDescFont, downfallMod.crossoverModCharacters, this.settingsPanel, label -> {}, button -> {
//            downfallMod.crossoverModCharacters = button.enabled;
//            CardCrawlGame.mainMenuScreen.charSelectScreen.options.clear();
//            CardCrawlGame.mainMenuScreen.charSelectScreen.initialize();
//            saveData();
//            return;
//        });
//        configPos -= configStep;
//        final ModLabeledToggleButton contentSharingBtnRelics = new ModLabeledToggleButton(configStrings.TEXT[0], 350.0f, (float)configPos,
//        Settings.CREAM_COLOR, FontHelper.charDescFont, downfallMod.contentSharing_relics, this.settingsPanel, label -> {}, button -> {
//            downfallMod.contentSharing_relics = button.enabled;
//            saveData();
//            return;
//        });
//        configPos -= configStep;
//        final ModLabeledToggleButton contentSharingBtnEvents = new ModLabeledToggleButton(configStrings.TEXT[2], 350.0f, (float)configPos,
//        Settings.CREAM_COLOR, FontHelper.charDescFont, downfallMod.contentSharing_events, this.settingsPanel, label -> {}, button -> {
//            downfallMod.contentSharing_events = button.enabled;
//            saveData();
//            return;
//        });
//        configPos -= configStep;
//        final ModLabeledToggleButton contentSharingBtnPotions = new ModLabeledToggleButton(configStrings.TEXT[1], 350.0f, (float)configPos,
//        Settings.CREAM_COLOR, FontHelper.charDescFont, downfallMod.contentSharing_potions, this.settingsPanel, label -> {}, button -> {
//            downfallMod.contentSharing_potions = button.enabled;
//            saveData();
//            return;
//        });
//        configPos -= configStep;
//        final ModLabeledToggleButton contentSharingBtnColorless = new ModLabeledToggleButton(configStrings.TEXT[3], 350.0f, (float)configPos,
//        Settings.CREAM_COLOR, FontHelper.charDescFont, downfallMod.contentSharing_colorlessCards, this.settingsPanel, label -> {}, button -> {
//            downfallMod.contentSharing_colorlessCards = button.enabled;
//            saveData();
//            return;
//        });
//        configPos -= configStep;
//        final ModLabeledToggleButton contentSharingBtnCurses = new ModLabeledToggleButton(configStrings.TEXT[6], 350.0f, (float)configPos,
//        Settings.CREAM_COLOR, FontHelper.charDescFont, downfallMod.contentSharing_curses, this.settingsPanel, label -> {}, button -> {
//            downfallMod.contentSharing_curses = button.enabled;
//            saveData();
//            return;
//        });
//        configPos -= configStep;
//        final ModLabeledToggleButton normalMapBtn = new ModLabeledToggleButton(configStrings.TEXT[7], 350.0f, (float)configPos, Settings
//        .CREAM_COLOR, FontHelper.charDescFont, downfallMod.normalMapLayout, this.settingsPanel, label -> {}, button -> {
//            downfallMod.normalMapLayout = button.enabled;
//            saveData();
//            return;
//        });
//        configPos -= configStep;
//        final ModLabeledToggleButton sneckoNoModConfig = new ModLabeledToggleButton(configStrings.TEXT[10], 350.0f, (float)configPos, Settings
//        .CREAM_COLOR, FontHelper.charDescFont, downfallMod.sneckoNoModCharacters, this.settingsPanel, label -> {}, button -> {
//            downfallMod.sneckoNoModCharacters = button.enabled;
//            saveData();
//            return;
//        });
//        configPos -= configStep;
//        final ModLabeledToggleButton unlockAllBtn = new ModLabeledToggleButton(configStrings.TEXT[8], 350.0f, (float)configPos, Settings
//        .CREAM_COLOR, FontHelper.charDescFont, downfallMod.unlockEverything, this.settingsPanel, label -> {}, button -> {
//            downfallMod.unlockEverything = button.enabled;
//            saveData();
//            return;
//        });
//        configPos -= configStep;
//        final ModLabeledToggleButton noMusicBtn = new ModLabeledToggleButton(configStrings.TEXT[11], 350.0f, (float)configPos, Settings
//        .CREAM_COLOR, FontHelper.charDescFont, downfallMod.noMusic, this.settingsPanel, label -> {}, button -> {
//            downfallMod.noMusic = button.enabled;
//            saveData();
//            return;
//        });
//        configPos -= configStep;
//        final ModLabeledToggleButton unlockAllSkinBtn = new ModLabeledToggleButton(configStrings.TEXT[12], 350.0f, (float)configPos, Settings
//        .CREAM_COLOR, FontHelper.charDescFont, reskinContent.unlockAllReskin, this.settingsPanel, label -> {}, button -> {
//            reskinContent.unlockAllReskin = button.enabled;
//            reskinContent.unlockAllReskin();
//            return;
//        });
//        this.settingsPanel.addUIElement((IUIElement)contentSharingBtnCurses);
//        this.settingsPanel.addUIElement((IUIElement)contentSharingBtnEvents);
//        this.settingsPanel.addUIElement((IUIElement)contentSharingBtnPotions);
//        this.settingsPanel.addUIElement((IUIElement)contentSharingBtnRelics);
//        this.settingsPanel.addUIElement((IUIElement)contentSharingBtnColorless);
//        this.settingsPanel.addUIElement((IUIElement)normalMapBtn);
//        this.settingsPanel.addUIElement((IUIElement)sneckoNoModConfig);
//        this.settingsPanel.addUIElement((IUIElement)unlockAllBtn);
//        this.settingsPanel.addUIElement((IUIElement)noMusicBtn);
//        this.settingsPanel.addUIElement((IUIElement)unlockAllSkinBtn);
//        this.settingsPanel.addUIElement((IUIElement)characterModCrossoverBtn);
//        BaseMod.registerModBadge(badgeTexture, "downfall", "Downfall Team", "A very evil Expansion.", this.settingsPanel);
//    }

}