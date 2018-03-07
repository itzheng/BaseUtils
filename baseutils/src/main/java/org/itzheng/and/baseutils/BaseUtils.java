package org.itzheng.and.baseutils;

import android.content.Context;

import org.itzheng.and.baseutils.ui.SystemUi;
import org.itzheng.and.baseutils.ui.UIUtils;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-3-7.
 */
public class BaseUtils {
    public static void init(Context context) {
        UIUtils.init(context);
        SystemUi.init(context);
    }
}
