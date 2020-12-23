package util;

import controller.ViewController;
import javafx.fxml.FXMLLoader;


import java.io.IOException;

/**
 * Utility class for controlling navigation between Windows.
 *
 * All methods on the navigator are static to facilitate
 * simple access from anywhere in the application.
 */
public class Navigator {

    /**
     * Convenience constants for fxml layouts managed by the navigator.
     */
    public static final String MAIN    = "../view/mainWindow.fxml";
    public static final String USERLOGIN    = "../view/userLogin.fxml";
    public static final String WALLET   = "../view/wallet.fxml";
    public static final String ABOUTUS   = "../view/AboutUs.fxml";
    public static final String PREFERENCES   = "../view/preferences.fxml";

    /** The main application layout controller. */
    private static ViewController viewController;

    /**
     * Stores the main controller for later use in navigation tasks.
     *
     * @param viewController the main application layout controller.
     */
    public static void setViewController(ViewController viewController) {
        Navigator.viewController = viewController;
    }

    /**
     * Loads the vista specified by the fxml file into the
     * rootPane of the main application layout.
     *
     * Previously loaded pane for the same fxml file are not cached.
     * The fxml is loaded anew and a new node hierarchy generated
     * every time this method is invoked.
     *
     * A more sophisticated load function could potentially add some
     * enhancements or optimizations, for example:
     *   cache FXMLLoaders
     *   cache loaded vista nodes, so they can be recalled or reused
     *   allow a user to specify node reuse or new creation
     *   allow back and forward history like a browser
     *
     * @param fxml the fxml file to be loaded.
     */
    public static void loadView(String fxml) {
        try {
            viewController.setRootPane(
                    FXMLLoader.load(
                            Navigator.class.getResource(
                                    fxml
                            )
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}