package com.example.myapplication.util;

import android.util.Xml;

import com.example.myapplication.model.CustomerModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlImporter {
    public static List<CustomerModel> importCustomersFromXML(InputStream inputStream) throws Exception {
        List<CustomerModel> customers = new ArrayList<>();
        try {
            // Khởi tạo XmlPullParser
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, null);
            int eventType = parser.getEventType();

            // Phân tích cú pháp XML
            CustomerModel currentCustomer = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("customer".equals(name)) {
                            currentCustomer = new CustomerModel();
                            // Đọc các thuộc tính từ thẻ <customer>
                            currentCustomer.setPhoneNumber(parser.getAttributeValue(null, "phoneNumber"));
                            currentCustomer.setNote(parser.getAttributeValue(null, "note"));
                            currentCustomer.setPoint(Integer.parseInt(parser.getAttributeValue(null, "point")));
                            currentCustomer.setTimeCreated(Long.parseLong(parser.getAttributeValue(null, "timeCreated")));
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if ("customer".equals(name) && currentCustomer != null) {
                            customers.add(currentCustomer);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new Exception("Có lỗi khi phân tích cú pháp XML: " + e.getMessage());
        } finally {
            inputStream.close(); // Đóng InputStream sau khi xong
        }
        return customers;
    }
}
