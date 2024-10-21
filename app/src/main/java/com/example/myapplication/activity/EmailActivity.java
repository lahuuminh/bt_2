package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.R;
import com.example.myapplication.util.CheckEmail;



import java.io.File;

public class EmailActivity extends AppCompatActivity {

    private EditText editTextSubject, editTextBody, editTextSender;
    private String recipient, subject, body;
    private TextView editTextRecipient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        // Nhận đường dẫn file XML
        String xmlFilePath = getIntent().getStringExtra("filename");
        File xmlFile = (xmlFilePath != null) ? new File(xmlFilePath) : null;

        // Tham chiếu đến các trường nhập liệu
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextBody = findViewById(R.id.editTextBody);
        editTextRecipient = findViewById(R.id.editTextRecipient);


        // Thêm một nút để gửi email
        Button buttonSend = findViewById(R.id.buttonSend);

        // Xử lý sự kiện khi người dùng nhấn vào nút Gửi Email
        buttonSend.setOnClickListener(v -> {
            String subject = editTextSubject.getText().toString();
            String body = editTextBody.getText().toString();
            recipient = editTextRecipient.getText().toString().isEmpty() ? "lahuuminh678@gmail.com" : editTextRecipient.getText().toString();
            if (CheckEmail.isValidEmail(recipient)) {
                openEmailApp(recipient, subject, body, xmlFile);
            } else {
                Toast.makeText(EmailActivity.this, "Địa chỉ email không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void openEmailApp(String recipient, String subject, String body, File xmlFile) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822"); // Định dạng email

        // Tạo Uri cho file XML
        Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", xmlFile);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri); // Đính kèm file

        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Cấp quyền cho các ứng dụng khác đọc file

        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}