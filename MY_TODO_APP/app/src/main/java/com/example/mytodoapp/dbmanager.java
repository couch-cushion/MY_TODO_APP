package com.example.mytodoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbmanager extends SQLiteOpenHelper
{
    private static final String dbname="dbcontact";

    public dbmanager(@Nullable Context context)
    {
        super(context, dbname, null, 1);
    }




    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String qry="create table tbl_contact ( id integer primary key autoincrement, title text, detail text)";
        sqLiteDatabase.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        String qry="DROP TABLE IF EXISTS tbl_contact";
        sqLiteDatabase.execSQL(qry);
        onCreate(sqLiteDatabase);
    }

    public  String addrecord(String title, String detail)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put("title",title);
        cv.put("detail",detail);

        float res=db.insert("tbl_contact",null,cv);

        if(res==-1)
            return "Failed";
        else
            return  "Successfully inserted";

    }




    public Cursor readalldata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String qry="select * from tbl_contact order by id desc";
        Cursor cursor=db.rawQuery(qry,null);
        return  cursor;
    }
}
