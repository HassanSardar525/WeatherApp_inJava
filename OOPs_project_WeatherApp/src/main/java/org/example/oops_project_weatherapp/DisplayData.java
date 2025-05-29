package org.example.oops_project_weatherapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

public class DisplayData {
    //Enum for weather conditions
    public enum WeatherCondition {
        CLEAR("clear", "/images/clear.png", "#e6ffff"),
        SCATTERED_CLOUDS("scattered clouds", "/images/scattered_clouds.png", "#c6d9eb"),
        CLOUDS("clouds", "/images/cloudy.png", "#5c8a8a"),
        RAIN("rain", "/images/rainy.png", "#527a7a"),
        SNOW("snow", "/images/snowy.png", "#c6d9eb"),
        STORM("storm", "/images/stormy.png", "#476b6b"),
        DEFAULT("default", "/images/default.png", "#ffffff");

        private final String description;
        private final String imagePath;
        private final String backgroundColor;

        WeatherCondition(String description, String imagePath, String backgroundColor) {
            this.description = description;
            this.imagePath = imagePath;
            this.backgroundColor = backgroundColor;
        }

        public String getDescription() {
            return description;
        }

        public String getImagePath() {
            return imagePath;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public static WeatherCondition pic(String description) {
            for (WeatherCondition condition : values()) {
                if (description.contains(condition.description)) {
                    return condition;
                }
            }
            return DEFAULT;
        }
    }

    @FXML
    private Label Temp;

    @FXML
    private Label Humidity;

    @FXML
    private Button SearchButton;

    @FXML
    private Label WeatherDescription;

    @FXML
    private TextField cityInput;

    @FXML
    private Label humidityLabel;

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Label cityName;

    @FXML
    private ImageView weatherImage;

    @FXML
    private Pane lowerPane;

    @FXML
    private Button addCIty;

    @FXML
    private Button AddNewCity;


    @FXML
    public void initialize() {
        // Setting image for SearchButton
        Image searchImage = new Image(getClass().getResourceAsStream("/images/search.png"));
        SearchButton.setGraphic(new ImageView(searchImage));
        
    }

    // Method for search button action
    @FXML
    private void handleSearchButtonAction() {
        String city = cityInput.getText();
        if (city != null && !city.isEmpty() && city.matches("[a-zA-Z]+")) {//validation for city
            WeatherService weatherService = new WeatherService();
            try {
                String weatherData = weatherService.getWeatherData(city);
                displayWeatherData(weatherData);
            } catch (Exception e) {
                System.out.println("Error, cannot get weather data not found..");
                cityName.setText("City not found...");
                e.printStackTrace();
            }
        } else {
            cityName.setText("Enter valid city name");
        }
    }

    // Method to display weather data
    public void displayWeatherData(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject main = jsonObject.getJSONObject("main");
            double temp = main.getDouble("temp") - 273.15; // Convert Kelvin to Celsius
            int humidity = main.getInt("humidity");
            String weatherDescription = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
            String city = jsonObject.getString("name");

            // Update the UI elements
            Temp.setText(Math.round(temp) + "°C");
            Humidity.setText(humidity + "%");
            WeatherDescription.setText(weatherDescription);
            WeatherDescription.setWrapText(true);
            cityName.setText(city);
            updateWeatherImage(weatherDescription);
        } catch (Exception e) {
            System.out.println("Error parsing data");
            e.printStackTrace();
        }
    }

    // Method to update the weather image
    @FXML
    private void updateWeatherImage(String weatherDescription) {
        WeatherCondition condition = WeatherCondition.pic(weatherDescription);
        
        Image image;
        try {
            image = new Image(getClass().getResourceAsStream(condition.getImagePath()));
            if (image.isError()) throw new IllegalArgumentException("Image not found: " + condition.getImagePath());
        } catch (Exception e) {
            // Handle image loading errors
            System.out.println("Error loading image: " + condition.getImagePath());
            e.printStackTrace();
            image = new Image(getClass().getResourceAsStream(WeatherCondition.DEFAULT.getImagePath()));
        }
        weatherImage.setImage(image);
        //AnchorPane.setStyle("-fx-background-color: " + condition.getBackgroundColor() + ";" +
         //       "-fx-transition: 500ms ease-in-out ;" );

    }
    Stage stage2;
    @FXML
    public void AddCity(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addCItyPage.fxml"));
        Scene scene2 = new Scene(fxmlLoader.load());
        stage2 = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage2.setScene(scene2);
        stage2.show();
    }
}




