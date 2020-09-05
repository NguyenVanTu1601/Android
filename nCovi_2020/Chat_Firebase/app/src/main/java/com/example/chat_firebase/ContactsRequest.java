package com.example.chat_firebase;

/**
 * extent từ contact để đọc toàn bộ dữ liệu bao gồm cả uid của tài khoản đó cho tiện xử lý trong requestFragment
 */
public class ContactsRequest extends Contacts {
    private String uid;

    public ContactsRequest(String uid) {
        this.uid = uid;
    }

    public ContactsRequest(String name, String status, String image, String uid) {
        super(name, status, image);
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
