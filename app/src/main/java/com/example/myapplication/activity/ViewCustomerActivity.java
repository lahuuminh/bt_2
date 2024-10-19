package com.example.myapplication.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CustomerAdapter;
import com.example.myapplication.model.CustomerModel;
import com.example.myapplication.util.DBHelper;

import java.util.ArrayList;

public class ViewCustomerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<CustomerModel> customersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);

        initData();
        initViews();
    }

    private void initData() {
        DBHelper dbHelper = new DBHelper(this);

        // Nếu chưa có db thì chạy dòng này 1 lần duy nhất
//        dbHelper.insertCustomer(new CustomerModel("0988338791", 10, "abc"));
//        dbHelper.insertCustomer(new CustomerModel("0363768911", 30, "abcd"));
//        dbHelper.insertCustomer(new CustomerModel("0789657453", 50, null));

        this.customersData = dbHelper.getAllCustomers();
    }

    private void initViews() {
        this.recyclerView = findViewById(R.id.recyclerView);
        this.recyclerView.setAdapter(new CustomerAdapter(this, this.customersData));
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
