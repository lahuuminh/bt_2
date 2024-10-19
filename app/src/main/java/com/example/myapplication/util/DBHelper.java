package com.example.myapplication.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.model.CustomerModel;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    // Database and Table name
    public static final String DATABASE_NAME = "customers.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "customer";

    // Table Attributes
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_POINT = "point";
    public static final String COLUMN_TIME_CREATED = "time_created";
    public static final String COLUMN_NOTES = "note";

    //Câu SQL để tạo bảng
    private static final String TABLE_CREATE = "create table " + TABLE_NAME + " (" +
            COLUMN_PHONE_NUMBER + " text primary key not null, " +
            COLUMN_POINT + " interger not null, " +
            COLUMN_NOTES + " text, " +
            COLUMN_TIME_CREATED + " interger not null);";   //Dùng timeCreated để làm khóa chính

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Methods
    public boolean insertCustomer(CustomerModel customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.COLUMN_PHONE_NUMBER, customer.getPhoneNumber());
        contentValues.put(DBHelper.COLUMN_POINT, customer.getPoint());
        contentValues.put(DBHelper.COLUMN_TIME_CREATED, customer.getTimeCreated());
        contentValues.put(DBHelper.COLUMN_NOTES, customer.getNote());

        long result = db.insert(DBHelper.TABLE_NAME, null, contentValues);
        db.close();

        // Nếu kết quả trả về -1 thì việc insert thất bại, ngược lại thành công
        return result != -1;
    }

    public ArrayList<CustomerModel> getAllCustomers() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CustomerModel> customers = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String phoneNumber = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PHONE_NUMBER));
                @SuppressLint("Range") int point = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_POINT));
                @SuppressLint("Range") long timeCreated = cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_TIME_CREATED));
                @SuppressLint("Range") String note = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NOTES));

                CustomerModel customer = new CustomerModel(phoneNumber, point, note);
                customer.setTimeCreated(timeCreated);

                customers.add(customer);
            } while (cursor.moveToNext());
        }

        db.close();

        return customers;
    }
}
