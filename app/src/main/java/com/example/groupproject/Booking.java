package com.example.groupproject;
import java.util.ArrayList;

public class Booking {
    private String reference;
    private ArrayList<Passenger> passengers = new ArrayList<Passenger>();

    private String travelclass;
    private String flight;
    private final int totalprice;


    Booking(String reference, String travelclass, String flight, int totalprice){

        this.reference = reference;

        this.travelclass = travelclass;
        this.flight = flight;
        this.totalprice = totalprice;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }



    public int getTotalprice() {
        return totalprice;
    }

    public String getFlight() {
        return flight;
    }

    public String getReference() {
        return reference;
    }

    public String getTravelclass() {
        return travelclass;
    }

    public void addPassenger(Passenger newpassenger){
        passengers.add(newpassenger);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "reference='" + reference + '\'' +
                ", passengers=" + passengers +
                ", travelclass='" + travelclass + '\'' +
                ", flight='" + flight + '\'' +
                ", totalprice=" + totalprice +
                '}';
    }


}
