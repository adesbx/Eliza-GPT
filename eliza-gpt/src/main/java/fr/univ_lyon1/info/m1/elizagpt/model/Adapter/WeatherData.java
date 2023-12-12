package fr.univ_lyon1.info.m1.elizagpt.model.Adapter;

/**
 * Class to stock the data about the data.
 */
public class WeatherData {
    private double temperature;
    private int humidity;
    private double winSpeed;
    private  String date;

    /**
     * Basic constructor to the class.
     */
    public WeatherData() {
        this.temperature = 0;
        this.winSpeed = 0;
        this.humidity = 0;
        this.date = "";
    }

    double getTemperature() {
        return this.temperature;
    }

    int getHumidity() {
        return this.humidity;
    }

    double getWinSpeed() {
        return this.winSpeed;
    }

    String getDate() {
        return this.date;
    }

    void setTemperature(final double temp) {
        this.temperature = temp;
    }

    void setHumidity(final int humidity) {
        this.humidity = humidity;
    }

    void setWinSpeed(final double winSpeed) {
        this.winSpeed = winSpeed;
    }

    void setDate(final String date) {
        this.date = date;
    }
}
