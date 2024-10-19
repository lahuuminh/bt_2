package com.example.myapplication.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;


public class InputActivity extends AppCompatActivity {

    private EditText inputCustomerPhone, inputCurrentPoint, inputNewPoint, inputNote;
    private Button buttonSave, buttonSaveNext, buttonInput, buttonUse, buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input); // Thay thế 'activity_main' bằng tên file layout của bạn nếu khác

        // Khởi tạo các view
        inputCustomerPhone = findViewById(R.id.inputCustomerPhone);
        inputCurrentPoint = findViewById(R.id.inputCurrentPoint);
        inputNewPoint = findViewById(R.id.inputNewPoint);
        inputNote = findViewById(R.id.inputNote);

        buttonSave = findViewById(R.id.buttonSave);
        buttonSaveNext = findViewById(R.id.buttonSaveNext);
        buttonInput = findViewById(R.id.buttonInput);
        buttonUse = findViewById(R.id.buttonUse);
        buttonList = findViewById(R.id.buttonList);

        // Xử lý sự kiện cho nút Save
        buttonSave.setOnClickListener((View)-> {

        });

        // Xử lý sự kiện cho nút Save & Next
        buttonSaveNext.setOnClickListener((View)-> {

        });

        // Xử lý sự kiện cho nút Input
        buttonInput.setOnClickListener((View)-> {

        });

        // Xử lý sự kiện cho nút Use
        buttonUse.setOnClickListener((View)-> {

        });

        // Xử lý sự kiện cho nút List
        buttonList.setOnClickListener((View)-> {
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
}