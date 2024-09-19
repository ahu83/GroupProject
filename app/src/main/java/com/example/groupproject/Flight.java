package com.example.groupproject;
import java.io.Serializable;
import java.util.ArrayList;

public class Flight implements Serializable {
    private String flightNumber;
    private String flightId;
    private int economyavail, businessavail, firstavail;
    private int totalecon, totalbusiness, totalfirst;
    private ArrayList<String> bookingreferences = new ArrayList<>();
    private String planeRegistration;
    private int econprice;
    private int businessprice;
    private int firstprice;
    private String departure;
    private String departureTime;
    private String departureDate;
    private String duration;
    private String arrival;
    private String arrivalTime;
    private String arrivalDate;
    Flight(String flightNumber, String flightId,  String planeRegistration, int econprice, int businessprice, int firstprice, String departure, String departureTime,
    String departureDate, String duration, String arrival, String arrivalTime, String arrivalDate){

        this.flightNumber = flightNumber;
        this.flightId = flightId;
        this.planeRegistration = planeRegistration;
        this.econprice = econprice;
        this.businessprice = businessprice;
        this.firstprice = firstprice;
        this.departure = departure;
        this.departureTime = departureTime;
        this.departureDate = departureDate;
        this.duration = duration;
        this.arrival = arrival;
        this.arrivalTime = arrivalTime;
        this.arrivalDate = arrivalDate;
    }
    public void generateTotalSeats(Plane plane){
        this.totalecon = plane.getEconomy();
        this.totalbusiness =  plane.getBusiness();
        this.totalfirst = plane.getFirst();

        this.economyavail = this.totalecon;
        this.businessavail = this.totalbusiness;
        this.firstavail = this.totalfirst;
    }

    public void addBooking(Booking booking){
        if (booking.getTravelclass().equals("Economy")){
            this.economyavail = this.economyavail - booking.getPassengers().size();
            System.out.println(booking.getPassengers().size());

        } else if (booking.getTravelclass().equals("Business")){
            this.businessavail = this.businessavail - booking.getPassengers().size();

        } else if (booking.getTravelclass().equals("First")){
            this.firstavail = this.firstavail - booking.getPassengers().size();

        }

        this.bookingreferences.add(booking.getReference());
    }

    public void removeBooking(Booking booking){
        if (booking.getTravelclass().equals("Economy")){
            this.economyavail = this.economyavail + booking.getPassengers().size();

        } else if (booking.getTravelclass().equals("Business")){
            this.businessavail = this.businessavail + booking.getPassengers().size();

        } else if (booking.getTravelclass().equals("First")){
            this.firstavail = this.firstavail + booking.getPassengers().size();

        }

        this.bookingreferences.remove(booking.getReference());
    }

    public ArrayList<String> getBookingreferences() {
        return bookingreferences;
    }

    public int getBusinessavail() {
        return businessavail;
    }

    public int getBusinessprice() {
        return businessprice;
    }

    public int getEconomyavail() {
        return economyavail;
    }

    public int getEconprice() {
        return econprice;
    }

    public int getFirstavail() {
        return firstavail;
    }

    public int getFirstprice() {
        return firstprice;
    }

    public String getFlightId() {
        return flightId;
    }

    public int getTotalbusiness() {
        return totalbusiness;
    }

    public int getTotalecon() {
        return totalecon;
    }

    public int getTotalfirst() {
        return totalfirst;
    }

    public String getArrival() {
        return arrival;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getDuration() {
        return duration;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getPlaneRegistration() {
        return planeRegistration;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", flightId=" + flightId +
                ", economyavail=" + economyavail +
                ", businessavail=" + businessavail +
                ", firstavail=" + firstavail +
                ", totalecon=" + totalecon +
                ", totalbusiness=" + totalbusiness +
                ", totalfirst=" + totalfirst +
                ", bookingreferences=" + bookingreferences + "\n" +
                ", planeRegistration='" + planeRegistration + '\'' +
                ", econprice=" + econprice +
                ", businessprice=" + businessprice +
                ", firstprice=" + firstprice +
                ", departure='" + departure + '\'' +
                ", departureTime='" + departureTime + '\'' + "\n" +
                ", departureDate='" + departureDate + '\'' +
                ", duration='" + duration + '\'' +
                ", arrival='" + arrival + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                '}';
    }
}
