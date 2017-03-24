//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szcomtop.meal.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Tools {

    public Tools() {
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5F);
    }

    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / fontScale + 0.5F);
    }

    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5F);
    }

    public static int[] getScreen(Context con) {
        int[] screen = new int[2];
        WindowManager manager = (WindowManager)con.getSystemService("window");
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        screen[0] = dm.widthPixels;
        screen[1] = dm.heightPixels;
        return screen;
    }

    public static int getStatusBar() {
        int barHeight = 40;
        int heightId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if(heightId != 0) {
            barHeight = Resources.getSystem().getDimensionPixelSize(heightId);
        }

        return barHeight;
    }

    public static String toMD5(String sourceStr) {
        String result = "";
        if(TextUtils.isEmpty(sourceStr)) {
            return result;
        } else {
            try {
                MessageDigest e = MessageDigest.getInstance("MD5");
                e.update(sourceStr.getBytes());
                byte[] b = e.digest();
                StringBuffer buf = new StringBuffer("");

                for(int offset = 0; offset < b.length; ++offset) {
                    int i = b[offset];
                    if(i < 0) {
                        i += 256;
                    }

                    if(i < 16) {
                        buf.append("0");
                    }

                    buf.append(Integer.toHexString(i));
                }

                result = buf.toString();
            } catch (NoSuchAlgorithmException var7) {
                var7.printStackTrace();
            }

            return result;
        }
    }

    public static String findMIME(String name) {
        return TextUtils.isEmpty(name)?null:(name.endsWith(".js")?"application/x-javascript":(name.endsWith(".ttf")?"application/octet-stream":(name.endsWith(".png")?"image/png":(name.endsWith(".jpg")?"image/jpeg":(name.endsWith(".css")?"text/css":null)))));
    }

    public static int getSDKVision() {
        return VERSION.SDK_INT;
    }

    public static ColorStateList createColorStateList(int normal, int pressed) {
        return createColorStateList(normal, pressed, pressed, pressed);
    }

    public static StateListDrawable createStateListDrawable(Drawable normal, Drawable pressed) {
        return createStateListDrawable(normal, pressed, (Drawable)null, (Drawable)null);
    }

    public static StateListDrawable createStateListDrawable(Drawable normal, Drawable pressed, Drawable focused, Drawable unable) {
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[]{16842910, 16842908}, focused);
        sd.addState(new int[]{16842919, 16842910}, pressed);
        sd.addState(new int[]{16842908}, focused);
        sd.addState(new int[]{16842919}, pressed);
        sd.addState(new int[]{16842910}, normal);
        sd.addState(new int[0], normal);
        return sd;
    }

    public static ColorStateList createColorStateList(int normal, int pressed, int focused, int unable) {
        int[] colors = new int[]{pressed, focused, normal, focused, unable, normal};
        int[][] states = new int[][]{{16842919, 16842910}, {16842910, 16842908}, {16842910}, {16842908}, {16842909}, new int[0]};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }



    @SuppressLint({"NewApi"})
    public static void translucentStatus(Activity act, int color) {
        Window window = act.getWindow();
        if(getSDKVision() >= 21) {
            window.clearFlags(201326592);
            window.getDecorView().setSystemUiVisibility(1792);
            window.addFlags(-2147483648);
            window.setStatusBarColor(0);
            window.setNavigationBarColor(0);
            setStatusBarPadding(act, color);
        } else if(getSDKVision() >= 19) {
            act.getWindow().addFlags(67108864);
            setStatusBarPadding(act, color);
        }

    }

    private static void setStatusBarPadding(Activity act, int color) {
        View mainView = act.getWindow().getDecorView();
        if(color != -1) {
            mainView.setBackgroundColor(color);
            mainView.setPadding(0, getStatusBar(), 0, 0);
        } else {
            mainView.setPadding(0, 0, 0, 0);
        }

    }

    public static void translucentStatus(Activity act) {
        translucentStatus(act, -1);
    }

    public static void setViewEnabled(View view, boolean enabled) {
        if(view instanceof ViewGroup) {
            setGroupEnabled((ViewGroup)view, enabled);
        } else {
            view.setEnabled(enabled);
        }

    }

    private static void setGroupEnabled(ViewGroup group, boolean enabled) {
        group.setEnabled(enabled);
        if(group.getChildCount() >= 1) {
            for(int i = 0; i < group.getChildCount(); ++i) {
                View child = group.getChildAt(i);
                setViewEnabled(child, enabled);
            }

        }
    }
}
