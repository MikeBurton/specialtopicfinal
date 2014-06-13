/*
 * Michael Burton Copyright (c) 2014.
 */

package net.azurewebsites.specialtopicfinal.app.BusinessObjects;

import java.util.ArrayList;

/**
 * Created by Mike on 06/05/2014.
 */
public class Property extends Product
{
    int addressID;
    String rooms ;
    String propertyType;
    int floorArea;
    int landArea;


    public int getAddressID() {
        return addressID;
    }

    public String getRooms() {
        return rooms;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public int getFloorArea() {
        return floorArea;
    }

    public int getLandArea() {
        return landArea;
    }



    public Property(int productID, int categoryID, String defaultImage, String description, boolean isCurrent, String name, int parentProductID, double price, int stockCount, ArrayList<Image> images, int addressID, String rooms, String propertyType, int floorArea, int landArea) {

        super(productID, categoryID, description, isCurrent, name, parentProductID, price, stockCount, images);
        this.addressID = addressID;
        this.rooms = rooms;
        this.propertyType = propertyType;
        this.floorArea = floorArea;
        this.landArea = landArea;

    }

    @Override
    public String toString() {
        return "Property{" +
                "addressID=" + addressID +
                ", rooms='" + rooms + '\'' +
                ", propertyType='" + propertyType + '\'' +
                ", floorArea=" + floorArea +
                ", landArea=" + landArea +
                '}';
    }
}
