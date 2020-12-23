package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Preferences extends Model {

    private final String TB_NAME = "preferences";

    /**
     * ID of the setting, must be unique
     * Example: textwriter_path
     */
    private String module = "";

    /**
     * Example: "/path/"
     */
    private String setting = "";

    /**
     * Result
     */
    private ResultSet result;

    public Preferences() {
    }

    public Preferences(String module, String setting) {
        this.module = module;
        this.setting = setting;
    }

    @Override
    public boolean createDbTable() {
        return this.query("update",
                "CREATE TABLE IF NOT EXISTS " + TB_NAME +
                        " ('module' string, 'setting' string)");
    }

    @Override
    public boolean create() {
        String sql = "INSERT INTO " + TB_NAME +
                "('module', 'setting') VALUES " +
                "('" + module + "','" + setting + "')";
        return this.query("update", sql);
    }

    @Override
    public void createOrUpdate() throws Exception {

    }

    @Override
    public void read() {
        String sql = "select * from " + TB_NAME;
        String where =  " WHERE `module` = '" + module + "'";

        if(!module.isEmpty())
            sql += where;

        this.query("result", sql);
        this.setResult(this.result());
    }

    @Override
    public boolean update() throws Exception {
        checkID();

        String query = "UPDATE " + TB_NAME + " SET " +
                "'setting' = '" + setting + "' " +
                "WHERE `module` = '" + module + "'";

        return this.query("update", query);
    }

    @Override
    public boolean delete() throws Exception {
        checkID();
        return this.query("update",
                "DELETE FROM  " + TB_NAME + " WHERE `module` = '" + module + "'");
    }

    public ArrayList<Preferences> getAllPreferences () throws SQLException {
        ArrayList<Preferences> preferencesArrayList = new ArrayList<Preferences>();
        this.read();
        ResultSet result = this.getResult();
        while(result.next()) {
            preferencesArrayList.add(new Preferences(
                    result.getString("module"),
                    result.getString("setting")
            ));
        }
        return preferencesArrayList;
    }
    /**
     * Checks ID from Preference. ID consists module
     * @throws Exception
     */
    private void checkID () throws Exception {
        if (this.module.isEmpty())
            throw new Exception("Module cannot be empty");
    }

    @Override
    public String toString() {
        return "Preferences{" +
                "module='" + module + '\'' +
                ", setting='" + setting + '\'' +
                '}';
    }

    public ResultSet getResult() {
        return result;
    }

    public void setResult(ResultSet result) {
        this.result = result;
    }

    public String getSettingFromModule(String module) {
        this.setModule(module);
        this.read();
        try {
            return getResult().getString("setting");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return setting;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }
}
