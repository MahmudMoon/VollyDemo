package com.example.moon.vollydemo;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

public class SingleToneClass {
    Context mContext;
    RequestQueue requestQueue;
    public static SingleToneClass singleToneClass;

    public SingleToneClass(Context mContext) {
        this.mContext = mContext;
        requestQueue = getRequestQuery();
    }

    public RequestQueue getRequestQuery() {
        if(requestQueue==null){

            Network network = new BasicNetwork(new HurlStack());
            Cache cache = new DiskBasedCache(mContext.getCacheDir(),1024*1024);
            requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.getCache().clear();
        }
        return requestQueue;
    }


    public static synchronized SingleToneClass getInstance(Context context){

        // this is a temporary solution
        // i need to handle the cache
        if(singleToneClass!=null){
            singleToneClass = null;
        }

        if(singleToneClass==null) {
            singleToneClass = new SingleToneClass(context);
        }else{
            Log.i("TAG","Class has been initialized");
        }
        return singleToneClass;
    }

    public<T> void addToRequest(Request<T> request){
        requestQueue.add(request);
    }
}
