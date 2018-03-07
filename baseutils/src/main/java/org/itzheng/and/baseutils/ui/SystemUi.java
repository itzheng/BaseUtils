package org.itzheng.and.baseutils.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import org.itzheng.and.baseutils.ui.UIUtils;

/**
 * 一些关于系统界面的跳转，如拨号，设置，应用商店等等
 * 参考：http://blog.csdn.net/shenggaofei/article/details/51906611
 * Created by admin on 2017/7/28.
 */
public class SystemUi {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 启动一个intent，主要是方便内部调用
     *
     * @param intent
     */
    private static void startActivity(Intent intent) {
        mContext.startActivity(intent);
    }

    /**
     * 跳转到系统设置界面
     */
    public static void startSetting() {
        startSettingActivity(Settings.ACTION_SETTINGS);
    }

    /**
     * 跳转到设置系统界面，如设置界面
     *
     * @param action Settings.ACTION_SETTINGS
     */
    public static void startSettingActivity(String action) {
        Intent intent = new Intent(action);
        mContext.startActivity(intent); // 打开系统设置界面
    }

    /**
     * 拨打电话
     *
     * @param phone  电话号码
     * @param isCall 是否直接拨打，true 直接拨打，false 到拨号界面
     */
    public static void callPhone(String phone, boolean isCall) {
        if (isCall) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(intent);
        } else {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    /**
     * 发短信
     *
     * @param phone
     * @param msg
     */
    public static void sendMsg(String phone, String msg) {
        Uri uri = Uri.parse("smsto:" + phone);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.putExtra("sms_body", msg);
        startActivity(i);
    }

    /**
     * 选择联系人
     *
     * @param activity
     */
    public static void getContent(Activity activity) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("vnd.android.cursor.item/phone");
        activity.startActivityForResult(i, 1);
    }

//    /**
//     * 安装apk，直接传入文件，针对7.0以下系统，7.0以上需要在清单文件中注册provider
//     *
//     * @param file
//     */
//    private static void installApk(File file) {
//        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
//            Uri apkUri = FileProvider.getUriForFile(mContext, "com.a520wcf.chapter11.fileprovider", file);
//            installApk(apkUri);
//        } else {
//            installApk(Uri.fromFile(file));
//        }
//    }

    /**
     * 安装APK
     *
     * @param apkUri apk文件的uri，如：Uri.fromFile(file)
     */
    public static void installApk(Uri apkUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /**
     * 跳转到app详情界面
     */
    public static void startAppDetailSetting() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());
        }
        startActivity(localIntent);
    }

    /**
     * 通过包名 在应用商店打开应用
     *
     * @param packageName 包名
     */
    public static void openApplicationMarket(String packageName) {
        if (true) {
            launchAppDetail(packageName, null);
//            goToSamsungappsMarket(packageName);
            return;
        }
        String str = "market://details?id=" + packageName;
        Intent localIntent = new Intent(Intent.ACTION_VIEW);
        localIntent.setData(Uri.parse(str));
        startActivity(localIntent);

    }

    /**
     * 三星手机，跳转到应用市场(测试未通过)
     *
     * @param packageName
     */
    public static void goToSamsungappsMarket(String packageName) {
        Uri uri = Uri.parse("http://www.samsungapps.com/appquery/appDetail.as?appId=" + packageName);
        Intent goToMarket = new Intent();
        goToMarket.setClassName("com.sec.android.app.samsungapps", "com.sec.android.app.samsungapps.Main");
        goToMarket.setData(uri);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动到应用商店app详情界面
     *
     * @param appPkg    目标App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static void launchAppDetail(String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg)) return;

            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            UIUtils.showToast("error");
        }
    }

    /**
     * 调用系统浏览器打开网页
     *
     * @param url 地址
     */
    public static void openLinkBySystem(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
