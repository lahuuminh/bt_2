package com.example.myapplication.util;

import android.util.Log;
import android.util.Xml;

import com.example.myapplication.model.CustomerModel;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class XmlExporter {
    public static void exportCustomersToXML(ArrayList<CustomerModel> customers, String filename) throws Exception {
        // Kiểm tra xem danh sách có trống không
        if (customers.isEmpty()) {
            throw new IllegalArgumentException("Danh sách khách hàng trống.");
        }

        // Khởi tạo XmlSerializer
        XmlSerializer xmlSerializer = Xml.newSerializer();
        try (FileOutputStream fileOutputStream = new FileOutputStream(filename);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream)) {

            // Bắt đầu ghi XML
            xmlSerializer.setOutput(outputStreamWriter);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag("", "customers");

            for (CustomerModel customer : customers) {
                xmlSerializer.startTag("", "customer");
                xmlSerializer.attribute("", "phoneNumber", customer.getPhoneNumber());
                xmlSerializer.attribute("", "note", customer.getNote());
                xmlSerializer.attribute("", "point", String.valueOf(customer.getPoint()));
                xmlSerializer.attribute("", "timeCreated", String.valueOf(customer.getTimeCreated()));
                xmlSerializer.endTag("", "customer");
            }

            xmlSerializer.endTag("", "customers");
            xmlSerializer.endDocument();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ExportFileError", "Error exporting file: " + e.getMessage());
            throw new Exception("Có lỗi khi xuất file XML: " + e.getMessage());
        }
    }

}
