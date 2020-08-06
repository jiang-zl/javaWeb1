package models;

import jdbcUtil.MyUtilOfJdbc;

/**
 * @author jiangzl
 */
public class LoginModels extends MyUtilOfJdbc {
    static protected String myDB = "mywechat";

    private String name;
    private String pass;

    public LoginModels(String name, String pass){
        super(myDB);
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }
}
