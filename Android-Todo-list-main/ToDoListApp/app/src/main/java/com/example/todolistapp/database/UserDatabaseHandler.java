package com.example.todolistapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todolistapp.models.User;


public class UserDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final String TABLE_NAME = "user";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PASSWORD = "password";

    public UserDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = ("create Table user(id integer primary key autoincrement, name TEXT , password TEXT)");

        db.execSQL(CREATE_USERS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public Boolean addUser(String name , String password) {

        if (checkUsername(name)){
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(PASSWORD, password);

        db.insert(TABLE_NAME, null, values);
        db.close();
        return true;
    }

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from user where id = ?", new String[] {String.valueOf(id)});

        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            User user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
            return user;
        }else{
            return new User(99 , "Wrong User Id" , "");
        }

    }

    public Boolean checkUsername(String name) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from user where name = ?", new String[]{name});
        return cursor.getCount() > 0;
    }

    public User checkUserNamePassword(String name, String password){

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from user where name = ? and password = ?", new String[] {name,password});


        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            User user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
            return user;
        }else{
            return new User(-1 , "Wrong User and Password" , "");
        }
    }
}