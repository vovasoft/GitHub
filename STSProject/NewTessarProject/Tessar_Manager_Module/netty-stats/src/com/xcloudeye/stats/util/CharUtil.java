package com.xcloudeye.stats.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharUtil {

    public static String cutString(String str, String key) {
        String[] strs = str.split(key);
        int count = 0;
        for (String element : strs) {
            count++;
            if (count == 2) {
                return element;
            }
        }
        return str;
    }

    /**
     * @param str The String
     * @param key
     * @return Return the third string which are separated by special characters.
     */
    public static String cut3String(String str, String key, int num) {
        String[] strs = str.split(key);
        int count = 0;
        for (String element : strs) {
            count++;
            if (count == num) {
                return element;
            }
        }
        return str;
    }

    public static String[] splitChannel(String channel, String regex) {
        String[] split = channel.split(regex);
        return split;
    }

    public static boolean checkPattern(String channel) {
        Pattern pattern = Pattern.compile("[0-9a-zA-Z]+_[0-9a-zA-Z]+_[0-9a-zA-Z]+");
        Matcher matcher = pattern.matcher(channel);
        boolean b = matcher.matches();
        return b;
    }

    /**
     * @param str String
     * @param key Special characters
     * @return
     */
    public static String[] cutStringToArray(String str, String key) {
        String[] strs = str.split(key);
        return strs;
    }

    /**
     * @param str The string of input
     * @return If the string match the format XXX_XXX_XXX return true,else return false.
     */
    public static boolean judgeStringMatchFormat(String str) {
        String format = "^([a-zA-Z0-9])+(\\_)(([a-zA-Z0-9])+)+\\_(([a-zA-Z0-9])+)+$";
        return str.matches(format);
    }

    /**
     * @param str Input string.
     * @return True or false.
     */
    public static boolean judgeStringIsNumber(String str) {
        String format = "^[0-9]+$";
        return str.matches(format);
    }
}
