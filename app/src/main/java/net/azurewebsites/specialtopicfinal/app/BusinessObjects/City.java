/*
 * Michael Burton Copyright (c) 2014.
 */

package net.azurewebsites.specialtopicfinal.app.BusinessObjects;

import java.util.ArrayList;

/**
 * Created by Mike on 06/05/2014.
 */
public class City
{
    int CityID;
    String Name;
    ArrayList<Suburb> suburbs;

    public int getCityID() {
        return CityID;
    }

    public String getName() {
        return Name;
    }

    public ArrayList<Suburb> getSuburbs() {
        return suburbs;
    }

    public City(int cityID, String name, ArrayList<Suburb> suburbs) {
        CityID = cityID;
        Name = name;
        this.suburbs = suburbs;
    }

    @Override
    public String toString() {
        return "City{" +
                "CityID=" + CityID +
                ", Name='" + Name + '\'' +
                ", suburbs=" + suburbs +
                '}';
    }
}
