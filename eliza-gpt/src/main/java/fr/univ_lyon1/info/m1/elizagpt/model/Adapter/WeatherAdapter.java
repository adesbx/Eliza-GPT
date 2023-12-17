package fr.univ_lyon1.info.m1.elizagpt.model.Adapter;

import fr.univ_lyon1.info.m1.elizagpt.model.Data.DataType;

import java.util.HashMap;
import java.util.Map;

/**
 * Class called to get our data if the request is not null.
 */
public class WeatherAdapter {
    private final Weather weather;

    /**
     * Basics constructor.
     @param weather the data we want to get
     */
    public WeatherAdapter(final Weather weather) {
        this.weather = weather;
    }

    /**
     * Get the result of the weather if different than null else bad request.
     * @return String
     */
    public Map<DataType, String> getResults() {
        WeatherData weatherData = weather.getWeather();
        Map<DataType, String> res = new HashMap<>();
        res.put(DataType.TEMPERATURE, Double.toString(weatherData.getTemperature()));
        res.put(DataType.HUMIDITY, Integer.toString(weatherData.getHumidity()));
        res.put(DataType.WINDSPEED, Double.toString(weatherData.getWindSpeed()));
        res.put(DataType.TIME, weatherData.getDate().replace("T", " à ").replace("Z", ""));
        //        if (weatherData == null) {
        //            return "Problème de connexion avec l'API";
        //        }
        return res;
    }

    /**
     * Take all the data and make it a string.
     * @return String
     */
    private String convertToString(final WeatherData data) {
        String weatherString =
                "Temperature: " + data.getTemperature() + "°C\n"
                + "Humidité: " + data.getHumidity() + "%\n"
                + "Vitesse du vent: " + data.getWindSpeed() + " km/h\n"
                + "a Lyon pour le: " + data.getDate().replace("T", " à ").replace("Z", "");
        return weatherString;
    }
}
