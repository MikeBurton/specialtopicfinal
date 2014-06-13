/*
 * Michael Burton Copyright (c) 2014.
 */

package net.azurewebsites.specialtopicfinal.app.BusinessObjects;

import java.sql.Date;

/**
 * Created by Mike on 06/05/2014.
 */
public class HireDetail
{
    int hireDetailID;
    int hireID;
    int productID;
    Date timeStamp;
    Date startDate;
    Date endDate;
    Date returnedDate;
    boolean ended;

    public int getHireDetailID() {
        return hireDetailID;
    }

    public int getHireID() {
        return hireID;
    }

    public int getProductID() {
        return productID;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public boolean isEnded() {
        return ended;
    }
    public HireDetail( int hireID, int productID)
    {

        this.hireID = hireID;
        this.productID = productID;
    }
    public HireDetail(int hireDetailID, int hireID, int productID, Date timeStamp, Date startDate, Date endDate, Date returnedDate, boolean ended) {
        this.hireDetailID = hireDetailID;
        this.hireID = hireID;
        this.productID = productID;
        this.timeStamp = timeStamp;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returnedDate = returnedDate;
        this.ended = ended;
    }

    @Override
    public String toString() {
        return "HireDetail{" +
                "HireDetailID=" + hireDetailID +
                ", HireID=" + hireID +
                ", ProductID=" + productID +
                ", timeStamp=" + timeStamp +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", returnedDate=" + returnedDate +
                ", ended=" + ended +
                '}';
    }
}
