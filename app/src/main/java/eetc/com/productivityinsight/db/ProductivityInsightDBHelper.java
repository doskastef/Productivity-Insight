package eetc.com.productivityinsight.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import eetc.com.productivityinsight.db.ProductivityInsightContract.ProductivityInsightEntry;

public class ProductivityInsightDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "productivity_insight.db";

    //create table if not exists mytable (col1 type, col2 type);
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + ProductivityInsightEntry.TABLE_NAME +
                    " (" +
                    ProductivityInsightEntry._ID + " INTEGER PRIMARY KEY," +
                    ProductivityInsightEntry.COLUMN_USERNAME + " TEXT," +
                    ProductivityInsightEntry.COLUMN_PASSWORD + " INTEGER," +
                    ProductivityInsightEntry.COLUMN_USER_ID +  " INTEGER" +
                    ")";

    private static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + ProductivityInsightEntry.TABLE_NAME;

    public ProductivityInsightDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addUserToDB(User user) {
        ContentValues values = new ContentValues();
        values.put(ProductivityInsightEntry.COLUMN_USERNAME, user.getUsername());
        values.put(ProductivityInsightEntry.COLUMN_PASSWORD, user.getPassword());
        values.put(ProductivityInsightEntry.COLUMN_USER_ID, user.getUserID());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(ProductivityInsightEntry.TABLE_NAME, null, values);
        db.close();
        List<User> users = readFromDB();
        for (User u: users) {
            Log.i("USERdb", u.getUsername());
            Log.i("USERdb", u.getPassword());
            Log.i("USERdb", Integer.toString(u.getUserID()));
        }
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + ProductivityInsightEntry.TABLE_NAME + " WHERE 1");
        db.close();
    }

    public List<User> readFromDB() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                ProductivityInsightEntry._ID,
                ProductivityInsightEntry.COLUMN_USERNAME,
                ProductivityInsightEntry.COLUMN_PASSWORD,
                ProductivityInsightEntry.COLUMN_USER_ID
        };
        String selection = ProductivityInsightEntry._ID + " >= ?";
        String[] selectionArgs = { "0" };
        String sortOrder = ProductivityInsightEntry._ID + " ASC";

        Cursor cursor = db.query(
                ProductivityInsightEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        List<User> users = new ArrayList<>();
        if (cursor != null && cursor.getCount() < 1) {
            Log.i("DB", "Database is empty!");
        } else {
            Log.i("DB", "Printing _ID for entries in DB");
            while (cursor.moveToNext()) {
                User user = new User();
                Log.i("DB", "_ID: " + Integer.toString(cursor.getColumnIndexOrThrow(ProductivityInsightEntry._ID)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(ProductivityInsightEntry.COLUMN_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(ProductivityInsightEntry.COLUMN_PASSWORD)));
                user.setUserID(cursor.getInt(cursor.getColumnIndex(ProductivityInsightEntry.COLUMN_USER_ID)));
                users.add(user);
                Log.i("DB", user.toString());
            }
        }
        cursor.close();
        db.close();
        return users;
    }

    public boolean isDBEmpty() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                ProductivityInsightEntry._ID
        };
        String selection = ProductivityInsightEntry._ID + " >= ?";
        String[] selectionArgs = { "0" };
        String sortOrder = ProductivityInsightEntry._ID + " ASC";

        Cursor cursor = db.query(
                ProductivityInsightEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        if (cursor.getCount() < 1) {
            return true;
        }
        cursor.close();
        return false;
    }
}
