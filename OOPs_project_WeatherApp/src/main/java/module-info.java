module org.example.oops_project_weatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens org.example.oops_project_weatherapp to javafx.fxml;
    exports org.example.oops_project_weatherapp;
}