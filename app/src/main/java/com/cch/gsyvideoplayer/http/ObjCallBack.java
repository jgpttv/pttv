package com.cch.gsyvideoplayer.http;

import android.content.Context;


import com.cch.gsyvideoplayer.util.JsonUtil;

import java.lang.reflect.ParameterizedType;

public abstract class ObjCallBack<T> extends BaseCallBack {


    public ObjCallBack(Context context) {
        super(context);
    }

    //Java中泛型得到T.class
    private Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }
    @Override
    public void onResponse(String json) {
        T t = JsonUtil.toObj(json, getTClass());
        onResponse(t);
    }

    public abstract void onResponse(T obj);

}
