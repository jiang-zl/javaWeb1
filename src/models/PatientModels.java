package models;

/**
 * @author jiangzl
 */
public class PatientModels extends LoginModels {
    private String phoneNumber;

    public PatientModels(String name, String pass){
        super(name, pass);
    }

    public PatientModels(String name, String pass, String phoneNumber){
        super(name, pass);
        this.phoneNumber = phoneNumber;
    }

}
