package com.example.crawldata.ScorePackage;

public class CPA {
    private String semester;
    private String medium_score;
    private String accumulation_score;
    private String sotin;
    private String sotin_tichluy;

    public CPA() {
    }


    public CPA(String semester, String medium_score, String accumulation_score, String sotin, String sotin_tichluy) {
        this.semester = semester;
        this.medium_score = medium_score;
        this.accumulation_score = accumulation_score;
        this.sotin = sotin;
        this.sotin_tichluy = sotin_tichluy;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getMedium_score() {
        return medium_score;
    }

    public void setMedium_score(String medium_score) {
        this.medium_score = medium_score;
    }

    public String getAccumulation_score() {
        return accumulation_score;
    }

    public void setAccumulation_score(String accumulation_score) {
        this.accumulation_score = accumulation_score;
    }

    public String getSotin() {
        return sotin;
    }

    public void setSotin(String sotin) {
        this.sotin = sotin;
    }

    public String getSotin_tichluy() {
        return sotin_tichluy;
    }

    public void setSotin_tichluy(String sotin_tichluy) {
        this.sotin_tichluy = sotin_tichluy;
    }

    @Override
    public String toString() {
        return this.getSemester() + "\n" + this.getMedium_score() + "\n" +
                this.getAccumulation_score() + "\n" + this.getSotin() + "\n" + this.getSotin_tichluy() + "\n";
    }
}
