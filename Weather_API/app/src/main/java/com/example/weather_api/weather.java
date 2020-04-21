package com.example.weather_api;

public class weather {
    private String day_weather;
    private String time;
    private String minTemp;
    private String maxTemp;
    private String icon;

    public weather(String day_weather, String time, String minTemp, String maxTemp, String icon) {
        this.day_weather = day_weather;
        this.time = time;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.icon = icon;
    }

    public String getDay_weather() {
        return day_weather;
    }

    public String getTime() {
        return time;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getIcon() {
        return icon;
    }
}
