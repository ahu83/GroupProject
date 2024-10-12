package com.example.groupproject;
import java.io.Serializable;
import java.util.ArrayList;

public class Booking implements Serializable {
    private String reference;
    private ArrayList<Passenger> passengers = new ArrayList<Passenger>();

    private String travelclass;
    private String flight;
    private  int totalprice;


    Booking(String reference, String travelclass, String flight, int totalprice){

        this.reference = reference;

        this.travelclass = travelclass;
        this.flight = flight;
        this.totalprice = totalprice;
    }
    Booking(String travelclass, String flight, int totalprice){

        this.reference = "";

        this.travelclass = travelclass;
        this.flight = flight;
        this.totalprice = totalprice;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }
    public void changeTotalprice(int totalprice) {
        this.totalprice = this.totalprice +  totalprice;
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
    public void addPassenger(ArrayList<Passenger> newpassenger){
        this.passengers = newpassenger;
    }
    public void setReference(String reference){
        this.reference = reference;
    }

    public void setFlight(String flight) {
        this.flight = flight;
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
