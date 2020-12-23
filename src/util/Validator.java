package util;


import javafx.scene.control.TextField;

public class Validator {

    public static boolean validate(TextField id) {
        if (id.getText() == null || id.getText().trim().isEmpty()) {
            AlertBox.display("Please " + id.getPromptText() );
            return false;
        } else {
            return true;
        }
    }

    public static String errorMessage(String name) {
        return name;
    }
}
