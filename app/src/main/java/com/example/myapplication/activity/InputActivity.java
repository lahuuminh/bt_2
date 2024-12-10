package com.example.myapplication.activity;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Provider.CustomerProvider;
import com.example.myapplication.R;
import com.example.myapplication.model.CustomerModel;
import com.example.myapplication.util.DBHelper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class InputActivity extends AppCompatActivity {

    private EditText inputCustomerPhone, inputCurrentPoint, inputNewPoint, inputNote;
    private Button buttonSave, buttonSaveNext, buttonInput, buttonUse, buttonList, btnLoadPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input); // Thay thế 'activity_main' bằng tên file layout của bạn nếu khác

        // Khởi tạo các view
        inputCustomerPhone = findViewById(R.id.inputCustomerPhone);
        inputCurrentPoint = findViewById(R.id.inputCurrentPoint);
        inputCurrentPoint.setEnabled(false);  // Không cho phép chỉnh sửa
        inputCurrentPoint.setFocusable(false);  // Không thể focus vào EditText
        inputNewPoint = findViewById(R.id.inputNewPoint);
        inputNote = findViewById(R.id.inputNote);

        buttonSave = findViewById(R.id.buttonSave);
        buttonSaveNext = findViewById(R.id.buttonSaveNext);
        buttonInput = findViewById(R.id.buttonInput);
        buttonUse = findViewById(R.id.buttonUse);
        buttonList = findViewById(R.id.buttonList);
        btnLoadPoint = findViewById(R.id.btn_load_point);
        // Xử lý sự kiện cho nút Save
        buttonSave.setOnClickListener((View) -> {
            String phone = inputCustomerPhone.getText().toString().trim();


            if (!isValidPhoneNumber(phone)) {
                Toast.makeText(InputActivity.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
                    if (inputCurrentPoint.getText().toString().isEmpty()) {
                        Toast.makeText(InputActivity.this, "Vui lòng ấn nút load point", Toast.LENGTH_SHORT).show();
                    return;}
            // Kiểm tra điểm nhập vào không phải là số âm, 0, hoặc chứa ký tự không hợp lệ
            if (!isValidPoint(inputNewPoint.getText().toString())) {
                Toast.makeText(InputActivity.this, "Điểm phải là số dương và không chứa ký tự đặc biệt và không được trống", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra độ dài ghi chú không vượt quá 100 ký tự
            if (!isValidNote(inputNote.getText().toString().trim())) {
                Toast.makeText(InputActivity.this, "Ghi chú không được vượt quá 100 ký tự và không bỏ trống", Toast.LENGTH_SHORT).show();
                return;
            }


                // Lấy giá trị điểm hiện tại từ EditText
                int currentPoint = Integer.parseInt(inputCurrentPoint.getText().toString());
                int newPointStr = Integer.parseInt(inputNewPoint.getText().toString());
                String note = inputNote.getText().toString().trim();
                int point = newPointStr + currentPoint;
                System.out.println(point);
                // Nếu các kiểm tra đều hợp lệ, tiến hành cập nhật điểm
                updatePointsForPhoneNumber(phone, point, note);

        });


        // Xử lý sự kiện cho nút Save & Next
        buttonSaveNext.setOnClickListener((View) -> {

            String phone = inputCustomerPhone.getText().toString().trim();


            if (!isValidPhoneNumber(phone)) {
                Toast.makeText(InputActivity.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (inputCurrentPoint.getText().toString().isEmpty()) {
                Toast.makeText(InputActivity.this, "Vui lòng ấn nút load point", Toast.LENGTH_SHORT).show();
                return;}
            // Kiểm tra điểm nhập vào không phải là số âm, 0, hoặc chứa ký tự không hợp lệ
            if (!isValidPoint(inputNewPoint.getText().toString())) {
                Toast.makeText(InputActivity.this, "Điểm phải là số dương và không chứa ký tự đặc biệt và không được trống", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra độ dài ghi chú không vượt quá 100 ký tự
            if (!isValidNote(inputNote.getText().toString().trim())) {
                Toast.makeText(InputActivity.this, "Ghi chú không được vượt quá 100 ký tự và không bỏ trống", Toast.LENGTH_SHORT).show();
                return;
            }

                // Lấy giá trị điểm hiện tại từ EditText
                int currentPoint = Integer.parseInt(inputCurrentPoint.getText().toString());
                int newPointStr = Integer.parseInt(inputNewPoint.getText().toString());
                String note = inputNote.getText().toString().trim();
                int point = newPointStr + currentPoint;
                System.out.println(point);
                // Nếu các kiểm tra đều hợp lệ, tiến hành cập nhật điểm
                updatePointsForPhoneNumber(phone, point, note);
                clearInputs();

        });
        btnLoadPoint.setOnClickListener((View) -> {
            String phone = inputCustomerPhone.getText().toString().trim();

            if (phone.isEmpty()) {
                Toast.makeText(InputActivity.this, "Số điện thoại không được để trống", Toast.LENGTH_SHORT).show();
            } else if (!isValidPhoneNumber(phone)) {
                Toast.makeText(InputActivity.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            } else {
                getPointsForPhoneNumber(phone);
                inputNewPoint.setText("");
                inputNote.setText("");
            }
        });


        // Xử lý sự kiện cho nút Input
        buttonInput.setOnClickListener((View) -> {
        });

        // Xử lý sự kiện cho nút Use
        buttonUse.setOnClickListener((View) -> {
            Intent intent = new Intent(InputActivity.this, UsePointActivity.class); // Chuyển đến UsePointActivity
            startActivity(intent); // Chạy activity UsePointActivity
        });

        // Xử lý sự kiện cho nút List
        buttonList.setOnClickListener((View) -> {
            Intent openCustomerListIntent = new Intent(InputActivity.this, ViewCustomerActivity.class);
            startActivity(openCustomerListIntent);
        });
    }

    // Phương thức lưu dữ liệu từ các input
    private void saveData() {
        String phone = inputCustomerPhone.getText().toString();
        String currentPoint = inputCurrentPoint.getText().toString();
        String newPoint = inputNewPoint.getText().toString();
        String note = inputNote.getText().toString();

    }

    // Phương thức xóa các input để nhập dữ liệu mới
    private void clearInputs() {
        inputCustomerPhone.setText("");
        inputCurrentPoint.setText("");
        inputNewPoint.setText("");
        inputNote.setText("");
    }


    private void getPointsForPhoneNumber(String phoneNumber) {
        Uri uri = Uri.withAppendedPath(CustomerProvider.CONTENT_URI, phoneNumber);
        String[] projection = {DBHelper.COLUMN_POINT};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        logCustomersFromContentProvider();
        if (cursor != null && cursor.moveToFirst()) {
            int point = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_POINT));
            inputCurrentPoint.setText(String.valueOf(point));
        } else {
            // Nếu không tìm thấy số điện thoại thì set điểm là 0
            Toast.makeText(InputActivity.this, "Số điện thoại chưa có điểm", Toast.LENGTH_SHORT).show();
            inputCurrentPoint.setText("0");
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    private void updatePointsForPhoneNumber(String phoneNumber, int newPoint, String note) {
        Uri uri = Uri.withAppendedPath(CustomerProvider.CONTENT_URI, phoneNumber);
        String selection = DBHelper.COLUMN_PHONE_NUMBER + "=?";
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_POINT, newPoint);
        contentValues.put(DBHelper.COLUMN_NOTES, note);
        long currentTimeMillis = System.currentTimeMillis();

        // Chuyển đổi thành đối tượng Date
        Date date = new Date(currentTimeMillis);

        // Định dạng ngày tháng năm giờ phút giây
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

//        contentValues.put(DBHelper.COLUMN_TIME_CREATED, sdf.format(date)); // Sửa ở đây

        Log.i("point", String.valueOf(newPoint));
        Log.i("Time Created", sdf.format(date));
//        contentValues.put(DBHelper.COLUMN_TIME_CREATED, System.currentTimeMillis()); // Lưu thời gian hiện tại
        int rowsUpdated = getContentResolver().update(uri, contentValues, selection, null);
        if (rowsUpdated > 0) {
            // Cập nhật thành công
            Toast.makeText(InputActivity.this, "Cập thành công cho phone" + "" + phoneNumber, Toast.LENGTH_SHORT).show();
        } else {
            // Nếu số điện thoại không tồn tại trong database
            // Thêm dữ liệu mới vào cơ sở dữ liệu

            contentValues.put(DBHelper.COLUMN_PHONE_NUMBER, phoneNumber);
            contentValues.put(DBHelper.COLUMN_POINT, newPoint);
            contentValues.put(DBHelper.COLUMN_NOTES, note);
//            contentValues.put(DBHelper.COLUMN_TIME_CREATED, System.currentTimeMillis()); // Lưu thời gian hiện tại
            contentValues.put(DBHelper.COLUMN_TIME_CREATED, currentTimeMillis); // Sửa ở đây
            Log.i("Time Created", sdf.format(date));
            Uri newUri = getContentResolver().insert(CustomerProvider.CONTENT_URI, contentValues);
            if (newUri != null) {
                Toast.makeText(InputActivity.this, "Thêm thành công cho phone: " + phoneNumber, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InputActivity.this, "Thêm không thành công cho phone: " + phoneNumber, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isValidPoint(String pointStr) {
        // Kiểm tra nếu pointStr bị null hoặc là chuỗi rỗng
        if (pointStr == null || pointStr.trim().isEmpty()) {
            System.out.println("Điểm không được bỏ trống");
            return false;
        }

        try {
            // Chuyển chuỗi thành số nguyên
            int point = Integer.parseInt(pointStr);

            // Kiểm tra nếu point là 0 hoặc giá trị âm
            if (point <= 0) {
                System.out.println("Điểm không được là số 0 hoặc âm");
                return false;
            }

            // Kiểm tra nếu point vượt quá giá trị tối đa của kiểu int
            if (point > Integer.MAX_VALUE) {
                System.out.println("Điểm không được vượt quá kích thước của int");
                return false;
            }

            return true;

        } catch (NumberFormatException e) {
            // Xử lý khi point không phải là một số hợp lệ
            System.out.println("Điểm phải là một số hợp lệ");
            return false;
        }
    }



    private boolean isValidNote(String note) {
        // Kiểm tra ghi chú không được bỏ trống và độ dài không vượt quá 100 ký tự
        if (note == null || note.trim().isEmpty()) {
            System.out.println("Ghi chú không được bỏ trống");
            return false;
        }
        if (note.length() > 100) {
            System.out.println("Ghi chú không được vượt quá 100 ký tự");
            return false;
        }
        return true;
    }


    private boolean isValidPhoneNumber(String phone) {
        // Số điện thoại phải chứa đúng 10 ký tự, bắt đầu bằng số 0, và chỉ chứa số
        String regex = "^(0[3|5|7|8|9])\\d{8}$"; // Định dạng số điện thoại hợp lệ ở Việt Nam
        // Kiểm tra xem chuỗi có chứa ký tự nào khác ngoài số hay không
        if (!phone.matches("\\d+")) {
            return false; // Nếu chứa ký tự không hợp lệ
        }
        return phone.matches(regex);
    }

    public void logCustomersFromContentProvider() {
        Uri uri = CustomerProvider.CONTENT_URI;
        String[] projection = {DBHelper.COLUMN_PHONE_NUMBER, DBHelper.COLUMN_POINT, DBHelper.COLUMN_NOTES, DBHelper.COLUMN_TIME_CREATED};

        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PHONE_NUMBER));
                int points = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_POINT));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NOTES));
                String timeCreated = String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TIME_CREATED)));

                // Log thông tin của từng khách hàng
                Log.i("CustomerInfo", "Phone: " + phoneNumber + ", Points: " + points + ", Notes: " + notes + ", Time Created: " + timeCreated);
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            Log.i("CustomerInfo", "Không có dữ liệu nào trong ContentProvider.");
        }
    }


}