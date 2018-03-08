package org.itzheng.and.baseutils.common;

import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.itzheng.and.baseutils.ui.UIUtils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * 这个应该进行优化，只显示字符处理部分
 */
public class StrUtils {
    private StrUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return UIUtils.getString(resId);
    }

    public final static String UTF_8 = "utf-8";

    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        if (value != null && !"".equalsIgnoreCase(value.trim()) && !"null".equalsIgnoreCase(value.trim())) {
            return false;
        } else {
            return true;
        }
    }

    private static final String TAG = "StrUtils";

    public static String getText(EditText editText) {
        if (editText == null) {
            Log.w(TAG, "editText is null");
            return "";
        } else {
            return editText.getText().toString().trim();
        }
    }

    public static String getText(TextView textView) {
        if (textView == null) {
            Log.w(TAG, "textView is null");
            return "";
        } else {
            return textView.getText().toString().trim();
        }
    }

    /**
     * 判断多个字符串是否相等，如果其中有一个为空字符串或者null，则返回false，只有全相等才返回true
     */
    public static boolean isEquals(String... agrs) {
        String last = null;
        for (int i = 0; i < agrs.length; i++) {
            String str = agrs[i];
            if (isEmpty(str)) {
                return false;
            }
            if (last != null && !str.equalsIgnoreCase(last)) {
                return false;
            }
            last = str;
        }
        return true;
    }

    /**
     * 返回一个高亮spannable
     *
     * @param content 文本内容
     * @param color   高亮颜色
     * @param start   起始位置
     * @param end     结束位置
     * @return 高亮spannable
     */
    public static CharSequence getHighLightText(String content, int color, int start, int end) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        start = start >= 0 ? start : 0;
        end = end <= content.length() ? end : content.length();
        SpannableString spannable = new SpannableString(content);
        CharacterStyle span = new ForegroundColorSpan(color);
        spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    /**
     * 获取链接样式的字符串，即字符串下面有下划线
     *
     * @param resId 文字资源
     * @return 返回链接样式的字符串
     */
    public static Spanned getHtmlStyleString(int resId) {
        StringBuilder sb = new StringBuilder();
        sb.append("<a href=\"\"><u><b>").append(UIUtils.getString(resId)).append(" </b></u></a>");
        return Html.fromHtml(sb.toString());
    }

    /**
     * 格式化文件大小，不保留末尾的0
     */
    public static String formatFileSize(long len) {
        return formatFileSize(len, false);
    }

    /**
     * 格式化文件大小，保留末尾的0，达到长度一致
     */
    public static String formatFileSize(long len, boolean keepZero) {
        String size;
        DecimalFormat formatKeepTwoZero = new DecimalFormat("#.00");
        DecimalFormat formatKeepOneZero = new DecimalFormat("#.0");
        if (len < 1024) {
            size = String.valueOf(len + "B");
        } else if (len < 10 * 1024) {
            // [0, 10KB)，保留两位小数
            size = String.valueOf(len * 100 / 1024 / (float) 100) + "KB";
        } else if (len < 100 * 1024) {
            // [10KB, 100KB)，保留一位小数
            size = String.valueOf(len * 10 / 1024 / (float) 10) + "KB";
        } else if (len < 1024 * 1024) {
            // [100KB, 1MB)，个位四舍五入
            size = String.valueOf(len / 1024) + "KB";
        } else if (len < 10 * 1024 * 1024) {
            // [1MB, 10MB)，保留两位小数
            if (keepZero) {
                size = String.valueOf(formatKeepTwoZero.format(len * 100 / 1024 / 1024 / (float) 100)) + "MB";
            } else {
                size = String.valueOf(len * 100 / 1024 / 1024 / (float) 100) + "MB";
            }
        } else if (len < 100 * 1024 * 1024) {
            // [10MB, 100MB)，保留一位小数
            if (keepZero) {
                size = String.valueOf(formatKeepOneZero.format(len * 10 / 1024 / 1024 / (float) 10)) + "MB";
            } else {
                size = String.valueOf(len * 10 / 1024 / 1024 / (float) 10) + "MB";
            }
        } else if (len < 1024 * 1024 * 1024) {
            // [100MB, 1GB)，个位四舍五入
            size = String.valueOf(len / 1024 / 1024) + "MB";
        } else {
            // [1GB, ...)，保留两位小数
            size = String.valueOf(len * 100 / 1024 / 1024 / 1024 / (float) 100) + "GB";
        }
        return size;
    }

    /**
     * 数字保留两位小数,常用于金额操作
     *
     * @param number
     * @return
     */
    public static String format2Decimal(double number) {
//        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
//        return df.format(number);
        return format2String(number, 2);
    }

    /**
     * 最多显示4位数，最多显示2位小数
     * 当前值是999.999，显示为999.9.
     * 假设当前值是99.9999，显示为99.99
     * <p>
     * 如果当前为9.9999，则显示9.99
     *
     * @param value
     * @return
     */
    public static String showUnitValue(double value) {
        if (value >= 1000) {
            //显示整数部分
            return (int) value + "";
        } else if (value >= 100) {
            //显示1位小数
            return format2String(value, 1);
        } else {
            return format2String(value, 2);
        }
    }

    /**
     * 将数字转成字符串，然后显示指定位数的小数，如果没有则补0
     *
     * @param number
     * @param mantissa 要显示的小数长度
     * @return
     */
    private static String format2String(double number, int mantissa) {
        //将整数部分和小数部分切开
        String[] split = (number + "").split("\\.");
        String integer = "";
        String decimal = "";
        if (split == null || split.length == 0) {
            integer = "0";
        } else {
            integer = split[0];
            if (TextUtils.isEmpty(integer)) {
                integer = "0";
            }
        }
        String tempDecimal = "";
        if (split.length >= 2) {
            tempDecimal = split[1];
        }
        //获取小数点
        for (int i = 0; i < mantissa; i++) {
            if (i == 0) {
                decimal = ".";
            }
            if (tempDecimal.length() <= i) {
                decimal = decimal + "0";
            } else {
                decimal = decimal + tempDecimal.charAt(i);
            }
        }
        return integer + decimal;
    }

    /**
     * 数字保留两位小数,常用于金额操作
     *
     * @param number
     * @return
     */
    public static String format2Decimal(String number) {

        return format2Decimal(toDouble(number));
    }

    /**
     * 将字符串转成整数
     *
     * @param number
     * @return
     */
    public static int toInt(String number) {

        int num = 0;
        try {
            num = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return num;
    }

    /**
     * 将长整形转成整数
     *
     * @param number
     * @return
     */
    public static int toInt(long number) {
        int num = 0;
        try {
            num = (int) (number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 将双进度数字转成整数
     *
     * @param number
     * @return
     */
    public static int toInt(double number) {
        int num = 0;
        try {
            num = (int) (number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 将字符串转成整数
     *
     * @param number
     * @return
     */
    public static int toInt(float number) {

        int num = 0;
        try {
            num = (int) (number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return num;
    }

    /**
     * 将整型转成整数
     *
     * @param number
     * @return
     */
    public static int toInt(int number) {
        return number;
    }

    /**
     * 将字符串转成单精度小数
     *
     * @param number
     * @return
     */
    public static float toFloat(String number) {
        float num = 0;
        try {
            num = Float.parseFloat(number);
        } catch (NumberFormatException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }

        return num;
    }

    /**
     * 将字符串转成双精度小数
     *
     * @param number
     * @return
     */
    public static double toDouble(String number) {

        double num = 0;
        try {
            num = Double.parseDouble(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return num;
    }

    /**
     * 将字符串转成长整型
     *
     * @param number
     * @return
     */
    public static long toLong(String number) {

        long num = 0;
        try {
            num = Long.parseLong(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return num;
    }

    /**
     * 将双精度转成长整型
     *
     * @param number
     * @return
     */
    public static long toLong(double number) {

        long num = 0;
        try {
            num = (long) (number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return num;
    }

    /**
     * 将短整型转成长整型
     *
     * @param number
     * @return
     */
    public static long toLong(int number) {
        return number;
    }

    /**
     * 将单精度数字转成长整形
     *
     * @param number
     * @return
     */
    public static long toLong(float number) {
        long num = 0;
        try {
            num = (long) (number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 将长整型转成长整形
     *
     * @param number
     * @return
     */
    public static long toLong(long number) {
        return number;
    }

    /**
     * 将任意类型转成字符串避免出现null等无效字符
     *
     * @param value
     * @return
     */
    public static String toString(Object value) {
        if (value == null || "null".equalsIgnoreCase(value.toString())) {
            return "";
        }
        return value.toString();
    }

    /**
     * 去掉所有空格
     *
     * @param value
     * @return
     */
    public static String toTrimAll(Object value) {
        if (value == null || "null".equalsIgnoreCase(value.toString())) {
            return "";
        }
        String s = value.toString();
        if (s == null) {
            return "";
        }
        s = s.replace(" ", "");//去掉英文空格
        s.replace(" ", "");//去掉中文空格
        return s;
    }

    /**
     * 保留数字和字母
     *
     * @param value
     * @return
     */
    public static String toWordNum(Object value) {

        return toString(value).replaceAll("[^\\w]|_", "");

    }

    /**
     * 转换成日期格式：如2017-06-06
     *
     * @param value
     * @return
     */
    public static String toDate(String value) {
        if (value == null || "null".equalsIgnoreCase(value.toString())) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return "";
        } else {
            return df.format(date);
        }
    }

    /**
     * 转换换成隐藏中间的手机号码格式
     * 如138****1234
     *
     * @param value
     * @return
     */
    public static String toHintMobile(String value) {
        if (value == null || "null".equalsIgnoreCase(value.toString())) {
            return "";
        }
        if (value.length() > 8) {
            StringBuffer sb = new StringBuffer(value);
            sb.replace(3, 7, "****");
            return sb.toString();

        }

        return value.toString();
    }

    /**
     * 将字符串转成钱的格式,保存两位小数,如0.00
     *
     * @param number
     * @return
     */
    public static String toMoney(String number) {
        return format2Decimal(toDouble(number));
    }

    /**
     * 将字符串转成钱的格式,保存两位小数,如0.00
     *
     * @param number
     * @return
     */
    public static String toMoney(double number) {
        return format2Decimal(number);
    }

    /**
     * 将字符串截取,末尾...
     *
     * @param s    字符串
     * @param size 要保存的个数
     * @return
     */
    public static String truncate(String s, int size) {
        if (TextUtils.isEmpty(s)) {
            return toString(s);
        }
        if (s.length() <= size) {
            return s;
        }

        return s.substring(0, size) + "...";
    }


    private static String currentYear = "";

    public static String getCurrentYear() {
        if (TextUtils.isEmpty(currentYear)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
            currentYear = sdf.format(date);
        }
        return currentYear;
    }

    public static String getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("M");
        Date date = new Date();
        return sdf.format(date);
    }

    public static String getCurrentDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("d");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 获取当前的系统时间
     *
     * @return
     */
    public static String getCurrentDateTime() {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // new Date()为获取当前系统时间
        return df.format(new Date());
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getGUID() {
        return UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }

    public static String getPercentPack(int innerQty, int middleQty, int outterQty) {

        return innerQty + "/" + middleQty + "/" + outterQty;
    }

    public static String getLWH(double packL, double packW, double packH) {
        return packL + "x" + packW + "x" + packH + "cm";
    }

    public static boolean getTrueOrFalse() {
        return System.currentTimeMillis() % 2 == 0;
    }


    public static String toHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes) {
            String hexString = Integer.toHexString(b & 0xff);
            if (hexString.length() < 2) {
                sb.append("0");
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static String toHexString(int bytes) {
        return Integer.toHexString(bytes & 0XFF);
    }

    public static String toHexString(ArrayList<Byte> bytes) {
        if (bytes == null) {
            return "";
        }
        //Byte和byte位数不一样
        byte[] data = listToBytes(bytes);
        return toHexString(data);
    }

    public static byte[] listToBytes(ArrayList<Byte> bytes) {
        if (bytes == null) {
            return null;
        }
//        ArrayList<Byte> newBytes=bytes.
        int length = bytes.size();
        byte[] data = new byte[length];
        for (int i = 0; i < length; i++) {
            if (data.length <= i || bytes.size() <= i) {
                break;
            }
            if (bytes == null || bytes.get(i) == null) {
                break;
            }
            data[i] = bytes.get(i);
        }
        return data;
    }

    /**
     * 截取byte数组
     *
     * @param bytes
     * @param fromIndex 从哪个位置截取
     * @param length    截取的长度
     * @return
     */
    public static byte[] subBytes(byte[] bytes, int fromIndex, int length) {
        if (bytes == null) {
            return null;
        }
        byte[] newByte = new byte[length];
        for (int i = 0; i < newByte.length; i++) {
            if (bytes.length < i + fromIndex) {
                break;
            }
            newByte[i] = bytes[i + fromIndex];
        }
        return newByte;
    }
}
