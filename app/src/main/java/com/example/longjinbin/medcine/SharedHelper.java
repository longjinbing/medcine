package com.example.longjinbin.medcine;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by longjinbin on 2018/5/26.
 */

public class SharedHelper {

    private Context mContext;

    public SharedHelper() {
    }

    public SharedHelper(Context mContext) {
        this.mContext = mContext;
    }


    //定义一个保存数据的方法
    public void saveuserinfo(String id,String username, String passwd,String tizhi,String cookie) {
        SharedPreferences sp =  mContext.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("id", id);
        editor.putString("username", username);
        editor.putString("password", passwd);
        editor.putString("tizhi", tizhi);
        editor.putString("cookie", cookie);
        editor.commit();
    }

    //定义一个读取SP文件的方法
    public Map<String, String> readuserinfo() {
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = mContext.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        data.put("id", sp.getString("id", ""));
        data.put("username", sp.getString("username", ""));
        data.put("password", sp.getString("password", ""));
        data.put("tizhi", sp.getString("tizhi", ""));
        data.put("cookie", sp.getString("cookie", ""));
        return data;
    }
}
