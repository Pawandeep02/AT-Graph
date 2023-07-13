package com.example.asssignmentjava;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HelloController {

    @FXML
    private BarChart<String, Integer> barChart;

    public void chart() {
        String chartSQL = "SELECT country, EV_count FROM EV_Countries GROUP BY country, EV_count ORDER BY TIMESTAMP(country) ASC LIMIT 5";

        DatabaseConnector dbConnector = new DatabaseConnector();
        Connection connection = dbConnector.connectDB();

        try {
            XYChart.Series<String, Integer> chartData = new XYChart.Series<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(chartSQL);

            while (resultSet.next()) {
                chartData.getData().add(new XYChart.Data<>(resultSet.getString(1), resultSet.getInt(2)));
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

        String pieChartSQL = "SELECT country, EV_count FROM EV_Countries ";

        DatabaseConnector dbConnector = new DatabaseConnector();
        Connection connection = dbConnector.connectDB();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(pieChartSQL);

            while (resultSet.next()) {
                String category = resultSet.getString("country");
                int count = resultSet.getInt("EV_count");

                pieChart.getData().add(new PieChart.Data(category, count));
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a new scene with the pie chart
        Scene scene = new Scene(pieChart, 400, 300);

        stage.setTitle("EV's per Country");
        stage.setScene(scene);
        stage.show();
    }
}
