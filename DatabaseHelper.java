package com.example.diaboloacademia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
     private static final String DB_NAME = "diabolo.db";
     private static final int BD_VERSION=1;

     private static final String TABLE_USERS= "users";

     public DatabaseHelper(Context context){
         super(context, DB_NAME, null, BD_VERSION);
     }

     @Override
     public void onCreate(SQLiteDatabase db){
         String createUsers= "CREATE TABLE " + TABLE_USERS + "(" +
                 "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                 "username TEXT UNIQUE," +
                 "affection INTEGER DEFAULT 0," +
                 "lastNode TEXT DEFAULT 'start'," +
                 "created_at INTEGER" +
                 ")";
         db.execSQL(createUsers);
     }

     //tells android what to do if i update my app and change the database structure
     @Override
     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This method is called when you change BD_VERSION.
        // For now, we just drop the table and recreate it.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
     }

     // CREATE
     public void createUser(String username){
         SQLiteDatabase db = getWritableDatabase();
         ContentValues cv = new ContentValues();

         cv.put("username", username);
         cv.put("created_at", System.currentTimeMillis());
         db.insert(TABLE_USERS, null, cv);
         db.close();
     }

     //READ
     public User getUserByName(String username){
         SQLiteDatabase db= getReadableDatabase();
         Cursor cursor = db.query(TABLE_USERS, null, "username=?", new String[]{username},null, null, null);

         if (cursor != null && cursor.moveToFirst()){

             User user = new User(
                     cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                     cursor.getString(cursor.getColumnIndexOrThrow("username")),
                     cursor.getInt(cursor.getColumnIndexOrThrow("affection")),
                     cursor.getString(cursor.getColumnIndexOrThrow("lastNode"))
             );
             cursor.close();
             db.close();
             return user;
         }
         return null ;
     }

    //UPDATE
     public void updateUser(User user){
         SQLiteDatabase db = getWritableDatabase();
         ContentValues cv = new ContentValues();

         cv.put("affection", user.getAffection());
         cv.put("lastNode", user.getLastNode());

         db.update(TABLE_USERS, cv, "id=?", new String[]{String.valueOf(user.getId())});
         db.close();
    }

    //DELETE
     public void deleteUser(int id){
         SQLiteDatabase db= getWritableDatabase();
         db.delete(TABLE_USERS, "id=?", new String[]{String.valueOf(id)});
         db.close() ;
    }
}
