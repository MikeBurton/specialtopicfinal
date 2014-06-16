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
    String Email;
    String Phone;
    ArrayList<Address> addresses;
    ArrayList<Hire> hires;

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

    public String getEmail() {
        return Email;
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
    public User(String userName, String password, String firstName, String lastName, String type, String email, String phone, ArrayList<Address> addresses, ArrayList<Hire> hires)
    {
        this.UserName = userName;
        this.Password = password;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Type = type;
        this.Email = email;
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
                ", Email='" + Email + '\'' +
                ", Phone='" + Phone + '\'' +
                ", addresses=" + addresses +
                ", hires=" + hires +
                '}';
    }
}
