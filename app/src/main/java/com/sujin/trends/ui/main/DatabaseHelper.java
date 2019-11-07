package com.sujin.trends.ui.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    Context context;
    SQLiteDatabase sqLiteDatabase;

    public DatabaseHelper(@Nullable Context context) {
        super(context, "name.db", null, 1);
        this.context = context;
        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table bookmarks (id integer primary key autoincrement ,author text,name text,avatar text,url text,description text,language text,languageColor text," +
                "stars integer,forks integer,currentPeriodStars integer)");
        sqLiteDatabase.execSQL("create table builtby (authorName text, username text,href text,avatar text)");
        sqLiteDatabase.execSQL("create table userid (id integer primary key autoincrement, uid text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertUserId(String userId)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("uid",userId);
        sqLiteDatabase.insert("userid",null,contentValues);
    }

    public String getUserId()
    {
        String uid="nothing";
        Cursor userid = sqLiteDatabase.rawQuery("select * from userid ", null);
        if(userid.moveToNext())
        {
            uid = userid.getString(1);
        }
        return uid;
    }

    public void insertData(Repository repository) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("author",repository.getAuthor());
        contentValues.put("name",repository.getName());
        contentValues.put("avatar",repository.getAvatar());
        contentValues.put("url",repository.getUrl());
        contentValues.put("description",repository.getDescription());
        contentValues.put("language",repository.getLanguage());
        contentValues.put("languageColor",repository.getLanguageColor());
        contentValues.put("stars",repository.getStars());
        contentValues.put("forks",repository.getForks());
        contentValues.put("currentPeriodStars",repository.getCurrentPeriodStars());
        sqLiteDatabase.insert("bookmarks",null,contentValues);

        for(int i=0; i<repository.getBuiltBy().size();i++)
        {
            ContentValues builtby = new ContentValues();
            builtby.put("authorName",repository.getAuthor()+repository.getName());
            builtby.put("username",repository.getBuiltBy().get(i).getUsername());
            builtby.put("href",repository.getBuiltBy().get(i).getHref());
            builtby.put("avatar",repository.getBuiltBy().get(i).getAvatar());
            sqLiteDatabase.insert("builtby",null,builtby);

        }
        Toast.makeText(context, "Bookmark added", Toast.LENGTH_SHORT).show();
    }

    public Cursor getBookmarks() {
        Cursor bookmarks = sqLiteDatabase.rawQuery("select * from bookmarks ", null);
        return bookmarks;
    }

    public Cursor getBuiltBy(String authorName)
    {
        Cursor contributors = sqLiteDatabase.rawQuery("select * from builtby where authorName ='"+authorName+"'",null);
        return contributors;
    }

    public void deleteData(String author, String name)
    {
        sqLiteDatabase.execSQL("delete from bookmarks where author ='"+author+"' and "+"name='"+name+"'");
        sqLiteDatabase.execSQL("delete from builtby where authorName='"+author+name+"'");
        Toast.makeText(context, "Bookmark removed", Toast.LENGTH_SHORT).show();
    }

}