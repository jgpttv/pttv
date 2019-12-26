package com.cch.gsyvideoplayer.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Window;

import com.cch.gsyvideoplayer.R;
import com.cch.gsyvideoplayer.view.FSLVCircularSmile;


/**
 * Created by Fstar on 2017/5/26.
 */
public class DialogUtil {
    /**
     * 创建等待对话框
     *
     * @param context Activity 调用者
     * @return 对话框
     */
    public static Dialog createWaitingDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_waiting);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(
                android.graphics.Color.TRANSPARENT));
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Dialog d = (Dialog) dialog;
                FSLVCircularSmile lVCircularSmile = (FSLVCircularSmile) d.findViewById(R.id.lv_circularSmile);
                lVCircularSmile.startAnim();
            }
        });
        return dialog;
    }
}
