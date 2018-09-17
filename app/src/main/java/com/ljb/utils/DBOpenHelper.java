package com.ljb.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.util.Log.e;

/**
 * Created by longjinbin on 2018/7/23.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {super(context, "my.db", null, 2); }
    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {
        Log.e("data","创建数据库");
        db.execSQL("CREATE TABLE user(id VARCHAR(100),username VARCHAR(20),password VARCHAR(20),Status INTEGER,logintime VARCHAR(200),headpic BLOB)");
        db.execSQL("CREATE TABLE article(id Varchar(100),title VARCHAR(200),type Integer,source VARCHAR(2048),url VARCHAR(2048),comment INTEGER,publishtime VARCHAR(200),imgurls VARCHAR(1024))");
        db.execSQL("CREATE TABLE tizhi(id Integer primary key AUTOINCREMENT,tizhi VARCHAR(200),tizhiid VARCHAR(200),testtime VARCHAR(200),userid VARCHAR(100))");
    }
    //软件版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.delete("article", "id != ?", new String[]{""});
        Log.e("data","更新数据库");
    }
}
