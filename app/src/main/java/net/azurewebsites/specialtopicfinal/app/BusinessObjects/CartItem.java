package net.azurewebsites.specialtopicfinal.app.BusinessObjects;

import java.sql.Date;

/**
 * Created by Mike on 01/06/2014.
 */
public class CartItem
{
    int productID;
    int hireID;
    double total;
    Date startDate;
    Date endDate;
    int amount;
    Product product;
    int hireDetailID;
    public CartItem() {

    }

    public CartItem(int productID, int hireID,int hireDetailID, Product product,int amount,Date startDate,Date endDate) {
        this.productID = productID;
        this.hireID = hireID;
        this.product = product;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hireDetailID = hireDetailID;
    }

    public int getProductID() {
        return productID;
    }

    public int getHireID() {
        return hireID;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getHireDetailID() {
        return hireDetailID;
    }


}
