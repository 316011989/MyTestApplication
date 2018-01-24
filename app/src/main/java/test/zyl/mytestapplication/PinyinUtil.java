package test.zyl.mytestapplication;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Pattern;


/**
 * 拼音工具类
 */
public class PinyinUtil {
    public static String[] getStringArray(char c) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        //字符转成大写
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        //不需要音调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //v作v处理
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        try {
            return PinyinHelper.toHanyuPinyinStringArray(c, format);
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        return null;
    }


    private static String stringsToString(String[] strings) {
        String pinyin = "";
        for (int x = 0; x < strings.length; x++) {
            pinyin += strings[x];
        }
        return pinyin;
    }

    public static String firstAl(String str) {
        //将字符串去除前后空格,然后转成大写，并转成charArray数组
        String newStr = str.trim().toUpperCase();
        char[] chars = newStr.toCharArray();
        //判断第一个字是不是汉字
        String[] strings = getStringArray(chars[0]);
        String first = "";
        if (strings == null) {
            //判断第一个字符是不是英文字符，如果是，则设置开头字符为第一个字符，否则设置为#
            if (String.valueOf(chars[0]).matches("^[a-zA-Z]*"))
                first = String.valueOf(chars[0]);
            else
                first = "#";
        } else {
            first = (String.valueOf(strings[0].charAt(0)));
        }
        return first;
    }

    /**
     * 将汉字转换为全拼
     */
    public static String getPinYin(String src) {
        char[] t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (Character.toString(t1[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                }
            }
            return t4.toUpperCase();
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4.toUpperCase();
    }


    /**
     * 将汉字转换为首字母
     */
    public static String getPinYinFirst(String src) {
        char[] t1 = src.toCharArray();
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (Character.toString(t1[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    t4 += CharacterParser.Companion.getInstance().getFirstSell(t1[i] + "");
                }
            }
            return t4.toUpperCase();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return t4.toUpperCase();
    }


    /**
     * 获取首字母
     *
     * @param str
     * @return
     */
    public static String getAlpha(String str, Boolean tag) {
        if (str == null || str.trim().length() == 0) {
            return "#";
        }
        String c = str.trim().substring(0, 1);
        // 正则表达式匹配
        Pattern p_pattern = Pattern.compile("^[A-Za-z]+$");
        Pattern h_pattern = Pattern.compile("[\u4e00-\u9fa5]");
        Pattern s_pattern = Pattern.compile("^[0-9]*$");
        if (p_pattern.matcher(c).matches()) {
            return (c).toUpperCase(); // 将小写字母转换为大写
        } else if (h_pattern.matcher(c).matches()) {
            String s = getPinYinHeadChar(c);
            String newS = null;
            if (s != null) {
                newS = s.trim().substring(0, 1).toUpperCase();
            }
            return newS;
        }
        if (tag) {
            if (s_pattern.matcher(str).matches()) {
                return str;
            }
        }
        return "#";
    }

    // 返回中文的首字母
    public static String getPinYinHeadChar(String str) {

        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }


}
