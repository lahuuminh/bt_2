package com.example.myapplication;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.util.ArrayList;
import java.util.List;

public class ViewCustomer extends AppCompatActivity {

    private ListView listView;
    private CustomAdapter adapter;
    private List<Customer> customerList;
    private Button buttonInput, buttonUse, buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);

        listView = findViewById(R.id.listView);
        buttonInput = findViewById(R.id.buttonInput);
        buttonUse = findViewById(R.id.buttonUse);
        buttonList = findViewById(R.id.buttonList);

        // Tạo danh sách khách hàng mẫu
        customerList = new ArrayList<>();
        customerList.add(new Customer("0987654321", "01/01/2023", "VIP Customer"));
        customerList.add(new Customer("0912345678", "15/06/2022", "Regular Customer"));

        // Khởi tạo adapter và set cho ListView
        adapter = new CustomAdapter(this, customerList);
        listView.setAdapter(adapter);

        // Xử lý sự kiện cho các nút (tùy chỉnh thêm theo nhu cầu)
        buttonInput.setOnClickListener(v -> {
            // Xử lý sự kiện khi nhấn nút Input
        });

        buttonUse.setOnClickListener(v -> {
            // Xử lý sự kiện khi nhấn nút Use
        });

        buttonList.setOnClickListener(v -> {
            // Xử lý sự kiện khi nhấn nút List
        });
    }
}