package com.example.groupproject;
public class Plane {
    private String registration;
    private String aircraftType;
    private int economy;
    private int business;
    private int first;
    private int total;

    Plane(String registration, String aircraftType, int economy, int business, int first){

        this.registration = registration;
        this.aircraftType = aircraftType;
        this.economy = economy;
        this.business = business;
        this.first = first;
        this.total = economy + business + first;
    }

    public int getBusiness() {
        return business;
    }

    public int getEconomy() {
        return economy;
    }

    public int getFirst() {
        return first;
    }

    public int getTotal() {
        return total;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public String getRegistration() {
        return registration;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "registration='" + registration + '\'' +
                ", aircraftType='" + aircraftType + '\'' +
                ", economy=" + economy +
                ", business=" + business +
                ", first=" + first +
                ", total=" + total +
                '}';
    }
}
