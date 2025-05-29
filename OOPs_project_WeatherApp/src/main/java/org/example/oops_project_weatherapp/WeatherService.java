package org.example.oops_project_weatherapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {

    private final String apiKey;

    public WeatherService() {
        this.apiKey = "c7790ba47086b7d5b530fbf08955d195";
    }

    public String getWeatherData(String city) throws Exception {
        //making URL
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + this.apiKey;
        URL url = new URL(apiUrl);
        //connecting to website
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            //using file handling to read data received in JSON
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } finally {
            // closing the connection after the operation
            connection.disconnect();
        }
    }
}