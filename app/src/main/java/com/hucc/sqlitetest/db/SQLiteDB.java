package com.hucc.sqlitetest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hucc.sqlitetest.model.Contact;

/**
 * Created by chunchun.hu on 2018/2/27.
 */

public class SQLiteDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contact.db";

    public static final String TABLE_NAME = "contact";
    public static final String COLUMN_ID = "contact_id";
    public static final String COLUMN_NAME = "contact_name";
    public static final String COLUMN_PHONE = "contact_phone";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_PHONE + " TEXT" + " )";

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        onUpgrade(db, oldVersion, newVersion);
    }

    public void add(Contact contactBean) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contactBean.getName());
        values.put(COLUMN_PHONE, contactBean.getPhone());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(TABLE_NAME, null, values);
    }

    public Cursor query() {
        SQLiteDatabase db = getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_PHONE};
        // How you want the results sorted in the resulting Cursor
        String sortOrder = COLUMN_NAME + " ASC";

        Cursor  cursor = db.query(TABLE_NAME,  projection, null, null, null, null, sortOrder);

        return cursor;
    }

    public void delete(int id) {
        SQLiteDatabase db = getReadableDatabase();

        // Define 'where' part of query.
        String selection = COLUMN_ID + " LIKE ? ";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(id)};
        // Issue SQL statement.
        db.delete(TABLE_NAME,selection,selectionArgs);
    }

    public void update(Contact contact) {
        SQLiteDatabase db = getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,contact.getName());
        values.put(COLUMN_PHONE, contact.getPhone());

        // Which row to update, based on the ID
        String selection = COLUMN_ID + " LIKE ?";
        String[] selectionArgs = new String[]{String.valueOf(contact.getId())};

        int count = db.update(TABLE_NAME, values, selection, selectionArgs);
    }
}
