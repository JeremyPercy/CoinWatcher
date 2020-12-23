package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.AlertBox;
import util.ConfirmBox;
import util.Navigator;

import java.io.IOException;

/**
 * Main controller class for the entire layout.
 */
public class ViewController extends Application {

    /**
     * Holder of a switchable pane
     */
    @FXML
    private AnchorPane rootPane;


    @Override
    public void start(Stage window) throws Exception {
        window.getIcons().add(new Image("/assets/images/logoicon.png"));
        window.setTitle("Coin Watcher");
        window.setOnCloseRequest(event -> {
            event.consume();
            closeProgram();
        });
        window.setScene(createScene(
                loadMainPane()
        ));
        window.setResizable(false);
        window.show();
    }

    /**
     * Loads the main fxml layout.
     * Sets up the Navigator.
     * Loads the first screen into the fxml layout.
     *
     * @return the loaded pane.
     * @throws IOException if the pane could not be loaded.
     */
    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane mainPane = (Pane) loader.load(
                getClass().getResourceAsStream(
                        "/view/mainWindow.fxml"
                )
        );
        ViewController viewController = loader.getController();
        UserController userController = new UserController();

        Navigator.setViewController(viewController);
        if (!userController.getCheckUser()) {
            Navigator.loadView(Navigator.USERLOGIN);
        } else {
            Navigator.loadView(Navigator.WALLET);
        }
        return mainPane;
    }

    /**
     * Creates the main application scene.
     *
     * @param mainPane the main application layout.
     * @return the created scene.
     */
    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(
                mainPane, 1300, 800
        );
        scene.getStylesheets().add(
                this.getClass().getResource("/assets/styles/styles.css").toExternalForm()
        );

        return scene;
    }

    /**
     * Replaces the rootPane displayed.
     *
     * @param node the pane node to be swapped in.
     */
    public void setRootPane(Node node) {
        rootPane.getChildren().setAll(node);
    }

    public static void launchView(String[] args) {
        launch(args);
    }

    public void aboutUsAction() {
        Navigator.loadView(Navigator.ABOUTUS);
    }

    public void exportPortofolioCoins() {
        ExportController exporter = new ExportController();
        if (exporter.exportPortofolioCoins())
            AlertBox.display("Success, File written to " + exporter.filePath());
    }

    public void closeProgram() {
        boolean answer = ConfirmBox.display("Close program", "Do you want to close program");

        if (answer)
            Platform.exit();
    }

    public void updateUserAction() {
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane mainPane = (Pane) loader.load(
                    getClass().getResourceAsStream(
                            "../view/userUpdate.fxml"
                    )
            );
            Stage window2 = new Stage();
            window2.initModality(Modality.APPLICATION_MODAL);
            window2.setTitle("Update you API");
            window2.setScene(new Scene(mainPane));
            window2.show();
        } catch (IOException e) {
            System.out.println("cannot load screen" + e);
        }
    }

    public void showPreferencesAction() {
        Navigator.loadView(Navigator.PREFERENCES);

    }
}