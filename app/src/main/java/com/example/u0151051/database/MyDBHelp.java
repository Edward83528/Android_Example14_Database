package com.example.u0151051.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by u0151051 on 2017/4/6.
 */
//->實作SQLiteOpenHelper抽象類別的兩個抽象方法(onCreate/onUpgrade)和建立建構子(因為SQLiteOpenHelper有有參數的建構子,卻沒有無參數的建構子)
public class MyDBHelp extends SQLiteOpenHelper {
    //命名資料庫名稱和資料庫版本
    private static final String DATABASE_NAME = "todo";
    private static final int DATABASE_VERSION = 1;

    public MyDBHelp(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //建立資料表格的時機(onCreate)
    @Override
    //在onCreate方法中，取得可寫入SQLiteDatabase物件後，呼叫execSQL方法建立exp表格
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE todos (" +
                " _id INTEGER PRIMARY KEY," +
                " name TEXT," +
                " score REAL)";
        db.execSQL(sql);
    }

    //當使用者手機中已安裝較舊版本應用程式時，在存取資料庫指令時自動檢查到舊的資料庫檔案時，會自動執行本方法
    //onUpgrade方法內可撰寫程式碼以協助更新使用者舊資料，以順利移轉至新版的資料表格
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }
}
