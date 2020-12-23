package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Preferences;
import util.AlertBox;
import util.Navigator;
import util.TextWriter;
import util.Validator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class PreferencesController {

    private Preferences preferences;

    @FXML
    private TextField ExportPath, ExportName;
    @FXML
    private Button updateConfirm;
    @FXML
    private AnchorPane preferenceMainPane;

    public PreferencesController() {
        this.preferences = new Preferences();
    }

    @FXML
    public void initialize() {
        if(ExportName != null || ExportPath != null) {
            ExportName.setText(preferences.getSettingFromModule(TextWriter.NAME_SETTING_EXPORT_FILENAME));
            ExportPath.setText(preferences.getSettingFromModule(TextWriter.NAME_SETTING_EXPORT_PATH));
        }
    }

    /**
     * Updates export preferences
     * Can be easily extended to store more preferences when extending getUpdatedPreferencesList().
     */
    public void updateExportPreference() {
        if (!Validator.validate(ExportName)) {
            return;
        }
        try {
            HashMap<String, String> preferenceList = getUpdatedPreferencesList();
            Set set = preferenceList.entrySet();
            for (Object aSet : set) {
                Map.Entry mentry = (Map.Entry) aSet;
                preferences.setModule(mentry.getKey().toString());
                preferences.setSetting(mentry.getValue().toString());
                preferences.update();
            }
        } catch (Exception e) {
            AlertBox.display(e.getMessage());
        }
    }

    /**
     * Puts preferences in hashmap, to store in database
     * @return
     */
    private HashMap<String, String> getUpdatedPreferencesList() {
        HashMap<String, String> preferenceList = new HashMap<>();
        preferenceList.put(TextWriter.NAME_SETTING_EXPORT_FILENAME, ExportName.getText());
        preferenceList.put(TextWriter.NAME_SETTING_EXPORT_PATH, ExportPath.getText());
        return preferenceList;
    }

    /**
     * Gets default preferences
     * @return
     */
    List<Preferences> getAllPreferences() {
        try {
            return this.preferences.getAllPreferences();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Default settings for whole application where needed
     */
    void setDefaultSettings() {
        //Textwriter settings
        preferences.setModule(TextWriter.NAME_SETTING_EXPORT_PATH);
        preferences.setSetting(TextWriter.getPath());
        preferences.create();

        preferences.setModule(TextWriter.NAME_SETTING_EXPORT_FILENAME);
        preferences.setSetting(TextWriter.getFileName());
        preferences.create();
    }

    public void loadPreferenceWindow() throws IOException {
        Parent updateExportPath;
        updateExportPath = FXMLLoader.load(getClass().getResource("../view/updateExportPath.fxml"));
        preferenceMainPane.getChildren().setAll(updateExportPath);
    }

    public void loadUpdateUserWindow() throws IOException {
        Parent updateUser;
        updateUser = FXMLLoader.load(getClass().getResource("../view/userUpdate.fxml"));
        preferenceMainPane.getChildren().setAll(updateUser);
    }

    public void loadWallet () {
        Navigator.loadView(Navigator.WALLET);
    }

}
