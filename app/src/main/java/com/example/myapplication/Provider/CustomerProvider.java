package com.example.myapplication.Provider;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.myapplication.util.DBHelper;

public class CustomerProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.myapplication.provider.CustomerProvider";
    private static final String PATH_CUSTOMERS = "customer";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH_CUSTOMERS);

    private static final int CUSTOMERS = 1;
    private static final int CUSTOMER_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, PATH_CUSTOMERS, CUSTOMERS);
        uriMatcher.addURI(AUTHORITY, PATH_CUSTOMERS + "/#", CUSTOMER_ID);
    }

    private DBHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case CUSTOMERS:
                cursor = db.query(DBHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CUSTOMER_ID:
                selection = DBHelper.COLUMN_PHONE_NUMBER + " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                cursor = db.query(DBHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert(DBHelper.TABLE_NAME, null, values);
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.update(DBHelper.TABLE_NAME, values, selection, new String[]{uri.getLastPathSegment()});
    }

    // Trả về null cho getType nếu không cần
    @Override
    public String getType(Uri uri) {
        return null;
    }

    // Trả về 0 cho delete nếu không cần xóa
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
}

