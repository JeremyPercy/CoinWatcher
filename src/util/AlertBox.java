package util;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.stage.StageStyle;

public class AlertBox {

    public static void display(String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        label.setWrapText(true);
        Button closeButton = new Button("Close the window");
        closeButton.setDefaultButton(true);
        closeButton.setOnAction(event -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 250, 150);
        window.setScene(scene);
        window.showAndWait();
    }
}
