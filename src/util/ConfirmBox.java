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

public class ConfirmBox {

    private static boolean answer;

    public static boolean display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);
        label.setWrapText(true);

        Button confirmButton = new Button("Yes");
        Button cancelButton = new Button("No");

        confirmButton.setDefaultButton(true);
        cancelButton.setCancelButton(true);
        confirmButton.setOnAction( event -> {
            answer = true;
            window.close();
        });
        cancelButton.setOnAction(event -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, confirmButton, cancelButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 250, 150);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
