import controller.IndexController;
import controller.ViewController;
import util.DatabaseConnection;

public class Main {

    public static void main(String[] args) {
        DatabaseConnection.initializeDBConnection();
        IndexController index = new IndexController();
        index.indexAction();
        ViewController.launchView(args);
    }

}
