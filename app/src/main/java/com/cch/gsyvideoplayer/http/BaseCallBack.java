package com.cch.gsyvideoplayer.http;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cch.gsyvideoplayer.util.DialogUtil;
import com.cch.gsyvideoplayer.util.JsonUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

public abstract class BaseCallBack extends StringCallback {
    private final Context context;
    private Dialog mWaitingDialog;
    // private ProgressDialog progressDialog;

    public BaseCallBack(Context context) {
        this.context=context;
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        showWaitingDialog();
    }


    public Dialog showWaitingDialog() {
        if (mWaitingDialog == null) {
            mWaitingDialog = DialogUtil.createWaitingDialog(context);
        }

        if (mWaitingDialog.getContext() != null && !mWaitingDialog.isShowing()) {
            mWaitingDialog.show();
        }
        return mWaitingDialog;
    }

    public void dismissWaitingDialog() {
        if (mWaitingDialog != null && mWaitingDialog.isShowing()) {
            try {
                mWaitingDialog.dismiss();
            } catch (Exception e) {
                //防止某些手机的异常状态，使得对话框关闭抛异常
                Log.w("BASE ACTIVITY", "Dismiss dialog error", e);
            }
        }
    }


    @Override
    public void onAfter(int id) {
        super.onAfter(id);
       dismissWaitingDialog();
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        Log.d("http","Exception  "+e.getMessage());
        onError(new MyError(1001,e.getMessage()));
        Toast.makeText(context,"网络连接失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response, int id) {
        Log.d("http","response  "+response);
        MyResponse myResponse = JsonUtil.toObj(response, MyResponse.class);
        if(myResponse.getStatus()==1){
            Object data = myResponse.getData();
            String json = JsonUtil.toJson(data);
            onResponse(json);
        }else {
            //Toast.makeText(context,myResponse.getMsg(),Toast.LENGTH_SHORT).show();
            onError(new MyError(myResponse.getStatus(),myResponse.getMsg()));
        }
    }


    public abstract void onError(MyError e);
    public abstract void onResponse(String json);




    public class MyResponse{
        private int status;
        private String msg;
        private Object data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
    public class MyError{
        int code;
        String msg;

        public MyError(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
