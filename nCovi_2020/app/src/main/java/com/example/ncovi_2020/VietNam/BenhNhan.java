package com.example.ncovi_2020.VietNam;

public class BenhNhan {
    private String maBenhNhan;
    private int tuoi;
    private String gioiTinh;
    private String diaDiem;
    private String tinhTrang;
    private String quocTich;

    public BenhNhan(String maBenhNhan, int tuoi, String gioiTinh, String diaDiem, String tinhTrang, String quocTich) {
        this.maBenhNhan = maBenhNhan;
        this.tuoi = tuoi;
        this.gioiTinh = gioiTinh;
        this.diaDiem = diaDiem;
        this.tinhTrang = tinhTrang;
        this.quocTich = quocTich;
    }

    public String getMaBenhNhan() {
        return maBenhNhan;
    }

    public int getTuoi() {
        return tuoi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public String getQuocTich() {
        return quocTich;
    }
}
