package com.example.todolistapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.*;

import com.example.todolistapp.models.TodoItem;

import java.util.ArrayList;
import java.util.List;


public class TodoDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "TodoDatabase";
    private static final String TABLE_NAME = "todo";
    private static final String ID = "id";
    private static final String NAME = "title";
    private static final String DESC = "des";
    private static final String USER_ID = "user_id";
    private static final String isDone = "isdone";
    private SQLiteDatabase db1;


//        todoHandler.addItem("title" , "Description" , 2  , 0); this 2 is the user_id and last column is is_done (0 for false , 1 for true)

//        todoHandler.updateItem(new TodoItem(idOfItemYouWantToUpdate , "NewTitle" , "NewDescription" , NewUserId ,isDone ));

//        todoHandler.deleteItem(2); -> delete item with id

//        todoHandler.getAllItemsOfUser(1) -> return list of TodoItems of specific User


    public TodoDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = ("create Table todo(id integer primary key autoincrement, title TEXT , des TEXT , user_id NUMBER , isdone BIT)");
        db.execSQL(CREATE_TODO_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void addItem(String title, String desc, int user_id, int is_done) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, title);
        values.put(DESC, desc);
        values.put(USER_ID, user_id);
        values.put(isDone, is_done);

        db.insert(TABLE_NAME, null, values);
        getWritableDatabase().close();
        db.close();
    }

    public TodoItem getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from todo where id = ?", new String[]{String.valueOf(id)});
        if (cursor != null) {
            cursor.moveToFirst();

            if (cursor.isAfterLast()) {
                getReadableDatabase().close();
                return new TodoItem(99, "There's no item with this id", "", 99, 0);
            }


            TodoItem item = new TodoItem(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)));

            getReadableDatabase().close();
            return item;
        } else {
            getReadableDatabase().close();
            return new TodoItem(99, "There's no item with this id", "", 99, 0);
        }

    }

    public List<TodoItem> getAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<TodoItem> todoList = new ArrayList<TodoItem>();

        Cursor  cursor = db.rawQuery("select * from todo",null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                TodoItem todoItem = new TodoItem(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)),
                        Integer.parseInt(cursor.getString(4))
                );

                todoList.add(todoItem);
                cursor.moveToNext();
            }
        }
        getReadableDatabase().close();
        return todoList;

    }

    public ArrayList<TodoItem> getAllItemsOfUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TodoItem> todoList = new ArrayList<TodoItem>();

        Cursor  cursor = db.rawQuery("select * from todo where user_id = ?",new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                TodoItem todoItem = new TodoItem(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)),
                        Integer.parseInt(cursor.getString(4))
                );

                todoList.add(todoItem);
                cursor.moveToNext();
            }
        }
        getReadableDatabase().close();
        return todoList;
    }

    public int updateItem(int id , String title , String desc , int isdone) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME,title);
        values.put(DESC, desc);
        values.put(isDone, isdone);

        // updating row
        return db.update(TABLE_NAME, values, ID + " = ?", new String[] { String.valueOf(id) });
    }

    // Deleting single contact
    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }


}