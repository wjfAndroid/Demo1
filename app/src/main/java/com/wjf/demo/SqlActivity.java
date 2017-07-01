package com.wjf.demo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

/*

http://blog.csdn.net/codeeer/article/details/30237597/
1.使用SQLiteOpenHelper 来创建数据库辅助类
onCreate用于创建数据库

onupdate用于更新数据库版本使得操作。
通过switch来进行操作。

在activity中通过使用SQLiteOpenHelper的getWritableDatabase和getReadableDatabase来进行增删改差的操作。
都可以直接是有那个sql语句进行操作

记得关闭close

 */
public class SqlActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);

    }


    public void onCreate(View view) {
        StuDBHelper helper = new StuDBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
    }

    public void onupdateDatabase(View view) {

    }


    public void oninsert(View view) {
        StuDBHelper helper = new StuDBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String insert = "insert into stu(name,age) values('wjf',12)";
        db.execSQL(insert);
        String insert2 = "insert into stu(name,age) values('wjf2',123)";
        db.execSQL(insert2);
        String insert3 = "insert into stu(name,age) values('wjf3',1234)";
        db.execSQL(insert3);
        db.close();
    }

    public void onupdate(View view) {
        StuDBHelper helper = new StuDBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String update = "update stu set name='ccccc' where id=1";
        db.execSQL(update);
        db.close();
    }

    public void onsearch(View view) {
        StuDBHelper helper = new StuDBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("stu", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            System.out.println("id = " + id + "    name = " + name + "    age = " + age);
        }
        cursor.close();
        db.close();
    }

    public void ondelete(View view) {
        StuDBHelper helper = new StuDBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String delete = "delete from stu where id=1";
        db.execSQL(delete);
        db.close();
    }

    public void ondeletedatabase(View view) {
        StuDBHelper helper = new StuDBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String delete = "drop table stu";
        db.execSQL(delete);
        db.close();
    }

    public void oninsert2(View view) {
        StuDBHelper helper = new StuDBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String insert = "insert into  stu2(name,age) values('kkk',22)";
        db.execSQL(insert);
        db.close();
    }

    public void onsearch2(View view) {
        StuDBHelper helper = new StuDBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        // Cursor cursor = db.query("stu2", null, null, null, null, null, null, null);
        String s = "select * from stu2 where id>2";
        Cursor cursor = db.rawQuery(s, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            System.out.println("id = " + id + "    name = " + name + "    age = " + age);
        }
        cursor.close();
        db.close();
    }

    public void oninsert3(View view) {
        StuDBHelper helper = new StuDBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String insert = "insert into  stu2(name,age,books) values('kkk',22,'gggggg')";
        db.execSQL(insert);
        db.close();
    }

    public void onsearch3(View view) {
        StuDBHelper helper = new StuDBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        // Cursor cursor = db.query("stu2", null, null, null, null, null, null, null);
        String s = "select * from stu2 where id>2";
        Cursor cursor = db.rawQuery(s, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            String books = cursor.getString(cursor.getColumnIndex("books"));
            System.out.println("id = " + id + "    name = " + name + "    age = " + age + "    books = " + books);
        }
        cursor.close();
        db.close();
    }


}
