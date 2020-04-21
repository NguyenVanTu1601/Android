package com.example.retrofit_okhttp_web;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sinhvien implements Parcelable {

@SerializedName("Id")
@Expose
private String id;
@SerializedName("Taikhoan")
@Expose
private String taikhoan;
@SerializedName("Matkhau")
@Expose
private String matkhau;
@SerializedName("Hinhanh")
@Expose
private String hinhanh;

    protected Sinhvien(Parcel in) {
        id = in.readString();
        taikhoan = in.readString();
        matkhau = in.readString();
        hinhanh = in.readString();
    }

    public static final Creator<Sinhvien> CREATOR = new Creator<Sinhvien>() {
        @Override
        public Sinhvien createFromParcel(Parcel in) {
            return new Sinhvien(in);
        }

        @Override
        public Sinhvien[] newArray(int size) {
            return new Sinhvien[size];
        }
    };

    public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getTaikhoan() {
return taikhoan;
}

public void setTaikhoan(String taikhoan) {
this.taikhoan = taikhoan;
}

public String getMatkhau() {
return matkhau;
}

public void setMatkhau(String matkhau) {
this.matkhau = matkhau;
}

public String getHinhanh() {
return hinhanh;
}

public void setHinhanh(String hinhanh) {
this.hinhanh = hinhanh;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(taikhoan);
        dest.writeString(matkhau);
        dest.writeString(hinhanh);
    }
}