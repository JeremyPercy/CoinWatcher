package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.AlertBox;
import util.ConfirmBox;
import util.Validator;
import util.Navigator;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    /**
     * ApiInterface key for authorisation with bittrex
     */
    private static String publicApiKey = "95a92189990740be844a5f5c3f741d69";
    private static String secretKey = "41befca45db041709053a0f87e68d7cb";
    private User user;
    private boolean checkUser = false;

    @FXML
    private TextField userName, publicKey, privateKey, updatePublicKey, updatePrivateKey;

    @FXML
    private Button updateConfirm, register;

    @FXML
    public void initialize() {
        this.user.read();
        String publicKey = this.user.getPublicKey();
        String privateKey = this.user.getPrivateKey();
        if(publicKey != null && privateKey != null) {
            setUpdateKeys(publicKey, privateKey);
        }
    }

    private void setUpdateKeys(String publicKey, String privateKey) {
        if(updatePublicKey == null || updatePrivateKey == null) {
            return;
        }
        updatePublicKey.setText(publicKey);
        updatePrivateKey.setText(privateKey);
    }

    public UserController() {
        user = new User();
    }

    public String whoIs () throws SQLException {
        this.user.read();
        ResultSet result = user.result();
        return result.getString(1);

    }

    public void updateUser() {
        if (Validator.validate(updatePublicKey) && Validator.validate(updatePrivateKey)) {
            user = new User(updatePublicKey.getText(), updatePrivateKey.getText());
            Stage window = (Stage) updateConfirm.getScene().getWindow();
            // do what you have to do
            user.update();
            window.close();
        }
    }

    public void registerUser () {
        if (Validator.validate(userName) && Validator.validate(publicKey) && Validator.validate(privateKey)) {
            user = new User(userName.getText(), publicKey.getText(), privateKey.getText());
            try {
                user.create();
                Navigator.loadView(Navigator.WALLET);
            } catch(Exception e) {
                System.out.println("Yeeaaaa.... nooo i did not :(");
            }
        }
    }

    public void deleteUser() {
        if(ConfirmBox.display("Delete User", "Are you sure you want to delete the current user")) {
            try {
                if(this.user.delete()) {
                    AlertBox.display("User is deleted.");
                    Navigator.loadView(Navigator.USERLOGIN);
                }
            } catch (Exception e) {
                System.out.println("Something went wrong " + e);
            }
        }
    }

    public void getUser() {
        this.user.read();
        publicApiKey = this.user.getPublicKey();
        secretKey = this.user.getPrivateKey();
        if (publicApiKey != null && secretKey != null) {
            checkUser = true;
        }
    }

    public static String getSecretKey() {
        return secretKey;
    }
    public static String getPublicApiKey() {
        return publicApiKey;
    }

    public boolean getCheckUser() {
        getUser();
        return checkUser;
    }
}
