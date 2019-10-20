package com.example.weatherapp;

public class forecast_item {
    String datetime;
    String wind;
    String pressure;
    String humidity;
    String temperature;
    String imglink;
    String description;

    public forecast_item(String datetime, String wind, String pressure, String humidity, String temperature, String imglink, String description) {
        this.datetime = datetime;
        this.wind = wind;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temperature = temperature;
        this.imglink = imglink;
        this.description = description;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getImglink() {
        return imglink;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
