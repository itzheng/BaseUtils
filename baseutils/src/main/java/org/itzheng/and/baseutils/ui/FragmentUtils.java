package org.itzheng.and.baseutils.ui;

import android.content.Intent;
import android.support.annotation.IdRes;

import java.util.List;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-1-11.
 */
public class FragmentUtils {
    /**
     * 将Fragment添加到view上面
     *
     * @param ft
     * @param containerViewId
     * @param fragments
     */
    public static <T extends android.app.Fragment> void addFragmentsToView(android.app.FragmentTransaction ft, @IdRes int containerViewId, List<T> fragments) {
        for (android.app.Fragment fragment : fragments) {
            ft.add(containerViewId, fragment);
        }
        ft.commit();
    }

    /**
     * 将Fragment添加到view上面
     *
     * @param ft
     * @param containerViewId
     * @param fragments
     */
    public static <T extends android.support.v4.app.Fragment> void addFragmentsToView(android.support.v4.app.FragmentTransaction ft, @IdRes int containerViewId, List<T> fragments) {
        for (android.support.v4.app.Fragment fragment : fragments) {
            ft.add(containerViewId, fragment);
        }
        ft.commit();
    }

    /**
     * 显示某个Fragment，并且隐藏其他Fragment
     *
     * @param ft
     * @param fragments
     * @param position
     * @param <T>
     */
    public static <T extends android.app.Fragment> void showFragment(android.app.FragmentTransaction ft, List<T> fragments, int position) {
        int size = fragments == null ? 0 : fragments.size();
        for (int i = 0; i < size; i++) {
            if (position == i) {
                ft.show(fragments.get(i));
            } else {
                ft.hide(fragments.get(i));
            }
        }
        ft.commit();
    }

    /**
     * 显示某个Fragment，并且隐藏其他Fragment
     *
     * @param ft
     * @param fragments
     * @param position
     * @param <T>
     */
    public static <T extends android.support.v4.app.Fragment> void showFragment(android.support.v4.app.FragmentTransaction ft, List<T> fragments, int position) {
        int size = fragments == null ? 0 : fragments.size();
        for (int i = 0; i < size; i++) {
            if (position == i) {
                ft.show(fragments.get(i));
            } else {
                ft.hide(fragments.get(i));
            }
        }
        ft.commit();
    }

    /**
     * 设置 onActivityResult
     *
     * @param fragments
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void onActivityResult(List fragments, int requestCode, int resultCode, Intent data) {
        if (fragments != null) {
            for (Object object : fragments) {
                if (object instanceof android.app.Fragment) {
                    ((android.app.Fragment) object).onActivityResult(requestCode, resultCode, data);
                } else if (object instanceof android.support.v4.app.Fragment) {
                    ((android.support.v4.app.Fragment) object).onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    /**
     * 移除Fragment，避免造成数据缓存
     *
     * @param ft
     * @param fragments
     */
    public static <T extends android.app.Fragment> void remove(android.app.FragmentTransaction ft, List<T> fragments) {
        if (ft == null || fragments == null) {
            return;
        }
        for (T fragment : fragments) {
            ft.remove(fragment);
        }
        ft.commit();
    }

    /**
     * 移除Fragment，避免造成数据缓存
     *
     * @param ft
     * @param fragments
     */
    public static <T extends android.support.v4.app.Fragment> void remove(android.support.v4.app.FragmentTransaction ft, List<T> fragments) {
        if (ft == null || fragments == null) {
            return;
        }
        for (T fragment : fragments) {
            ft.remove(fragment);
        }
        ft.commit();
    }

    /**
     * 替换Fragment到View并显示
     *
     * @param fragmentTransaction
     * @param containerViewId
     * @param fragment
     */
    public static void replaceFragmentToView(android.app.FragmentTransaction fragmentTransaction, int containerViewId, android.app.Fragment fragment) {
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 替换Fragment到View并显示
     *
     * @param fragmentTransaction
     * @param containerViewId
     * @param fragment
     */
    public static void replaceFragmentToView(android.support.v4.app.FragmentTransaction fragmentTransaction, int containerViewId, android.support.v4.app.Fragment fragment) {
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 添加Fragment到View并显示
     *
     * @param fragmentTransaction
     * @param containerViewId
     * @param fragment
     */
    public static void addFragmentToView(android.app.FragmentTransaction fragmentTransaction, int containerViewId, android.app.Fragment fragment) {
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 添加Fragment到View并显示
     *
     * @param fragmentTransaction
     * @param containerViewId
     * @param fragment
     */
    public static void addFragmentToView(android.support.v4.app.FragmentTransaction fragmentTransaction, int containerViewId, android.support.v4.app.Fragment fragment) {
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 移除Fragment
     *
     * @param fragmentTransaction
     * @param fragment
     */
    public static void removeFragment(android.app.FragmentTransaction fragmentTransaction, android.app.Fragment fragment) {
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    /**
     * 移除Fragment
     *
     * @param fragmentTransaction
     * @param fragment
     */
    public static void removeFragment(android.support.v4.app.FragmentTransaction fragmentTransaction, android.support.v4.app.Fragment fragment) {
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }
}
