package com.example.task7_1;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "ass9.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_ITEMS = "items";

    // User Table Columns
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_USER_STATUS = "status";
    private static final String KEY_USER_PHONE = "phone";
    private static final String KEY_USER_DESCRIPTION = "description";
    private static final String KEY_USER_DATE = "date";
    private static final String KEY_USER_LOCATION = "location";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_ITEMS +
                "(" +
                KEY_USER_NAME + " TEXT UNIQUE PRIMARY KEY," + // Define a primary key
                KEY_USER_STATUS + " TEXT," + // Define a unique key
                KEY_USER_PHONE + " TEXT," +
                KEY_USER_DESCRIPTION + " TEXT," +
                KEY_USER_DATE + " TEXT," +
                KEY_USER_LOCATION + " TEXT" +
                ")";

        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
            onCreate(db);
        }
    }

    // Insert a user into the database
    public void addItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_USER_NAME, item.getName());
            values.put(KEY_USER_STATUS, item.getStatus());
            values.put(KEY_USER_PHONE, item.getPhone());
            values.put(KEY_USER_DESCRIPTION, item.getDescription());
            values.put(KEY_USER_DATE, item.getDate());
            values.put(KEY_USER_LOCATION, item.getLocation());

            // Insert the user
            db.insertOrThrow(TABLE_ITEMS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    // Update user's playlist
//    public void updateUserInterest(String username, List<String> interest) {
//        SQLiteDatabase db = getWritableDatabase();
//
//        db.beginTransaction();
//        try {
//            ContentValues values = new ContentValues();
//            values.put(KEY_USER_INTEREST, TextUtils.join(",", interest));
//
//            // Updating playlinterestist for user with that username
//            db.update(TABLE_USERS, values, KEY_USER_USERNAME + " = ?", new String[]{username});
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            db.endTransaction();
//        }
//    }

    // Get user by username
    public Item getItemByName(String name) {
        Item item = null;
        String USERS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = ?",
                        TABLE_ITEMS, KEY_USER_NAME);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USERS_SELECT_QUERY, new String[]{String.valueOf(name)});
        try {
            if (cursor.moveToFirst()) {
                Log.d("database", "Here???");
                @SuppressLint("Range") String itemName = cursor.getString(cursor.getColumnIndex(KEY_USER_NAME));
                @SuppressLint("Range") String itemStatus = cursor.getString(cursor.getColumnIndex(KEY_USER_STATUS));
                @SuppressLint("Range") String itemPhone = cursor.getString(cursor.getColumnIndex(KEY_USER_PHONE));
                @SuppressLint("Range") String itemDescription = cursor.getString(cursor.getColumnIndex(KEY_USER_DESCRIPTION));
                @SuppressLint("Range") String itemDate = cursor.getString(cursor.getColumnIndex(KEY_USER_DATE));
                @SuppressLint("Range") String itemLocation = cursor.getString(cursor.getColumnIndex(KEY_USER_LOCATION));
                item = new Item(itemName, itemStatus, itemPhone, itemDescription, itemDate, itemLocation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return item;
    }

    public void deleteItem(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Delete the item with the given name
            int rows = db.delete(TABLE_ITEMS, KEY_USER_NAME + " = ?", new String[]{name});
            if (rows > 0) {
                Log.d("DatabaseHelper", "Deleted " + rows + " items from the database.");
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error while trying to delete item", e);
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<>();
        // Select All Query
        Log.d("dmm", "1");
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_ITEMS;
        Log.d("dmm", "2");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_USER_NAME));
                @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_USER_STATUS));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_USER_PHONE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_USER_DESCRIPTION));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_USER_DATE));
                @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_USER_LOCATION));
                Log.d("dmm", "3");
                Item item = new Item(name, status, phone, description, date, location);
                items.add(item);
            } while (cursor.moveToNext());
        }

        // Close cursor and database
        cursor.close();
        db.close();

        return items;
    }
}