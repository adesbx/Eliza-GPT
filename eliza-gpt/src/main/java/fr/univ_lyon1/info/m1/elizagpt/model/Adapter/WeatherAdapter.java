package fr.univ_lyon1.info.m1.elizagpt.model.Adapter;

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
    public String getResults() {
        WeatherData weatherData = weather.getWeather();
        if (weatherData == null) {
            return "Problème de connexion avec l'API";
        }
        return convertToString(weatherData);
    }

    /**
     * Take all the data and make it a string.
     * @return String
     */
    private String convertToString(final WeatherData data) {
        String weatherString =
                "Temperature: " + data.getTemperature() + "°C\n"
                + "Humidité: " + data.getHumidity() + "%\n"
                + "Vitesse du vent: " + data.getWinSpeed() + " km/h\n"
                + "a Lyon pour le: " + data.getDate();
        return weatherString;
    }
}
