package com.example.asssignmentjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUsage extends Application {

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
                ResultSet resultSet = statement.executeQuery("SELECT * FROM EV_Countries");

                while (resultSet.next()) {
                    String country = resultSet.getString("country");
                    int EV_count = resultSet.getInt("EV_count");

                    System.out.println(country + " " + EV_count);
                }

                resultSet.close();
                statement.close();
                connection.close();

                // Load the FXML file and display the GUI
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dbChart.fxml"));
                Parent root = loader.load();
                HelloController controller = loader.getController();
                controller.chart();

                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.show();
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
