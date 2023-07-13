package com.example.javaAssignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyJavaApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            DatabaseConnector dbConnector = new DatabaseConnector();
            Connection connection = dbConnector.connectDB();

            if (connection != null) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM plane");

                while (resultSet.next()) {
                    String carModel = resultSet.getString("countries");
                    int carCount = resultSet.getInt("number_of_planes");

                    System.out.println(carModel + " - " + carCount);
                }


            } else {
                System.out.println("Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
