package com.example.baseutils;

import android.app.Application;

import org.itzheng.and.baseutils.BaseUtils;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-3-7.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BaseUtils.init(this);
    }
}
