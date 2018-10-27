package com.example.qamberhaider.sales.Model;

/**
 * Created by qamber.haider on 8/2/2018.
 */

public class User {

    public String employee_FirstName;
    public String employee_Email;
    public String employee_Password;
    public String employee_Profilepicture;
    public String id;

    public User() {
    }

    public User(String employee_FirstName, String employee_Email, String employee_Password, String employee_Profilepicture, String id) {
        this.employee_FirstName = employee_FirstName;
        this.employee_Email = employee_Email;
        this.employee_Password = employee_Password;
        this.employee_Profilepicture = employee_Profilepicture;
        this.id = id;
    }

    public String getEmployee_FirstName() {
        return employee_FirstName;
    }

    public void setEmployee_FirstName(String employee_FirstName) {
        this.employee_FirstName = employee_FirstName;
    }

    public String getEmployee_Email() {
        return employee_Email;
    }

    public void setEmployee_Email(String employee_Email) {
        this.employee_Email = employee_Email;
    }

    public String getEmployee_Password() {
        return employee_Password;
    }

    public void setEmployee_Password(String employee_Password) {
        this.employee_Password = employee_Password;
    }

    public String getEmployee_Profilepicture() {
        return employee_Profilepicture;
    }

    public void setEmployee_Profilepicture(String employee_Profilepicture) {
        this.employee_Profilepicture = employee_Profilepicture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
