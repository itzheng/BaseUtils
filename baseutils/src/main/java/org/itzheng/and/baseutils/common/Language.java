package org.itzheng.and.baseutils.common;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-3-7.
 */

import org.itzheng.and.baseutils.R;
import org.itzheng.and.baseutils.ui.UIUtils;

import java.util.Locale;

/**
 * 语言参数
 */
public class Language {
    //
//    static public final Language AUTO = createConstant(UIUtils.getString(R.string.language_item_auto),
//            LanguageUtils.getSystemLocale());

    /**
     * 获取当前语言下，Auto对应的语言
     * 如果使用的是变量，当系统语言改变时，对应的文字无法自动改变
     *
     * @return
     */
    public static final Language auto() {
        return createConstant(UIUtils.getString(R.string.language_item_auto),
                LanguageUtils.getSystemLocale());
    }

    static public final Language ENGLISH = createConstant("English", Locale.ENGLISH);
    static public final Language ENGLISH_GB = createConstant("English(UK)", Locale.UK);
    static public final Language ENGLISH_US = createConstant("English(US)", Locale.US);
    static public final Language CHINESE = createConstant("中文", Locale.CHINESE);
    static public final Language CHINESE_CN = createConstant("简体中文（大陆）", Locale.SIMPLIFIED_CHINESE);
    static public final Language CHINESE_HK = createConstant("繁體中文（香港）", new Locale("zh", "HK"));
    static public final Language CHINESE_TW = createConstant("繁體中文（台灣）", Locale.TAIWAN);


    public String language;
    public Locale locale;

    public Language() {
    }

    public Language(String language, Locale locale) {
        this.language = language;
        this.locale = locale;
    }

    private static Language createConstant(String language, Locale locale) {
        return new Language(language, locale);
    }
}

