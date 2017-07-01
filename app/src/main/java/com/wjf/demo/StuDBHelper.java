package com.wjf.demo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by qifan on 2017/7/1.
 */

public class StuDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "TestSQLite";
    public static final int VERSION = 1;

    public StuDBHelper(Context context) {
        super(context, "test.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "onCreate: ");
        String create_db = "create table stu(id integer primary key autoincrement,name varchar,age int)";
        db.execSQL(create_db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "db = [" + db + "], oldVersion = [" + oldVersion + "], newVersion = [" + newVersion + "]");
        switch (newVersion) {
            case 2:
                String create_db = "create table stu2(id integer primary key autoincrement,name varchar,age int)";
                db.execSQL(create_db);
                break;
            case 3:
                if (!checkColumnExistInTable(db, "stu2", "books")) {
                    String s = "alter table stu2 add books varchar";
                    db.execSQL(s);
                }else {
                    Log.e(TAG, "db = [" + db + "], oldVersion = [" + oldVersion + "], newVersion = [" + newVersion + "]    存在books字段  ");
                }
                break;
        }

    }


    private boolean checkColumnExistInTable(SQLiteDatabase db, String tableName, String columnName) {
        boolean result = false;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", null);
            result = cursor != null && cursor.getColumnIndex(columnName) != -1;
        } catch (Exception e) {
            Log.e("error", "checkColumnExists2..." + e.getMessage());
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }
}
