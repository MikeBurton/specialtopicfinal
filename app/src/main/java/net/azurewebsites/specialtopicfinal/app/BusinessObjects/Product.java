/*
 * Michael Burton Copyright (c) 2014.
 */

package net.azurewebsites.specialtopicfinal.app.BusinessObjects;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Mike on 06/05/2014.
 */
public class Product
{
    int productID;
    int categoryID;
    String description;
    boolean isCurrent;
    String name;
    int parentProductID;
    double price;
    int stockCount;
    ArrayList<Image> images;
    Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getProductID() {
        return productID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public String getName() {
        return name;
    }

    public int getParentProductID() {
        return parentProductID;
    }

    public double getPrice() {
        return price;
    }

    public int getStockCount() {
        return stockCount;
    }

    public ArrayList<Image> getImages() {
        return images;
    }
    public Product(){}
    public Product(int productID, int categoryID, String description, boolean isCurrent, String name, int parentProductID, double price, int stockCount,ArrayList<Image> images) {
        this.productID = productID;
        this.categoryID = categoryID;

        this.description = description;
        this.isCurrent = isCurrent;
        this.name = name;
        this.parentProductID = parentProductID;
        this.price = price;
        this.stockCount = stockCount;
        this.images = images;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", categoryID=" + categoryID +
                ", description='" + description + '\'' +
                ", isCurrent=" + isCurrent +
                ", name='" + name + '\'' +
                ", parentProductID=" + parentProductID +
                ", price=" + price +
                ", stockCount=" + stockCount +
                ", images=" + images +
                '}';
    }
}
