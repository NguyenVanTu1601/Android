package com.example.read_danh_ba_dt;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    Context context;
    private List<Contact> myListContact;
    public ContactManager(Context context) {
        this.context = context;
        getContactData();
    }

    public List<Contact> getMyListContact() {
        return myListContact;
    }

    private void getContactData() {
        myListContact = new ArrayList<>();

        // projection là bảng ảo chưa dữ liệu cần lấy ra
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI
        };

        // Cursor là con trỏ giống trong SQLite tương tự select để lấy dữ liệu
        Cursor phone = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, null, null, null);

        phone.moveToFirst(); // chuyển con trỏ cursor về đầy danh sách
        int nameIndex   = phone.getColumnIndex(projection[0]);
        int numberIndex = phone.getColumnIndex(projection[1]);
        int photoIndex  = phone.getColumnIndex(projection[2]);
        while (phone.moveToNext()){
            String name = phone.getString(nameIndex);
            String numberPhone = phone.getString(numberIndex);
            String photoUri = phone.getString(photoIndex);
            Bitmap bitmap = getPhoto(photoUri);
            myListContact.add(new Contact(name,numberPhone,bitmap));
        }
        phone.close(); // đóng con trỏ
    }

    private Bitmap getPhoto(String photoUri) {
        if(photoUri != null){
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(photoUri));
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

}
