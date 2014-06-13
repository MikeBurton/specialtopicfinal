/*
 * Michael Burton Copyright (c) 2014.
 */

package net.azurewebsites.specialtopicfinal.app.BusinessObjects;

/**
 * Created by Mike on 06/05/2014.
 */
public class Image
{
    String imageID;
    boolean isDefault;
    int productID;

    public String getImageID() {
        return imageID;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public int getProductID() {
        return productID;
    }
    public Image(){}
    public Image(String imageID, boolean isDefault, int productID) {
        this.imageID = imageID;
        this.isDefault = isDefault;
        this.productID = productID;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageID='" + imageID + '\'' +
                ", isDefault=" + isDefault +
                ", productID=" + productID +
                '}';
    }
}
