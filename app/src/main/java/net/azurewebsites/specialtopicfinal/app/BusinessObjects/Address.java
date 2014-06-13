/*
 * Michael Burton Copyright (c) 2014.
 */

package net.azurewebsites.specialtopicfinal.app.BusinessObjects;

/**
 * Created by Mike on 06/05/2014.
 */
public class Address
{
   int addressID;
   String email;
   String firstName;
   String lastName;
   String addressLine1;
   String addressLine2;
   int suburbID;
   int postCode;
   String country;
   boolean isDefault;
   int productID;
   Suburb suburb;

    public int getAddressID() {
        return addressID;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public int getSuburbID() {
        return suburbID;
    }

    public int getPostCode() {
        return postCode;
    }

    public String getCountry() {
        return country;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public int getProductID() {
        return productID;
    }

    public Suburb getSuburb() {
        return suburb;
    }

    public Address(int addressID, String email, String firstName, String lastName, String addressLine1, String addressLine2, int suburbID, int postCode, String country, boolean isDefault, int productID, Suburb suburb) {
        this.addressID = addressID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.suburbID = suburbID;
        this.postCode = postCode;
        this.country = country;
        this.isDefault = isDefault;
        this.productID = productID;
        this.suburb = suburb;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressID=" + addressID +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", suburbID=" + suburbID +
                ", postCode=" + postCode +
                ", country='" + country + '\'' +
                ", isDefault=" + isDefault +
                ", ProductID=" + productID +
                ", suburb=" + suburb +
                '}';
    }
}
