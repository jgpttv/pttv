package com.cch.gsyvideoplayer.http;

import android.content.Context;


import com.cch.gsyvideoplayer.util.JsonUtil;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class ListCallBack<T> extends BaseCallBack {


    public ListCallBack(Context context) {
        super(context);
    }

    //Java中泛型得到T.class
    private Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }
    @Override
    public void onResponse(String json) {
        List<T> ts = JsonUtil.toList(json, getTClass());
        onResponse(ts);
    }

    public abstract void onResponse(List<T> list);

}
