package fr.univ_lyon1.info.m1.elizagpt.model.Adapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Class that is going to do the request and set the value.
 */
public class Weather {

    /**
     * This fonction do a GET method on the url and parse the response.
     * @return an object WeatherData with all the data
     */
    public WeatherData getWeather() {
        WeatherData weatherData = null;
        String apiKey = "s6aTYDtmFGgGvPl4fRBbjLraKPGK0CeS";
        String urlDefault = "https://api.tomorrow.io/v4/weather/forecast?location=45.75,4.85&apikey=" + apiKey;
        try {
            URL url = new URL(urlDefault);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                weatherData = parseData(response.toString());
            } else {
                System.out.println("Erreur : " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherData;
    }

    /**
     * Take json and update a weatherdata object with all the data we want.
     * @param jsonResponse the json
     * @return an object WeatherData with all the data
     */
    private WeatherData parseData(final String jsonResponse) {
        WeatherData weatherData = new WeatherData();

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);

            if (jsonObject.containsKey("timelines")) {
                JSONObject timelines = (JSONObject) jsonObject.get("timelines");
                JSONArray minutely = (JSONArray) timelines.get("minutely");

                if (minutely != null && minutely.size() > 0) {
                    JSONObject firstEntry = (JSONObject) minutely.get(0);

                    JSONObject values = (JSONObject) firstEntry.get("values");
                    Object timeObject = firstEntry.get("time");

                    weatherData.setDate((String) timeObject);

                    Object windSpeedObject = values.get("windSpeed");
                    Object temperatureObject = values.get("temperature");
                    Object humidityObject = values.get("humidity");

                    if (windSpeedObject != null) {
                        if (windSpeedObject instanceof Number) {
                            double windSpeed = ((Number) windSpeedObject).doubleValue();
                            weatherData.setWinSpeed(windSpeed);
                        } else {
                            System.out.println("winspeed is Nan");
                        }
                    } else {
                        System.out.println("winspeed is null");
                    }

                    if (temperatureObject != null) {
                        if (temperatureObject instanceof Number) {
                            double temperature = ((Number) temperatureObject).doubleValue();
                            weatherData.setTemperature(temperature);
                        } else {
                            System.out.println("temp is not Nan");
                        }
                    } else {
                        System.out.println("temp is null");
                    }

                    if (humidityObject != null) {
                        if (humidityObject instanceof Number) {
                            int humidity = ((Number) humidityObject).intValue();
                            weatherData.setHumidity(humidity);
                        } else {
                            System.out.println("humidity is Nan");
                        }
                    } else {
                        System.out.println("humidity is null");
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weatherData;
    }
}
