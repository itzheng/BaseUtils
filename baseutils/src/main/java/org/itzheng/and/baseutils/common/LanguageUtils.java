package org.itzheng.and.baseutils.common;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import org.itzheng.and.baseutils.ui.UIUtils;

import java.util.Locale;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-3-7.
 */
public class LanguageUtils {
    /**
     * 系统默认的语言环境
     */
    private static Locale mSysLocal;

    /**
     * 设置当前APP语言
     *
     * @param locale {@link Locale}
     */
    public static void setAppLanguage(Locale locale) {
        Resources res = UIUtils.getContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (mSysLocal == null) {
            //如果系统默认的语言环境为空，则在设置之前，将语言环境设置
            //不考虑通过其他方式设置的情况
            mSysLocal = conf.locale;
        }
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }

    /**
     * 获取当前APP的语言
     *
     * @return
     */
    public static Locale getCurrentAppLocale() {
        if (true) {
            Resources res = UIUtils.getContext().getResources();
            Configuration conf = res.getConfiguration();
            if (conf.locale != null) {
                return conf.locale;
            }
        }
        //这个就是当前APP的语言
        return Locale.getDefault();
    }

    /**
     * 获取当前系统的语言
     *
     * @return
     */
    public static Locale getSystemLocale() {
        if (mSysLocal == null) {
            mSysLocal = getCurrentAppLocale();
        }
        return mSysLocal;
    }

    /**
     * 判断两种语言是否相同
     *
     * @param locale1
     * @param locale2
     * @return
     */
    public static boolean isEquals(Locale locale1, Locale locale2) {
        //两个都为空
        if (locale1 == null && locale2 == null) {
            return true;
        }
        //其中一个为空
        if (locale1 == null || locale2 == null) {
            return false;
        }
        //都不为空的时候
        return locale1.equals(locale2);
    }

    /**
     * 新建一个Locale
     *
     * @param language Locale.toString();如：zh_TW
     * @return
     */
    public static Locale newLocale(String language) {
        if (language == null) {
            return null;
        }
        String[] value = language.split("_");
        if (value == null || value.length == 0) {
            return null;
        }
        String lang = "";
        String country = "";
        lang = value[0];
        if (value.length > 1) {
            country = value[1];
        }
        return new Locale(lang, country);
    }
}
