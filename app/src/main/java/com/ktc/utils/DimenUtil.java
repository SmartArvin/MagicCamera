package com.ktc.utils;

import com.ktc.MagicCameraApplication;

/**
 * 尺寸转换
 * @author Arvin
 * @date 2020.01.08
 */
public class DimenUtil {

    public static int dp2Px(int dpValue) {
        final float scale = MagicCameraApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int dp2Px(float dpValue) {
        final float scale = MagicCameraApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2Dp(float pxValue) {
        final float scale = MagicCameraApplication.getContext().getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale);
    }

}
