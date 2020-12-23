package util;

import model.Preferences;

import java.io.*;

public class TextWriter {

    /**
     * Values to be used in the preferences
     */
    public static final String NAME_SETTING_EXPORT_PATH = "Export path";
    public static final String NAME_SETTING_EXPORT_FILENAME = "File name";

    private static String path = "";
    private static String fileName = "default";
    private String extension = ".txt";
    private File file;

    public TextWriter(Preferences preferences) {
        file = new File(getPathname(preferences));
    }

    /**
     * Tries to get prefered settings from user. If those are empty, fall back to default.
     * @param preferences
     * @return
     */
    private String getPathname(Preferences preferences) {
        String pathname = preferences.getSettingFromModule(NAME_SETTING_EXPORT_PATH) +
                preferences.getSettingFromModule(NAME_SETTING_EXPORT_FILENAME);

        if (!pathname.isEmpty()){
            return pathname + getExtension();
        }

        return getPath() + getFileName() + getExtension();
    }

    /**
     * Writes data to file
     *
     * @param data
     */
    public void write(String data) throws IOException {
        int numberOfLines = 1;
        String dataWithNewLine = data + System.getProperty("line.separator");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (int i = numberOfLines; i > 0; i--) {
            bufferedWriter.write(dataWithNewLine);
        }
        bufferedWriter.close();
        fileWriter.close();
    }

    public String getAbsolutePath() {
        return file.getAbsolutePath();
    }

    public static String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

}
