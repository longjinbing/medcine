package com.ljb.main;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by longjinbin on 2018/7/22.
 */

public class MedcineApplication extends Application {
    //Volley的全局请求队列
    public static RequestQueue sRequestQueue;

    public static boolean isLogin=false;

    /**
     * @return Volley全局请求队列
     */
    public static RequestQueue getRequestQueue() {
        return sRequestQueue;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //实例化Volley全局请求队列
        sRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }
}
