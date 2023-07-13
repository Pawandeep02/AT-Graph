package com.example.javaAssignment;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainController {

    @FXML
    private BarChart<String, Integer> barChart;

    public void generateBarChart() {
        String query = "SELECT country, plane_count FROM PlaneData GROUP BY country, plane_count ORDER BY country ASC LIMIT 5";

        DatabaseConnector dbConnector = new DatabaseConnector();
        Connection connection = dbConnector.connectDB();

        try {
            XYChart.Series<String, Integer> chartData = new XYChart.Series<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                chartData.getData().add(new XYChart.Data<>(resultSet.getString("country"), resultSet.getInt("plane_count")));
            }

            resultSet.close();
            statement.close();

            barChart.getData().add(chartData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openPieChartWindow() {
        Stage stage = new Stage();
        PieChart pieChart = new PieChart();

        String query = "SELECT country, plane_count FROM PlaneData";

        DatabaseConnector dbConnector = new DatabaseConnector();
        Connection connection = dbConnector.connectDB();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String country = resultSet.getString("country");
                int planeCount = resultSet.getInt("plane_count");

                pieChart.getData().add(new PieChart.Data(country, planeCount));
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(pieChart, 400, 300);

        stage.setTitle("Plane Count per Country");
        stage.setScene(scene);
        stage.show();
    }
}
