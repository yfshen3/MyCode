package org.lf.admin.api.baseapi.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author sunwill
 */
public class StringUtils {
    /**
     * 整型正则表达式
     */
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");

    /**
     * 换行匹配
     */
    private static final Pattern CHANGE_LINE_PATTERN = Pattern.compile("\\t|\r|\n");

    /**
     * 将字符串进行md5加密
     *
     * @param s
     * @return
     */
    public static final String toMD5(String s) {
        try {
            return (new String(DigestUtils.md5Hex(s.getBytes("UTF-8"))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串进行SHA1加密
     *
     * @param s
     * @return
     */
    public final static String toSHA1(String s) {
        try {
            return (new String(DigestUtils.sha1Hex(s.getBytes("UTF-8"))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断指定字符串是否为空,为空返回true
     *
     * @param str
     * @return 为空返回true
     */
    public static final boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断字符串是否为整型数字
     *
     * @param str
     * @return
     */
    public static final boolean isInteger(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return INTEGER_PATTERN.matcher(str).matches();
    }

    /**
     * 将规定长度之外的字符替换为省略号...
     *
     * @param value
     * @param length
     */
    public final static String sub(String value, int length) {
        if (value.length() < length) {
            return value;
        } else {
            return value.substring(0, length - 3) + "...";
        }
    }

    /**
     * 左右填充
     *
     * @param str
     * @param padChar
     * @param length
     */
    public final static String pad(String str, char padChar, int length) {
        if (padChar > 128) {
            throw new IllegalArgumentException("填充字符必须为ASCII码！");
        }

        if (str.length() > length) {
            return str;
        }

        int padSize = (length - str.length()) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padSize; i++) {
            sb.append(padChar);
        }
        sb.append(str);
        for (int i = 0; i < padSize; i++) {
            sb.append(padChar);
        }
        return sb.toString();
    }

    /**
     * 左填充
     *
     * @param str
     * @param padChar
     * @param length
     */
    public final static String lpad(String str, char padChar, int length) {
        if (padChar > 128) {
            throw new IllegalArgumentException("填充字符必须为ASCII码！");
        }

        if (str.length() > length) {
            return str;
        }

        int padSize = length - str.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padSize; i++) {
            sb.append(padChar);
        }
        sb.append(str);
        return sb.toString();
    }

    /**
     * 右填充
     *
     * @param str
     * @param padChar
     * @param length
     */
    public final static String rpad(String str, char padChar, int length) {
        if (padChar > 128) {
            throw new IllegalArgumentException("填充字符必须为ASCII码！");
        }

        if (str.length() > length) {
            return str;
        }

        int padSize = length - str.length();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        for (int i = 0; i < padSize; i++) {
            sb.append(padChar);
        }
        return sb.toString();
    }

    /**
     * UUID是由一个36位的数字组合组成,表现出来的形式例如: 550E8400-E29B-11D4-A716-446655440000
     */
    public final static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * UUID是由一个36位的数字组成,表现出来的形式例如: 550E8400-E29B-11D4-A716-446655440000
     * 本函数去除其中的'-'字符，返回32位字母数字组合。
     */
    public final static String getShortUUID() {
        String uuid = getUUID();
        return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
    }

    /**
     * 对字符串进行base64加密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public final static String base64Encode(String str) throws Exception {
        if (isEmpty(str)) {
            return null;
        }
        return new String(Base64.encodeBase64(str.getBytes("UTF-8")), "UTF-8");
    }

    /**
     * 对字符串进行base64解密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public final static String base64Decode(String str) throws Exception {
        if (isEmpty(str)) {
            return null;
        }
        return new String(Base64.decodeBase64(str.getBytes("UTF-8")), "UTF-8");
    }

    /**
     * 生成指定长度的字符串。字符串中的字符随机取自base。
     */
    public final static String randomString(char[] base, int size) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            int number = random.nextInt(base.length);
            sb.append(base[number]);
        }
        return sb.toString();
    }

    /**
     * 首字母大写，其他小写
     */
    public static final String toInitCapital(String in) {
        if (isEmpty(in)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        char c = in.trim().charAt(0);
        if (c >= 97 && c <= 122) {
            // 小写字母
            sb.append(c - 32);
        } else {
            sb.append(c);
        }
        sb.append(in.substring(1).toLowerCase());

        return sb.toString();
    }

    /**
     * 去除字符串中的空格，换行符，回车符，制表符
     */
    public static final String replaceWithBlank(String str) {
        String afterStr = null;
        Matcher m = CHANGE_LINE_PATTERN.matcher(str.trim());
        afterStr = m.replaceAll("");
        return afterStr;
    }

    /**
     * 将values中的内容使用splitChar进行分割，拼出一个字符串。 例如：UserID1|UserID2|UserID3
     *
     * @param values
     * @return
     */
    public static final String toString(List<String> values, char splitChar) {
        StringBuilder sb = new StringBuilder();
        sb.append(values.get(0));

        for (int i = 1; i < values.size(); i++) {
            sb.append(splitChar).append(values.get(i));
        }

        return sb.toString();
    }

    public static final String nullToEmpty(String str) {
        if (str == null) {
            str = "";
        }
        return str;
    }
}
