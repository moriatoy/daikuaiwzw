package com.fuzhuangfaqianla.xinhuahua.manager;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.fuzhuangfaqianla.xinhuahua.R;

/**
 * Created by xiongchang on 2017/8/26.
 */

public class LoadingManager {

    public static Dialog showLoadingViewDialog(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.inflate_loading_view_layout,null);

        final Dialog dialog = new Dialog(context,R.style.Theme_Light_NoBG_Dialog);
        dialog.setContentView(dialogView);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        return dialog;
    }
}
