package org.itzheng.and.baseutils.common;

import android.os.Handler;
import android.os.Looper;

/**
 * Title:延时工具<br>
 * Description: <br>
 * 可以指定一个时间，延时执行某个任务，该延时可以被取消，
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2017-12-14.
 */
public class DelayedUtils {
    Handler handler = new Handler(Looper.getMainLooper());
    private Runnable mRunnable;

    public static DelayedUtils newInstance() {
        return new DelayedUtils();
    }

    public void postDelayed(Runnable r, long delayMillis) {
        //添加任务之前，先移除任务，目前只能支持一个任务
        removeCallback();
        mRunnable = r;
        handler.postDelayed(mRunnable, delayMillis);
    }

    /**
     * 移除所有监听
     */
    public void removeCallback() {
        if (mRunnable != null) {
            handler.removeCallbacks(mRunnable);
        }
    }

    /**
     * 移除指定监听
     *
     * @param runnable
     */
    public void removeCallbacks(Runnable runnable) {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
