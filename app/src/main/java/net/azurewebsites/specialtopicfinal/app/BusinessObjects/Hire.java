/*
 * Michael Burton Copyright (c) 2014.
 */

package net.azurewebsites.specialtopicfinal.app.BusinessObjects;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Mike on 06/05/2014.
 */
public class Hire {

    int hireID;
    String email;
    String notes;
    boolean isComplete;
    boolean isCurrent;
    double grandTotal;
    Date timeStamp;
    User user;
    ArrayList<HireDetail> hireDetails;

    public int getHireID() {
        return hireID;
    }

    public String getEmail() {
        return email;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<HireDetail> getHireDetails() {
        return hireDetails;
    }
    public Hire(){}
    public Hire(int hireID,String email)
    {
        this.hireID = hireID;
        this.email = email;
        if (hireDetails == null){hireDetails = new ArrayList<HireDetail>();}
    }
    public Hire(int hireID, String email, String notes, boolean isComplete, boolean isCurrent, double grandTotal, Date timeStamp, User user, ArrayList<HireDetail> hireDetails) {
        this.hireID = hireID;
        this.email = email;
        this.notes = notes;
        this.isComplete = isComplete;
        this.isCurrent = isCurrent;
        this.grandTotal = grandTotal;
        this.timeStamp = timeStamp;
        this.user = user;
        this.hireDetails = hireDetails;
    }

    @Override
    public String toString() {
        return "Hire{" +
                "HireID=" + hireID +
                ", email='" + email + '\'' +
                ", notes='" + notes + '\'' +
                ", isComplete=" + isComplete +
                ", isCurrent=" + isCurrent +
                ", grandTotal=" + grandTotal +
                ", timeStamp=" + timeStamp +
                ", user=" + user +
                ", hireDetails=" + hireDetails +
                '}';
    }
}
