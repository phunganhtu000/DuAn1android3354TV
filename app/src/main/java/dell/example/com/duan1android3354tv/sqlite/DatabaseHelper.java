package dell.example.com.duan1android3354tv.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dell.example.com.duan1android3354tv.Constant;
import dell.example.com.duan1android3354tv.model.Favorite;
import dell.example.com.duan1android3354tv.model.User;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String Name_Database = "user_db";
    private static final int Version_Database = 15;
    private SQLiteDatabase db;
    public DatabaseHelper(Context context) {
        super(context, Name_Database, null, Version_Database);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_USER_TABLE);
        db.execSQL(Favorite.CREATE_TABLE_FAVORITE);
        if (Constant.isDEBUG){
            Log.e("CREATE_TABLE","CREATE_TABLE");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + User.TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + Favorite.TABLE_NAME);
        onCreate(db);

    }


    public void insertUser(User user) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(user.COLUMN_USER_NAME, user.getUser_name());
        values.put(user.COLUMN_PASSWORD, user.getPassword());
        // insert row
        long id=db.insert(User.TABLE_NAME, null, values);
        Log.e("add",""+id);
        // close db connection
        db.close();
        // return newly inserted row id
    }

    public User getUser(String user_name) {
        User user=null;
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(User.TABLE_NAME, new String[]
                {User.COLUMN_USER_NAME, User.COLUMN_PASSWORD}, User.COLUMN_USER_NAME + "=?", new String[]{user_name}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()){
            String username = cursor.getString(cursor.getColumnIndex(User.COLUMN_USER_NAME));

            String password = cursor.getString(cursor.getColumnIndex(User.COLUMN_PASSWORD));


            // khoi tao user voi cac gia tri lay duoc
            user = new User(username, password);
        }
        // close the db connection
        cursor.close();

        return user;
    }
    public List<User> getUserAll() {
        List<User> list= new ArrayList<>();
        String selectQuery = "SELECT * FROM " + User.TABLE_NAME;         //retrieve data from the database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUser_name(cursor.getString(cursor.getColumnIndex(User.COLUMN_USER_NAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(User.COLUMN_PASSWORD)));
                list.add(user);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

//    public int updateUser(String username,User user) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(User.COLUMN_NAME,user.getName());
//        values.put(User.COLUMN_PHONE_NUMBER,user.getPhone_number());
//
//        // updating row
//        return db.update(User.TABLE_NAME, values, User.COLUMN_USER_NAME + " = ?",
//                new String[]{username});
//    }

    //    delete
//    public void deleteUser(String username){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(User.TABLE_NAME, User.COLUMN_USER_NAME+ " = ?",
//                new String[]{username});
//        Log.e("Delete","Delete");
//        db.close();
//
//    }






    public void insertFavorite(Favorite favorite) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(favorite.COLUMN_LINK, favorite.getLink());
        values.put(favorite.COLUMN_NAME, favorite.getName());
        // insert row
        long id=db.insert(Favorite.TABLE_NAME, null, values);
        Log.e("add",""+id);
        // close db connection
        db.close();
        // return newly inserted row id
    }
    public List<Favorite> getFavoriteAll() {
        List<Favorite> list= new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Favorite.TABLE_NAME;         //retrieve data from the database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Favorite favorite = new Favorite();
                favorite.setLink(cursor.getString(cursor.getColumnIndex(Favorite.COLUMN_LINK)));
                favorite.setName(cursor.getString(cursor.getColumnIndex(Favorite.COLUMN_NAME)));
                list.add(favorite);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public void deleteUFavorite(String favorite){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Favorite.TABLE_NAME, Favorite.COLUMN_NAME+ " = ?",
                new String[]{favorite});
        Log.e("Delete","Delete");
        db.close();
    }
}