package com.sb.example.common.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * @author jack
 * @version 2013-05-22
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {


    /**
     * Trims every string in the specified strings array.
     *
     * @param strings the specified strings array, returns {@code null} if the
     *                specified strings is {@code null}
     * @return a trimmed strings array
     */
    public static String[] trimAll(final String[] strings) {
        if (null == strings) {
            return null;
        }

        return Arrays.stream(strings).map(StringUtils::trim).toArray(size -> new String[size]);
    }

    /**
     * Determines whether the specified strings contains the specified string, ignoring case considerations.
     *
     * @param string  the specified string
     * @param strings the specified strings
     * @return {@code true} if the specified strings contains the specified string, ignoring case considerations, returns {@code false}
     * otherwise
     */
    public static boolean containsIgnoreCase(final String string, final String[] strings) {
        if (null == strings) {
            return false;
        }

        return Arrays.stream(strings).anyMatch(str -> StringUtils.equalsIgnoreCase(string, str));
    }

    /**
     * Decodes the specified string.
     *
     * @param str the specified string
     * @return URL decoded string
     */
    public static String decodeUrl(final String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (final Exception e) {
            return str;
        }
    }

    /**
     * Sanitizes the specified file name.
     *
     * @param unsanitized the specified file name
     * @return sanitized file name
     */
    public static String sanitizeFilename(final String unsanitized) {
        return unsanitized.
                replaceAll("[\\?\\\\/:|<>\\*\\[\\]]", "-"). // ? \ / : | < > * [ ] to -
                replaceAll("\\s+", "-");              // white space to -
    }


    /*
     * 中文转unicode编码
     */
    public static String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String lowerFirst(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        } else {
            return str.substring(0, 1).toLowerCase() + str.substring(1);
        }
    }

    /**
     * 首字段大写
     * @param str
     * @return
     */
    public static String upperFirst(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }

    /**
     * 驼峰转下划线，并将结果转换成大写
     * @param para
     * @return
     */
    public static String HumpToUnderline(String para) {
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;//定位
        for (int i = 0; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    public static String replaceSqlKey(String text) {
        if (isBlank(text)) {
            return "";
        }
        text = text.toLowerCase();
        return text.replace("insert", "").replace("select", "").replace("and", "")
                .replace("delete", "").replace("update", "")
                .replace("drop", "").replace("truncate", "").replace("table", "")
                .replace("grant", "").replace("order", "").replace("where", "")
                .replace("like", "").replace("=", "").replace("<", "")
                .replace(">", "").replace("<=", "").replace(">=", "");
    }

    /**
     * 缩略字符串（不区分中英文字符）
     *
     * @param str    目标字符串
     * @param length 截取长度
     * @return
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 缩略字符串（替换html）
     *
     * @param str    目标字符串
     * @param length 截取长度
     * @return
     */
    public static String rabbr(String str, int length) {
        return abbr(replaceHtml(str), length);
    }


    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf(trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    /**
     * 转换为String类型
     */
    public static String toString(Object val) {
        if (null == val) return "";
        return val.toString();
    }

    /**
     * 读取流到字符串
     *
     * @param is
     * @return
     */
    public static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static int getLen(Object object) {
        if (null == object) {
            return 0;
        } else {
            return object.toString().trim().length();
        }
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean checkMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 手机号验证
     *
     * @param obj
     * @return
     */
    public static boolean checkMobile(final Object obj) {
        if (null == obj) {
            return false;
        }
        return checkMobile(obj.toString());
    }

    /**
     * 验证邮箱
     *
     * @param obj
     * @return
     */
    public static boolean checkEmail(final Object obj) {
        if (null == obj) {
            return false;
        }
        return checkEmail(obj.toString());
    }

    public static boolean checkQQ(final Object obj) {
        return checkQQ(obj.toString());
    }

    public static boolean checkQQ(final String str) {
        if (StringUtils.isEmpty(str) || str.startsWith("0") || str.length() > 18) {
            return false;
        }
        return NumberUtils.isNumber(str);
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        if (StringUtils.isEmpty(email) || email.length() > 150) {
            return false;
        }

        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 计算字符的长度
     *
     * @param str
     * @return
     */
    public static int getLen(String str) {
        if (StringUtils.isNotEmpty(str)) {
            return str.length();
        }
        return 0;
    }

    public static String subStr(String source, int len) {
        if (StringUtils.isEmpty(source)) {
            return "";
        }
        if (source.length() <= len) {
            return source;
        }
        return source.substring(0, len);
    }

    public static String toHtml(String source) {
        if (isEmpty(source)) {
            return "";
        }
        source = source.replace("&lt;", "<").replace("&gt;", ">");
        source = source.replace("<script>", "").replace("</script>", "").replace("<script", "").replace("javascript", "");
        return source;
    }


    public static List<String> convertToStrs(Object obj) {
        List<String> rets = Lists.newLinkedList();
        if (null != obj && obj instanceof List) {
            List list = (List) obj;
            if (!list.isEmpty()) {
                for (Object o : list) {
                    if (null != o) {
                        rets.add(o.toString());
                    }
                }
            }
        }
        return rets;
    }

    /**
     * 整数数组转成List
     *
     * @param objs int[]
     * @return
     */
    public static List<String> convertIntArrayToStrs(int[] objs) {
        List<String> rets = Lists.newLinkedList();
        if (null != objs && objs.length > 0) {
            for (Object o : objs) {
                if (null != o) {
                    rets.add(String.valueOf(o));
                }
            }
        }
        return rets;
    }

    public final static List<String> DIRTY_WORD = Arrays.asList("操蛋", "傻B", "傻b",
            "畜生", "<", ">", "script", "QQ", "qq", "群", "微信", "他妈的", "妈",
            "weixin", "电话", "手机", "草尼", "换网投", "有病", "脑残");

    public static String dirtyWordFilter(String value) {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        for (String s : DIRTY_WORD) {
            if (value.contains(s)) {
                value = value.replace(s, "*");
            }
        }
        return value;
    }

    public static String convertStringArrayToString(String[] strArr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        if (null != strArr && strArr.length > 0) {
            for (String str : strArr) {
                sb.append(str).append(delimiter);
            }
            return sb.substring(0, sb.length() - 1);
        }
        return null;
    }

    /**
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
     *
     * @param content  传入的字符串
     * @param frontNum 保留前面字符的位数
     * @param endNum   保留后面字符的位数
     * @return 带星号的字符串
     */
    public static String replaceString2Star(String content, int frontNum, int endNum) {
        if (content == null || content.trim().isEmpty())
            return content;

        int len = content.length();

        if (frontNum >= len || frontNum < 0 || endNum >= len || endNum < 0)
            return content;

        if (frontNum + endNum >= len)
            return content;


        int beginIndex = frontNum;
        int endIndex = len - endNum;
        char[] cardChar = content.toCharArray();

        for (int j = beginIndex; j < endIndex; j++) {
            cardChar[j] = '*';
        }

        return new String(cardChar);
    }


    private final static int[] SIZE_TABLE = {9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999,
            Integer.MAX_VALUE};

    /**
     * 计算一个整数的大小
     *
     * @param x
     * @return
     */
    public static int sizeOfInt(int x) {
        for (int i = 0; ; i++)
            if (x <= SIZE_TABLE[i]) {
                return i + 1;
            }
    }

    /**
     * 判断字符串的每个字符是否相等
     *
     * @param str
     * @return
     */
    public static boolean isCharEqual(String str) {
        return str.replace(str.charAt(0), ' ').trim().length() == 0;
    }

    /**
     * 确定字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为空格、空(“)”或null。
     *
     * @param str
     * @return
     */
    public static boolean equalsNull(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0 || str.equalsIgnoreCase("null")) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines whether the specified strings contains the specified string.
     *
     * @param string  the specified string
     * @param strings the specified strings
     * @return {@code true} if the specified strings contains the specified string, returns {@code false} otherwise
     */
    public static boolean contains(final String string, final String[] strings) {
        if (null == strings) {
            return false;
        }

        return Arrays.stream(strings).anyMatch(str -> StringUtils.equals(string, str));
    }

    /**
     * Determines whether the specified string is a valid URL.
     *
     * @param string the specified string
     * @return {@code true} if the specified string is a valid URL, returns {@code false} otherwise
     */
    public static boolean isURL(final String string) {
        try {
            new URL(string);

            return true;
        } catch (final MalformedURLException e) {
            return false;
        }
    }

    /**
     * 判断是否为中文字符串
     *
     * @param str
     * @return
     */
    public static boolean isChinese(final String str) {
        String regEx = "^[\\u4e00-\\u9fa5]*$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);

        return m.find();
    }

    /**
     * 清除所有空格跟换行符
     *
     * @param str
     * @return
     */
    public static String cleanBlank(String str) {
        return str.replaceAll(" +", "").replaceAll("\n", "").replaceAll("\r", "");
    }

    public static boolean isJson(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        str = str.trim();
        if ((str.startsWith("{") && str.endsWith("}")) ||
                (str.startsWith("[{") && str.endsWith("}]"))) {
            return true;
        }
        return false;
    }


    public static String getDomainName(String fullUrl) {
        if (StringUtils.isEmpty(fullUrl)) {
            return "";
        }
        fullUrl = fullUrl.toLowerCase().replace("https://", "")
                .replace("http://", "");
        int end = fullUrl.indexOf("/");
        if (end > 0) {
            fullUrl = fullUrl.substring(0, end);
        }
        return fullUrl;
    }

    public static String getRootDomainName(String fullUrl) {
        String domainName = getDomainName(fullUrl);
        if (domainName.startsWith("www.")) {
            return domainName.replace("www.", "");
        }
        return domainName;
    }


    /**
     * @param clientUrl
     * @param domainNameSettings
     * @return
     */
    public static boolean checkDomainNameInSpecific(String clientUrl, String domainNameSettings) {
        final String domainName = getRootDomainName(clientUrl);
        if (StringUtils.isNotBlank(domainNameSettings) && isNotEmpty(domainName)) {
            String[] specialUrls = domainNameSettings.split(",");
            for (String url : specialUrls) {
                url = StringUtils.getRootDomainName(url);
                if (url.contains(".") && domainName.startsWith(url)) {
                    return true;
                }
            }
        }
        return false;
    }

  /*  public static void main(String[] args) {
        String url="https://www.baidu.com/1";
        System.out.println(getRootDomainName(url));
        System.out.println(checkDomainNameInSpecific(url,"www.1baidu.com;baidu.com"));
    }*/


}
