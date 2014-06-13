/*
 * Michael Burton Copyright (c) 2014.
 */

package net.azurewebsites.specialtopicfinal.app.BusinessObjects;

import java.util.ArrayList;

/**
 * Created by Mike on 06/05/2014.
 */
public class Vehicle extends Product
{
    String vehicleType;
    int numberOfSeats;
    String color;
    String make;
    String model;
    String subModel;
    double litres;
    String transmission;
    String engine;
    int kilometres;
    String features;

    public String getVehicleType() {
        return vehicleType;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public String getColor() {
        return color;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getSubModel() {
        return subModel;
    }

    public double getLitres() {
        return litres;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getEngine() {
        return engine;
    }

    public int getKilometres() {
        return kilometres;
    }

    public String getFeatures() {
        return features;
    }

    public Vehicle(int productID, int categoryID, String defaultImage, String description, boolean isCurrent, String name, int parentProductID, double price, int stockCount, ArrayList<Image> images, String vehicleType, int numberOfSeats, String color, String model, String make, String subModel, double litres, String transmission, int kilometres, String engine, String features) {

        super(productID, categoryID, description, isCurrent, name, parentProductID, price, stockCount, images);
        this.vehicleType = vehicleType;
        this.numberOfSeats = numberOfSeats;
        this.color = color;
        this.model = model;
        this.make = make;
        this.subModel = subModel;
        this.litres = litres;
        this.transmission = transmission;
        this.kilometres = kilometres;
        this.engine = engine;
        this.features = features;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleType='" + vehicleType + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", color='" + color + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", subModel='" + subModel + '\'' +
                ", litres=" + litres +
                ", transmission='" + transmission + '\'' +
                ", engine='" + engine + '\'' +
                ", kilometres=" + kilometres +
                ", features='" + features + '\'' +
                '}';
    }
}
