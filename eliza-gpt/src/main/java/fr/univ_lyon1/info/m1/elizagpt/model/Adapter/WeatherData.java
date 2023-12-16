package fr.univ_lyon1.info.m1.elizagpt.model.Adapter;

/**
 * Class to stock the data about the data.
 */
public class WeatherData {
    private double temperature;
    private int humidity;
    private double windSpeed;
    private  String date;

    /**
     * Basic constructor to the class.
     */
    public WeatherData() {
        this.temperature = 0;
        this.windSpeed = 0;
        this.humidity = 0;
        this.date = "";
    }

    double getTemperature() {
        return this.temperature;
    }

    int getHumidity() {
        return this.humidity;
    }

    double getWindSpeed() {
        return this.windSpeed;
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

    void setWindSpeed(final double windSpeed) {
        this.windSpeed = windSpeed;
    }

    void setDate(final String date) {
        this.date = date;
    }
}
