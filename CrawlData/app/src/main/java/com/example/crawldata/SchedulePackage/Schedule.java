package com.example.crawldata.SchedulePackage;

import java.io.Serializable;

public class Schedule implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nameClass;
    private String dayOfWeek;
    private String timeOfDay;
    private String room;
    private String lecturer;
    private String group;

    public Schedule() {
    }

    public Schedule(String nameClass, String dayOfWeek, String timeOfDay, String room, String lecturer, String group) {
        this.nameClass = nameClass;
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
        this.room = room;
        this.lecturer = lecturer;
        this.group = group;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Môn học : " + this.getNameClass() + "\n" +
                "Giảng viên : " + this.getLecturer() + "\n" +
                "Phòng : " + this.getRoom() + "\n" +
                "Tiết bắt đầu : " + this.getTimeOfDay() + "\n" +
                "Thứ : " + this.getDayOfWeek() + "\n" +
                "Nhóm : " + this.group + "\n";

    }
}
