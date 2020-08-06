package models;

/**
 * @author jiangzl
 */
public class DoctorModels extends LoginModels {
    private String emailNumber;

    public DoctorModels(String name, String pass){
        super(name, pass);
    }

    public DoctorModels(String name, String pass, String emailNumber){
        super(name, pass);
        this.emailNumber = emailNumber;
    }

}
