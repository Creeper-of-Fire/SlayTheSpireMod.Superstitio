package superstitio;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import superstitio.cards.CardOwnerPlayerManager;
import superstitio.cards.general.PowerCard.drawAndEnergy.ChokeChoker;
import superstitio.cards.general.SkillCard.gainEnergy.TimeStop;
import superstitio.cards.lupa.LupaCard;
import superstitio.cards.lupa.SkillCard.block.Philter;
import superstitio.cards.maso.MasoCard;
import superstitio.customStrings.*;
import superstitio.delayHpLose.DelayHpLosePower_ApplyOnAttacked;
import superstitio.delayHpLose.DelayHpLosePower_ApplyOnlyOnVictory;
import superstitio.delayHpLose.DelayHpLosePower_HealOnVictory;
import superstitio.delayHpLose.DelayRemoveDelayHpLosePower;
import superstitio.powers.SexualHeat;
import superstitio.powers.lupaOnly.BeerCupSemen;
import superstitio.powers.lupaOnly.FloorSemen;
import superstitio.powers.lupaOnly.InsideSemen;
import superstitio.powers.lupaOnly.OutsideSemen;
import superstitioapi.DataUtility;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static superstitioapi.DataUtility.makeDefaultPath;

public class DataManager {
    public static Map<String, CardStringsWithFlavorSet> cards = new HashMap<>();
    public static Map<String, PowerStringsSet> powers = new HashMap<>();
    public static Map<String, ModifierStringsSet> modifiers = new HashMap<>();
    public static Map<String, OrbStringsSet> orbs = new HashMap<>();
    public SPTT_DATA spttData = new SPTT_DATA();

    static String makeLocalizationPath(Settings.GameLanguage language, String filename) {
        String ret = "localization/";
        switch (language) {
            case ZHS:
                ret = ret + "zhs/";
                break;
            default:
                ret = ret + "zhs/";
                break;
        }

        return getResourcesFilesPath() + ret + filename + ".json";
    }

    public static String getModID() {
        return SuperstitioModSetup.MOD_NAME + "Mod";
    }

    private static String getResourcesFilesPath() {
        return getModID() + "Resources/";
    }

    private static String getImgFolderPath(String path) {
        String allLevelPath = getResourcesFilesPath() + "img" + path;
        String sfwLevelPath = getResourcesFilesPath() + "imgSFW" + path;

        if (!SuperstitioModSetup.getEnableSFW()) {
            return allLevelPath;
        }
        else
            return sfwLevelPath;
    }

    public static String makeImgFilesPath(String fileName, String... folderPaths) {
        return getImgFolderPath(makeFolderTotalString(folderPaths)) + "/" + fileName + ".png";
    }

    public static String makeFolderTotalString(String... strings) {
        if (strings.length == 0) return "";
        StringBuilder totalString = new StringBuilder();
        for (String string : strings)
            totalString.append("/").append(string);
        return totalString.toString();
    }

    public static String makeImgFilesPath_Card(String fileName, String... subFolder) {
        return makeImgFilesPath(fileName, "cards", makeFolderTotalString(subFolder));
    }

    public static String makeImgFilesPath_Relic(String fileName, String... subFolder) {
        return makeImgFilesPath(fileName, "relics", makeFolderTotalString(subFolder));
    }

    public static String makeImgFilesPath_UI(String fileName, String... subFolder) {
        return makeImgFilesPath(fileName, "UI", makeFolderTotalString(subFolder));
    }

    public static String makeImgFilesPath_Character_Lupa(String fileName, String... subFolder) {
        return makeImgFilesPath(fileName, "character", makeFolderTotalString(subFolder));
    }

    public static String makeImgFilesPath_RelicOutline(String fileName, String... subFolder) {
        return makeImgFilesPath(fileName, "relics/outline", makeFolderTotalString(subFolder));
    }

    public static String makeImgFilesPath_RelicLarge(String fileName, String... subFolder) {
        return makeImgFilesPath(fileName, "relics/large", makeFolderTotalString(subFolder));
    }

    public static String makeImgFilesPath_Orb(String fileName, String... subFolder) {
        return makeImgFilesPath(fileName, "orbs", makeFolderTotalString(subFolder));
    }

    public static String makeImgFilesPath_Power(String fileName, String... subFolder) {
        return makeImgFilesPath(fileName, "powers", makeFolderTotalString(subFolder));
    }

    public static String makeImgFilesPath_Event(String fileName, String... subFolder) {
        return makeImgFilesPath(fileName, "events", makeFolderTotalString(subFolder));
    }

    public static String MakeTextID(String idText) {
        return getModID() + ":" + idText;
    }

    public static String MakeTextID(Class<?> idClass) {
        if (CardOwnerPlayerManager.isGeneralCard(idClass))
            return getModID() + ":" + idClass.getSimpleName();
        if (CardOwnerPlayerManager.isLupaCard(idClass))
            return getModID() + ":" + LupaCard.class.getSimpleName() + ":" + idClass.getSimpleName();
        if (CardOwnerPlayerManager.isMasoCard(idClass))
            return getModID() + ":" + MasoCard.class.getSimpleName() + ":" + idClass.getSimpleName();
        return getModID() + ":" + idClass.getSimpleName();
    }

    public static String MakeTextID(String idText, Class<?> idClass) {
        if (CardOwnerPlayerManager.isGeneralCard(idClass))
            return getModID() + ":" + idText;
        if (CardOwnerPlayerManager.isLupaCard(idClass))
            return getModID() + ":" + LupaCard.class.getSimpleName() + ":" + idText;
        if (CardOwnerPlayerManager.isMasoCard(idClass))
            return getModID() + ":" + MasoCard.class.getSimpleName() + ":" + idText;
        return getModID() + ":" + idText;
    }

    public static String makeImgPath(
            String defaultFileName, BiFunction<String, String[], String> PathFinder, String fileName, String... subFolder) {

        if (!SuperstitioModSetup.getEnableGuroCharacter() && fileName.contains(MasoCard.class.getSimpleName()))
            return makeDefaultPath(defaultFileName, PathFinder);

        return DataUtility.makeImgPath((string) -> {
            try {
                makeNeedDrawPicture(defaultFileName, PathFinder, DataUtility.getIdOnly(fileName), makeDefaultPath(defaultFileName, PathFinder), subFolder);
            } catch (IOException e) {
                Logger.error(e);
            }
        }, defaultFileName, PathFinder, fileName, subFolder);
    }


    //生成所有的需要绘制的图片，方便检查
    private static void makeNeedDrawPicture(String defaultFileName, BiFunction<String, String[], String> PathFinder, String fileName, String defaultPath, String... subFolder) throws IOException {
        List<String> needDrawFileName = new ArrayList<>();
        needDrawFileName.add("needDraw");
        needDrawFileName.addAll(Arrays.asList(subFolder));
        if (fileName.contains("32")) return;
        if (fileName.contains("84") && noNeedDrawPower84(fileName)) return;

        if (defaultPath.contains("outline")) return;
        if (defaultPath.contains("large")) return;


        final FileHandle defaultFileHandle;

        if (PathFinder.apply("", subFolder).contains("card")) {
            defaultFileHandle = Gdx.files.internal(PathFinder.apply(defaultFileName + "_p", new String[]{""}));
        }
        else if (PathFinder.apply("", subFolder).contains("orb")) {
            return;
        }
        else {
            defaultFileHandle = Gdx.files.internal(defaultPath);
        }

        String needDrawFilePath;
        if (PathFinder.apply("", subFolder).contains("card")) {
            needDrawFilePath = PathFinder.apply(fileName + "_p", needDrawFileName.toArray(new String[0]));
        }
        else
            needDrawFilePath = PathFinder.apply(fileName, needDrawFileName.toArray(new String[0]));
        File defaultFileCopyTo = new File(needDrawFilePath);
        Pattern pattern = Pattern.compile("^(.+/)[^/]+$");
        Matcher matcher = pattern.matcher(needDrawFilePath);
        String totalFolderPath;
        if (matcher.find()) {
            totalFolderPath = matcher.group(1);
        }
        else {
            totalFolderPath = needDrawFilePath;
        }
        new File(totalFolderPath).mkdirs();
        if (!defaultFileCopyTo.exists())
            Files.copy(defaultFileHandle.read(), defaultFileCopyTo.toPath());
    }

    private static boolean noNeedDrawPower84(String fileName) {
        String checkName = fileName.replace("84", "");
        if (checkName.equals(DataUtility.getIdOnly(Philter.SexPlateArmorPower.POWER_ID)))
            return true;
        if (checkName.equals(DataUtility.getIdOnly(TimeStop.TimeStopPower.POWER_ID)))
            return true;
        if (checkName.equals(DataUtility.getIdOnly(ChokeChoker.ChokeChokerPower.POWER_ID)))
            return true;
        if (checkName.equals(DataUtility.getIdOnly(SexualHeat.POWER_ID)))
            return true;
        if (checkName.equals(DataUtility.getIdOnly(DelayHpLosePower_ApplyOnlyOnVictory.POWER_ID)))
            return true;
        if (checkName.equals(DataUtility.getIdOnly(DelayHpLosePower_ApplyOnAttacked.POWER_ID)))
            return true;
        if (checkName.equals(DataUtility.getIdOnly(DelayHpLosePower_HealOnVictory.POWER_ID)))
            return true;
        if (checkName.equals(DataUtility.getIdOnly(DelayRemoveDelayHpLosePower.POWER_ID)))
            return true;
        if (checkName.equals(DataUtility.getIdOnly(FloorSemen.POWER_ID)))
            return true;
        if (checkName.equals(DataUtility.getIdOnly(InsideSemen.POWER_ID)))
            return true;
        if (checkName.equals(DataUtility.getIdOnly(OutsideSemen.POWER_ID)))
            return true;
        if (checkName.equals(DataUtility.getIdOnly(BeerCupSemen.POWER_ID)))
            return true;
        return false;
    }

    public static <T> T makeJsonStringFromFile(String fileName, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = Gdx.files.internal(DataManager.makeLocalizationPath(Settings.language, fileName))
                .readString(String.valueOf(StandardCharsets.UTF_8));
        return gson.fromJson(json, objectClass);
    }

    public static void replaceStringsInObj(Object obj, WordReplace wordReplace) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(obj) instanceof String) {
                    String value = (String) field.get(obj);
                    if (value == null || !value.contains(wordReplace.WordOrigin))
                        continue;
                    value = value.replace(wordReplace.WordOrigin, wordReplace.WordReplace);
                    field.set(obj, value);

                }
                else if (field.get(obj) instanceof String[]) {
                    String[] values = (String[]) field.get(obj);
                    if (values == null || values.length == 0)
                        continue;
                    String[] list = new String[values.length];
                    for (int i = 0, valuesLength = values.length; i < valuesLength; i++) {
                        String string = values[i];
                        if (string != null && string.contains(wordReplace.WordOrigin)) {
                            string = string.replace(wordReplace.WordOrigin, wordReplace.WordReplace);
                        }
                        String apply = string;
                        list[i] = apply;
                    }

                    field.set(obj, list);
                }
            } catch (IllegalAccessException e) {
                Logger.error(e);
            }
        }
    }

//    public static <T> Optional<String> getTypeMapFromLocalizedStrings(Class<T> tClass) {
//        Logger.run("initializeTypeMaps");
//        for (Field f : LocalizedStrings.class.getDeclaredFields()) {
//            Type type = f.getGenericType();
//            if (type instanceof ParameterizedType) {
//                ParameterizedType pType = (ParameterizedType) type;
//                Type[] typeArgs = pType.getActualTypeArguments();
//                if (typeArgs.length == 2 && typeArgs[0] == String.class && typeArgs[1] == tClass)
//                    return Optional.of(f.getName());
//            }
//        }
//        return Optional.empty();
//    }
//
//    public static void setJsonStrings(Class<?> tClass, Map<String, Object> GivenMap) {
//        String mapName = getTypeMapFromLocalizedStrings(tClass).orElse("");
//        if (mapName.isEmpty()) return;
//        Map<String, Object> localizationStrings = ReflectionHacks.getPrivateStatic(LocalizedStrings.class, mapName);
//        localizationStrings.putAll(GivenMap);
//        ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, mapName, localizationStrings);
//    }

    public static <T extends HasSFWVersion> void loadCustomStringsFile(String fileName, Map<String, T> target, Class<T> tSetClass) {
        superstitioapi.Logger.debug("loadJsonStrings: " + tSetClass.getTypeName());
        String jsonString = Gdx.files.internal(makeLocalizationPath(Settings.language, fileName))
                .readString(String.valueOf(StandardCharsets.UTF_8));
        ParameterizedType typeToken =
                GetTypeOfMapByAComplexFunctionBecauseTheMotherfuckerGenericProgrammingWayTheFuckingJavaUse(tSetClass).orElse(null);
        Gson gson = new Gson();
        Map<String, T> map = gson.fromJson(jsonString, typeToken);
        map.forEach((id, strings) -> {
            strings.initialOrigin();
            if (strings instanceof HasTextID)
                ((HasTextID) strings).setTextID(id);
        });
        target.putAll(map);
    }

    private static <T> Optional<ParameterizedType> GetTypeOfMapByAComplexFunctionBecauseTheMotherfuckerGenericProgrammingWayTheFuckingJavaUse(
            Class<T> tClass) {
        Field[] var0 = DataManager.class.getDeclaredFields();

        for (Field f : var0) {
            Type type = f.getGenericType();
            if (!(type instanceof ParameterizedType)) continue;
            ParameterizedType pType = (ParameterizedType) type;
            Type[] typeArgs = pType.getActualTypeArguments();
            if (typeArgs.length == 2 && typeArgs[0].equals(String.class) && typeArgs[1].equals(tClass))
                return Optional.of($Gson$Types.newParameterizedTypeWithOwner(null, Map.class, String.class, typeArgs[1]));
        }
        return Optional.empty();
    }


    @SpireInitializer
    public static class SPTT_DATA {

        public static final Color SEX_COLOR = new Color(250.0F / 255.0F, 20.0F / 255.0F, 147.0F / 255.0F, 1.0F);
        public static final String BG_ATTACK_SEMEN = makeImgFilesPath_UI("bg_attack_semen");
        public static final String BG_ATTACK_512_SEMEN = makeImgFilesPath_UI("bg_attack_512_semen");
        // 在卡牌和遗物描述中的能量图标
        public String SMALL_ORB = makeImgFilesPath_Character_Lupa("small_orb");
        // 在卡牌预览界面的能量图标
        public String BIG_ORB = makeImgFilesPath_Character_Lupa("card_orb");
        // 小尺寸的能量图标（战斗中，牌堆预览）
        public String ENERGY_ORB = makeImgFilesPath_Character_Lupa("cost_orb");
        // 攻击牌的背景（小尺寸）
        public String BG_ATTACK_512 = makeImgFilesPath("bg_attack_512", "512");
        // 能力牌的背景（小尺寸）
        public String BG_POWER_512 = makeImgFilesPath("bg_power_512", "512");
        // 技能牌的背景（小尺寸）
        public String BG_SKILL_512 = makeImgFilesPath("bg_skill_512", "512");
        // 攻击牌的背景（大尺寸）
        public String BG_ATTACK_1024 = makeImgFilesPath("bg_attack", "1024");
        // 能力牌的背景（大尺寸）
        public String BG_POWER_1024 = makeImgFilesPath("bg_power", "1024");
        // 技能牌的背景（大尺寸）
        public String BG_SKILL_1024 = makeImgFilesPath("bg_skill", "1024");
        //选英雄界面的角色图标、选英雄时的背景图片
        public String LUPA_CHARACTER_BUTTON = makeImgFilesPath_Character_Lupa("Character_Button");
        // 人物选择界面的立绘
        public String LUPA_CHARACTER_PORTRAIT = makeImgFilesPath_Character_Lupa("Character_Portrait");
        public String MASO_CHARACTER_PORTRAIT = makeImgFilesPath_Character_Lupa("Character_Maso_Portrait");

        // 为原版人物枚举、卡牌颜色枚举扩展的枚举，需要写，接下来要用
        public static class LupaEnums {
            @SpireEnum
            public static AbstractPlayer.PlayerClass LUPA_Character;

            @SpireEnum(name = "LUPA_PINK")
            public static AbstractCard.CardColor LUPA_CARD;

            @SpireEnum(name = "LUPA_PINK")
            public static CardLibrary.LibraryType LUPA_LIBRARY;
        }

        public static class MasoEnums {
            @SpireEnum
            public static AbstractPlayer.PlayerClass MASO_Character;

            @SpireEnum(name = "MASO_PINK")
            public static AbstractCard.CardColor MASO_CARD;

            @SpireEnum(name = "MASO_PINK")
            public static CardLibrary.LibraryType MASO_LIBRARY;
        }


        public static class GeneralEnums {
            @SpireEnum
            public static AbstractPlayer.PlayerClass GENERAL_Virtual_Character;

            @SpireEnum(name = "SPTT_GENERAL_PINK")
            public static AbstractCard.CardColor GENERAL_CARD;

            @SpireEnum(name = "SPTT_GENERAL_PINK")
            public static CardLibrary.LibraryType GENERAL_LIBRARY;
        }

        // 为原版人物枚举、卡牌颜色枚举扩展的枚举，需要写，接下来要用
        public static class TempCardEnums {
            @SpireEnum
            public static AbstractPlayer.PlayerClass TempCard_Virtual_Character;
            @SpireEnum(name = "SPTT_TEMP_PINK")
            public static AbstractCard.CardColor TempCard_CARD;

            @SpireEnum(name = "SPTT_TEMP_PINK")
            public static CardLibrary.LibraryType TempCard_LIBRARY;
        }
    }

    public static class CanOnlyDamageDamageType {
        @SpireEnum
        public static DamageInfo.DamageType UnBlockAbleDamageType;

        @SpireEnum
        public static DamageInfo.DamageType NoTriggerMasoRelicDamageType;
    }
}