package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.CustomerModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CustomerModel> customersData;

    // Constructors
    public CustomerAdapter(Context context, ArrayList<CustomerModel> customersData) {
        this.context = context;
        this.customersData = customersData;
    }

    /* Chịu trách nhiệm nạp layout cho 1 Item  */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View itemView = inflater.inflate(R.layout.item_customer_layout, parent, false);

        return new ViewHolder(itemView);
    }

    /* Chịu trách nhiệm liên kết dữ liệu từ danh sách vào ViewHolder */
    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.ViewHolder holder, int position) {
        CustomerModel customer = this.customersData.get(position);

        holder.tvCustomerNumber.setText(customer.getPhoneNumber());
        holder.tvCustomerPoint.setText(Integer.toString(customer.getPoint()));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formatedTime = sdf.format(customer.getTimeCreated());
        holder.tvCreatedDate1.setText(formatedTime);
        holder.tvCreatedDate2.setText(formatedTime);

        holder.tvNote.setText(customer.getNote());
    }

    @Override
    public int getItemCount() {
        return this.customersData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Customer Views
        private TextView tvCustomerNumber, tvCustomerPoint, tvCreatedDate1, tvCreatedDate2, tvNote;

        /*
            itemView chính là container chứa toàn bộ các views con của 1 item,
            nó giống như là 1 layout
        */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvCustomerNumber = itemView.findViewById(R.id.tvPhoneNumber);
            this.tvCustomerPoint = itemView.findViewById(R.id.tvCustomerPoint);
            this.tvCreatedDate1 = itemView.findViewById(R.id.tvCreationDate1);
            this.tvCreatedDate2 = itemView.findViewById(R.id.tvCreationDate2);
            this.tvNote = itemView.findViewById(R.id.tvNote);
        }
    }
}
