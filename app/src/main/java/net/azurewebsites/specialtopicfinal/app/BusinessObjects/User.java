/*
 * Michael Burton Copyright (c) 2014.
 */

package net.azurewebsites.specialtopicfinal.app.BusinessObjects;

import java.util.ArrayList;

/**
 * Created by Mike on 06/05/2014.
 */
public class User
{
    String UserName;
    String Password;
    String FirstName;
    String LastName;
    String Type;
    String Phone;
    ArrayList<Address> addresses;
    ArrayList<Hire> hires;

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }



    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getType() {
        return Type;
    }



    public String getPhone() {
        return Phone;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public ArrayList<Hire> getHires() {
        return hires;
    }

    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }

    public void setHires(ArrayList<Hire> hires) {
        this.hires = hires;
    }
    public User(){}
    public User(String userName, String firstName, String lastName){
        this.UserName = userName;
        this.FirstName = firstName;
        this.LastName = lastName;

    }
    public User(String userName, String password, String firstName, String lastName, String type, String phone, ArrayList<Address> addresses, ArrayList<Hire> hires)
    {
        this.UserName = userName;
        this.Password = password;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Type = type;
        this.Phone = phone;
        this.addresses = addresses;
        this.hires = hires;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Type='" + Type + '\'' +
                ", Phone='" + Phone + '\'' +
                ", addresses=" + addresses +
                ", hires=" + hires +
                '}';
    }
}
