package com.ktc.utils;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ktc.MagicCameraApplication;
import com.ktc.camera.R;

/**
 * 自定义Toast
 * @author Arvin
 * @date 2020.01.08
 */
public class ToastUtil {

    private static Toast mToast = null;

    /**
     * 在子线程：仅文本
     */
    public static void showToastOnThread(String text) {
        showToastOnThread(null , text);
    }

    /**
     * 在子线程：图文
     */
    public static void showToastOnThread(Drawable iconDrawable, String text) {
        new Handler().post(() -> showToast(text));
    }

    /**
     * 只显示文本
     */
    public static void showToast(String text) {
        showToast(null , text);
    }

    /**
     * 图文同时显示
     */
    public static void showToast(Drawable iconDrawable, String text) {
        LayoutInflater inflater = LayoutInflater.from(MagicCameraApplication.getContext());
        View layout = inflater.inflate(R.layout.layout_toast, null);

        ImageView img =  layout.findViewById(R.id.icon);
        if(iconDrawable != null){
            img.setImageDrawable(iconDrawable);
            img.setVisibility(View.VISIBLE);
        }else{
            img.setVisibility(View.GONE);
        }

        TextView title = layout.findViewById(R.id.title);
        if(text != null && !TextUtils.isEmpty(text)){
            title.setText(text);
            title.setVisibility(View.VISIBLE);
        }else{
            title.setVisibility(View.GONE);
        }

        mToast = new Toast(MagicCameraApplication.getContext());
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(layout);
        mToast.show();
    }

}
