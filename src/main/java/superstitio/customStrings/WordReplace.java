package superstitio.customStrings;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordReplace {
    public String WordOrigin;
    public String WordReplace;

    public WordReplace() {
    }

    public WordReplace(String WordOrigin, String WordReplace) {
        this.WordOrigin = WordOrigin;
        this.WordReplace = WordReplace;
    }

    public static String replaceWord(final String string, List<superstitio.customStrings.WordReplace> replaceRules) {
        final String[] newString = {string};
        for (superstitio.customStrings.WordReplace replaceRule : replaceRules) {
            newString[0] = replace(newString[0], replaceRule.WordOrigin, replaceRule.WordReplace);
        }
        return newString[0];
    }

    public static String replace(String StringToReplace, CharSequence target, CharSequence replacement) {
        return Pattern
                .compile(target.toString(), Pattern.LITERAL)
                .matcher(StringToReplace)
                .replaceAll(Matcher.quoteReplacement(replacement.toString()));
    }

    public static String[] replaceWord(String[] strings, List<superstitio.customStrings.WordReplace> replaceRule) {
        if (strings.length != 0)
            for (int i = 0; i < strings.length; i++)
                strings[i] = replaceWord(strings[i], replaceRule);
        return strings;
    }

    public boolean hasNullOrEmpty() {
        return HasSFWVersion.isNullOrEmpty(this.WordOrigin) || HasSFWVersion.isNullOrEmpty(this.WordReplace);
    }

//    public static WordReplace getMockCardStringWithFlavor() {
//        WordReplace retVal = new WordReplace();
//        retVal.NAME = "[MISSING_TITLE]";
//        retVal.DESCRIPTION = "[MISSING_DESCRIPTION]";
//        retVal.UPGRADE_DESCRIPTION = "[MISSING_DESCRIPTION+]";
//        retVal.EXTENDED_DESCRIPTION = new String[]{"[MISSING_0]", "[MISSING_1]", "[MISSING_2]"};
//        retVal.FLAVOR = "[MISSING_FLAVOR]";
//        return retVal;
//    }
}