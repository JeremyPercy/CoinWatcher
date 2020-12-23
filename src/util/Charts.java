package util;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.History;

import java.util.List;


public class Charts {

    public static void display(String currencyName) {
        Stage window = new Stage();
        window.setTitle("Chart history from " + currencyName);
        window.initModality(Modality.APPLICATION_MODAL);
        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        //creating the chart
        final LineChart<String, Number> lineChart =
                new LineChart<String, Number>(xAxis, yAxis);

        lineChart.setTitle(currencyName);
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName(currencyName);
        //populating the series with data
        History currency = new History();
        for (History singleCurrency : currency.getCurrencyData(currencyName)) {
            series.getData().add(new XYChart.Data(singleCurrency.getUpdatedAt(), singleCurrency.getBalance()));
        }
        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().add(series);


        window.setScene(scene);
        window.show();
    }
}