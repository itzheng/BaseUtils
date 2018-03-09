package com.example.baseutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.itzheng.and.baseutils.common.Language;
import org.itzheng.and.baseutils.common.LanguageUtils;
import org.itzheng.and.baseutils.log.LogHelper;
import org.itzheng.and.baseutils.ui.UIUtils;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        BaseUtils.init(this);

        LanguageUtils.setAppLanguage(this, Locale.TRADITIONAL_CHINESE);
        testLanguage();
//        setLanguage();
        LanguageUtils.setAppLanguage(this, LanguageUtils.getSystemLocale());
        testLanguage();
    }

    private void setLanguage() {
//        LanguageUtils.setAppLanguage(this, Locale.SIMPLIFIED_CHINESE);
        LanguageUtils.setAppLanguage(this, new Locale("zh"));
    }

    private static final String TAG = "MainActivity";

    private void testLanguage() {
        Locale currentAppLocale = LanguageUtils.getCurrentAppLocale(this);
        LogHelper.d(TAG, "currentAppLocale isEquals US:" + LanguageUtils.isEquals(currentAppLocale, Locale.US));
        LogHelper.d(TAG, "currentAppLocale:" + currentAppLocale.toString());
        Locale systemLocale = LanguageUtils.getSystemLocale();
        LogHelper.d(TAG, "systemLocale:" + systemLocale.toString());
        Language auto = Language.auto();
//        Language auto = Language.AUTO;
        LogHelper.d(TAG, "auto Language:" + auto.language);
        LogHelper.d(TAG, "auto Language:" + UIUtils.getString(R.string.language_item_auto));
    }
}
