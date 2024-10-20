package com.example.myapplication.activity;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Provider.CustomerProvider;
import com.example.myapplication.R;
import com.example.myapplication.util.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class UsePointActivity extends AppCompatActivity {

    private EditText inputCustomerPhone, inputCurrentPoint, inputUsePoint, inputNote;
    private Button buttonSave, buttonSaveNext, buttonInput, buttonUse, buttonList,btn_load_Point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usepoint); // Thay thế 'activity_main' bằng tên file layout của bạn nếu khác

        // Khởi tạo các view
        inputCustomerPhone = findViewById(R.id.inputCustomerPhone);
        inputCurrentPoint = findViewById(R.id.inputCurrentPoint);
        inputCurrentPoint.setEnabled(false);
        inputCurrentPoint.setFocusable(false);
        inputUsePoint = findViewById(R.id.inputNewPoint);
        inputNote = findViewById(R.id.inputNote);

        buttonSave = findViewById(R.id.buttonSave);
        buttonSaveNext = findViewById(R.id.buttonSaveNext);
        buttonInput = findViewById(R.id.buttonInput);
        buttonUse = findViewById(R.id.buttonUse);
        buttonList = findViewById(R.id.buttonList);

        // Xử lý sự kiện cho nút Save
        buttonSave = findViewById(R.id.buttonSave);
        buttonSaveNext = findViewById(R.id.buttonSaveNext);
        buttonInput = findViewById(R.id.buttonInput);
        buttonUse = findViewById(R.id.buttonUse);
        buttonList = findViewById(R.id.buttonList);
        btn_load_Point=findViewById(R.id.button_load_point_1);
        // Xử lý sự kiện cho nút Save
        buttonSave.setOnClickListener((View) -> {
            String phone = inputCustomerPhone.getText().toString().trim();
            int newPointStr =Integer.parseInt(inputUsePoint.getText().toString());
            String note = inputNote.getText().toString().trim();

            if (!isValidPhoneNumber(phone)) {
                Toast.makeText(UsePointActivity.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            // Kiểm tra điểm nhập vào không phải là số âm, 0, hoặc chứa ký tự không hợp lệ
            if (!isValidPoint(newPointStr)) {
                Toast.makeText(UsePointActivity.this, "Điểm phải là số dương và không chứa ký tự đặc biệt", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra độ dài ghi chú không vượt quá 100 ký tự
            if (!isValidNote(note)) {
                Toast.makeText(UsePointActivity.this, "Ghi chú không được vượt quá 100 ký tự", Toast.LENGTH_SHORT).show();
                return;
            }

            if (inputCurrentPoint.getText().toString().isEmpty()) {
                Toast.makeText(UsePointActivity.this, "Vui lòng ấn nút load point", Toast.LENGTH_SHORT).show();}else{
            // Lấy giá trị điểm hiện tại từ EditText
            int currentPoint = Integer.parseInt(inputCurrentPoint.getText().toString());
            int point= currentPoint-newPointStr;
            if(point<0){
                Toast.makeText(UsePointActivity.this, "Điểm không còn đủ", Toast.LENGTH_SHORT).show();
            }else{
            updatePointsForPhoneNumber(phone, point, note);}}
        });

        // Xử lý sự kiện cho nút Save & Next
        buttonSaveNext.setOnClickListener((View)-> {

            String phone = inputCustomerPhone.getText().toString().trim();
            int newPointStr =Integer.parseInt(inputUsePoint.getText().toString());
            String note = inputNote.getText().toString().trim();

            if (!isValidPhoneNumber(phone)) {
                Toast.makeText(UsePointActivity.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            // Kiểm tra điểm nhập vào không phải là số âm, 0, hoặc chứa ký tự không hợp lệ
            if (!isValidPoint(newPointStr)) {
                Toast.makeText(UsePointActivity.this, "Điểm phải là số dương và không chứa ký tự đặc biệt", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra độ dài ghi chú không vượt quá 100 ký tự
            if (!isValidNote(note)) {
                Toast.makeText(UsePointActivity.this, "Ghi chú không được vượt quá 100 ký tự", Toast.LENGTH_SHORT).show();
                return;
            }

            if (inputCurrentPoint.getText().toString().isEmpty()) {
                Toast.makeText(UsePointActivity.this, "Vui lòng ấn nút load point", Toast.LENGTH_SHORT).show();}else{
                // Lấy giá trị điểm hiện tại từ EditText
                int currentPoint = Integer.parseInt(inputCurrentPoint.getText().toString());
                int point= currentPoint-newPointStr;
                if(point<0){
                    Toast.makeText(UsePointActivity.this, "Điểm không còn đủ", Toast.LENGTH_SHORT).show();
                }else{
                    updatePointsForPhoneNumber(phone, point, note);}}
        });
        // Thêm TextWatcher cho trường số điện thoại
        btn_load_Point.setOnClickListener((View)-> {
            String phone = inputCustomerPhone.getText().toString().trim();
            if (!isValidPhoneNumber(phone) && !phone.isEmpty()) {
                Toast.makeText(UsePointActivity.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            } else {
                getPointsForPhoneNumber(phone);
                inputUsePoint.setText("");
                inputNote.setText("");
            }
        });

        // Xử lý sự kiện cho nút Input
        buttonInput.setOnClickListener((View)-> {
            Intent intent = new Intent(UsePointActivity.this, InputActivity.class); // Chuyển đến InputActivity
            startActivity(intent);
        });

        // Xử lý sự kiện cho nút Use
        buttonUse.setOnClickListener((View)-> {
            Intent intent = new Intent(UsePointActivity.this, UsePointActivity.class); // Chuyển đến UsePointActivity
            startActivity(intent); // Chạy activity UsePointActivity
        });

        // Xử lý sự kiện cho nút List
        buttonList.setOnClickListener((View)-> {

        });
    }

    // Phương thức lưu dữ liệu từ các input
    private void saveData() {
        String phone = inputCustomerPhone.getText().toString();
        String currentPoint = inputCurrentPoint.getText().toString();
        String newPoint = inputUsePoint.getText().toString();
        String note = inputNote.getText().toString();

    }

    // Phương thức xóa các input để nhập dữ liệu mới
    private void clearInputs() {
        inputCustomerPhone.setText("");
        inputCurrentPoint.setText("");
        inputUsePoint.setText("");
        inputNote.setText("");
    }
    private void getPointsForPhoneNumber(String phoneNumber) {
        Uri uri = Uri.withAppendedPath(CustomerProvider.CONTENT_URI,phoneNumber);
        String[] projection = {DBHelper.COLUMN_POINT};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int point = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_POINT));
            inputCurrentPoint.setText(String.valueOf(point));
        } else {
            // Nếu không tìm thấy số điện thoại thì set điểm là 0
            Toast.makeText(UsePointActivity.this, "Số điện thoại chưa có điểm", Toast.LENGTH_SHORT).show();
            inputCurrentPoint.setText("0");
        }

        if (cursor != null) {
            cursor.close();
        }
    }
    private void updatePointsForPhoneNumber(String phoneNumber, int newPoint, String note) {
        Uri uri = Uri.withAppendedPath(CustomerProvider.CONTENT_URI, phoneNumber);
        String selection=DBHelper.COLUMN_PHONE_NUMBER+"=?";
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_POINT, newPoint);
        contentValues.put(DBHelper.COLUMN_NOTES, note);
        long currentTimeMillis = System.currentTimeMillis();

        // Chuyển đổi thành đối tượng Date
        Date date = new Date(currentTimeMillis);

        // Định dạng ngày tháng năm giờ phút giây
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        contentValues.put(DBHelper.COLUMN_TIME_CREATED, currentTimeMillis); // Lưu thời gian hiện tại
        Log.i("point", String.valueOf(newPoint));
//        contentValues.put(DBHelper.COLUMN_TIME_CREATED, System.currentTimeMillis()); // Lưu thời gian hiện tại
        int rowsUpdated = getContentResolver().update(uri, contentValues, selection, null);
        if (rowsUpdated > 0) {
            // Cập nhật thành công
            Toast.makeText(UsePointActivity.this, "Cập thành công cho phone"+""+ phoneNumber, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidPoint(int point) {
        // Điểm phải là số dương, không được là số 0 và không vượt quá kích thước của int
        return point > 0 && point <= Integer.MAX_VALUE;
    }

    private boolean isValidNote(String note) {
        // Kiểm tra xem độ dài của ghi chú không vượt quá 100 ký tự
        return note.length() <= 100;
    }
    private boolean isPhoneNumberCreated(String phone){
        Uri uri=Uri.withAppendedPath(CustomerProvider.CONTENT_URI,phone);
        String selection=DBHelper.COLUMN_PHONE_NUMBER+"=?";
       Cursor cur= getContentResolver().query(uri,null,selection,new String[]{phone},null);
        return cur!=null;
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
}