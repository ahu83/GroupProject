package com.example.groupproject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Booking> bookings;
    public static ArrayList<Plane> planes;
    public static ArrayList<Flight> flights;
    public static ArrayList<Airport> airports;

    EditText depart, arrival, pax, traveldate, returndate;
    Spinner travelclass;
    String selectedclass = "Economy";
    Button searchbutton;
    CheckBox returnstatus;
    TextView returntext;
    Boolean isreturn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.travelClass), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        depart = findViewById(R.id.departAirport);
        arrival = findViewById(R.id.arrivalAirport);
        pax = findViewById(R.id.pax);
        travelclass = (Spinner)findViewById(R.id.spinner);
        traveldate = findViewById(R.id.travelDate);
        searchbutton = findViewById(R.id.searchButton);
        returndate = findViewById(R.id.arrDate);
        returnstatus = findViewById(R.id.returnBox);
        returntext = findViewById(R.id.textView5);

        returndate.setVisibility(View.GONE);
        returntext.setVisibility(View.GONE);
        isreturn = false;

        returnstatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked == true){
                    returndate.setVisibility(View.VISIBLE);
                    returntext.setVisibility(View.VISIBLE);
                    isreturn = true;
                } else {
                    returndate.setVisibility(View.GONE);
                    returntext.setVisibility(View.GONE);
                    isreturn = false;
                }
            }
        });

        String[] travelclasses = {"Economy", "Business", "First"};
        ArrayAdapter<String> classadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, travelclasses);
        classadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        travelclass.setAdapter(classadapter);

        travelclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                travelclass.setSelection(position);
                selectedclass = travelclass.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                travelclass.setSelection(0);
            }

        });
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isreturn == false) {
                    try {
                        ArrayList<String> match = search();
                        Intent i = new Intent(MainActivity.this, searchFlight.class);
                        i.putStringArrayListExtra("match", match);
                        i.putExtra("isreturn", isreturn);
                        startActivity(i);
                    } catch (Exception e) {
                        Intent intent = new Intent(MainActivity.this, NoFlights.class);
                        startActivity(intent);
                    }
                } else {
                    try {
                        ArrayList<String> match = search();
                        ArrayList<String> returnmatch = searchReturn();
                        Intent i = new Intent(MainActivity.this, searchFlight.class);
                        int passengers = Integer.parseInt(String.valueOf(pax.getText()));

                        i.putExtra("isreturn", isreturn);
                        i.putStringArrayListExtra("match", match);
                        i.putStringArrayListExtra("returnmatch", returnmatch);
                        i.putExtra("pax", passengers);
                        startActivity(i);
                    } catch (Exception e) {
                        Intent intent = new Intent(MainActivity.this, NoFlights.class);
                        startActivity(intent);
                    }
                }

            }
        };

        searchbutton.setOnClickListener(clickListener);


        //import all the data
        try {
            airports = importAirports(this, R.raw.airports);
            planes = importPlanes(this, R.raw.planes);
            flights = importFlights(this, R.raw.flights, planes);
            bookings = importBookings(this, R.raw.bookings);
            importReturnBookings(this, R.raw.returns, bookings);
            importPassengers(this, R.raw.passengers, bookings);
            addBookingToFlight(bookings, flights);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    public static void addBookingToFlight(ArrayList<Booking> bookings, ArrayList<Flight> flights){
        //add the booking to their respective flights
        for (Booking booking : bookings){
            if ( !(booking instanceof Return)) {
                for (Flight flight : flights){
                    if (booking.getFlight().equals(flight.getFlightId())){
                        flight.addBooking(booking);
                    }
                }
            } if ( booking instanceof Return) {
                Return returnBooking = (Return) booking;
                for (Flight flight : flights){
                    if (returnBooking.getFlight().equals(flight.getFlightId())){
                        flight.addBooking(booking);
                    }
                    if (returnBooking.getReturnflight().equals(flight.getFlightId())){
                        flight.addBooking(booking);
                    }

                }
            }
        }
    }

    public static ArrayList<Booking> importBookings(Context context, int rawResourceId) throws IOException {
        Resources resources = context.getResources();
        ArrayList<Booking> bookings = new ArrayList<>();
        try (InputStream inputStream = resources.openRawResource(rawResourceId);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : parser) {
                String reference = record.get("Reference");
                String travelClass = record.get("TravelClass");
                String flight = record.get("Flight");
                int totalPrice = Integer.parseInt(record.get("TotalPrice"));

                Booking booking = new Booking(reference, travelClass, flight, totalPrice);
                bookings.add(booking);
            }
        }


        return bookings;
    }
    public static void importReturnBookings(Context context, int rawResourceId, ArrayList<Booking> bookings) throws IOException {
        Resources resources = context.getResources();
        try (InputStream inputStream = resources.openRawResource(rawResourceId);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : parser) {
                String reference = record.get("Reference");
                String travelClass = record.get("TravelClass");
                String flight = record.get("Flight");
                int totalPrice = Integer.parseInt(record.get("TotalPrice"));
                String returnFlight = record.get("ReturnFlight");

                Return booking = new Return(reference, travelClass, flight, totalPrice, returnFlight);
                bookings.add(booking);
            }
        }
    }

    public static void importPassengers(Context context, int rawResourceId, ArrayList<Booking> bookings) throws IOException {
        Resources resources = context.getResources();
        try (InputStream inputStream = resources.openRawResource(rawResourceId);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : parser) {
                String reference = record.get("BookingReference");
                String firstName = record.get("FirstName");
                String lastName = record.get("LastName");
                String gender = record.get("Gender");
                String dob = record.get("DOB");
                String nationality = record.get("Nationality");

                Passenger passenger = new Passenger(firstName, lastName, gender, dob, nationality);
                for (Booking booking : bookings){
                    if(booking.getReference().equals(reference)){
                        booking.addPassenger(passenger);
                    }
                }
            }
        }
    }
    public static ArrayList<Plane> importPlanes(Context context, int rawResourceId) throws IOException {
        ArrayList<Plane> planes = new ArrayList<>();
        Resources resources = context.getResources();
        try (InputStream inputStream = resources.openRawResource(rawResourceId);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : parser) {
                String registration = record.get("Registration");
                String aircraftType = record.get("AircraftType");
                int economy = Integer.parseInt(record.get("Economy"));
                int business = Integer.parseInt(record.get("Business"));
                int first = Integer.parseInt(record.get("First"));

                Plane plane = new Plane(registration, aircraftType, economy, business, first);
                planes.add(plane);
            }
        }

        return planes;
    }
    public static ArrayList<Airport> importAirports(Context context, int rawResourceId) throws IOException {
        ArrayList<Airport> airports = new ArrayList<>();
        Resources resources = context.getResources();
        try (InputStream inputStream = resources.openRawResource(rawResourceId);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : parser) {
                String code = record.get("Code");
                String name = record.get("Name");
                String city = record.get("City");
                String country = record.get("Country");
                int timezone = Integer.parseInt(record.get("Timezone"));

                Airport airport = new Airport(code, name, city, country, timezone);
                airports.add(airport);
            }
        }

        return airports;
    }
    public static ArrayList<Flight> importFlights(Context context, int rawResourceId, ArrayList<Plane> planes) throws IOException {
        ArrayList<Flight> flights = new ArrayList<>();
        Resources resources = context.getResources();
        try (InputStream inputStream = resources.openRawResource(rawResourceId);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : parser) {
                String flightNumber = record.get("FlightNumber");
                String flightId = record.get("FlightId");
                String planeRegistration = record.get("PlaneRegistration");
                int econPrice = Integer.parseInt(record.get("EconPrice"));
                int businessPrice = Integer.parseInt(record.get("BusinessPrice"));
                int firstPrice = Integer.parseInt(record.get("FirstPrice"));
                String departure = record.get("Departure");
                String departureTime = record.get("DepartureTime");
                String departureDate = record.get("DepartureDate");
                String duration = record.get("Duration");
                String arrival = record.get("Arrival");
                String arrivalTime = record.get("ArrivalTime");
                String arrivalDate = record.get("ArrivalDate");

                Flight flight = new Flight(flightNumber, flightId, planeRegistration, econPrice, businessPrice, firstPrice, departure, departureTime, departureDate, duration, arrival, arrivalTime, arrivalDate);
                flights.add(flight);
            }
        }

        for (Flight flight : flights){
            for (Plane plane : planes) {
                if (flight.getPlaneRegistration().equals(plane.getRegistration())){
                    flight.generateTotalSeats(plane);
                }
            }
        }

        return flights;
    }

    public static void exportBookings(String bookingsFilePath, ArrayList<Booking> bookings) throws IOException {
        try (FileWriter writer = new FileWriter(bookingsFilePath);
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Reference", "TravelClass", "Flight", "TotalPrice"))) {

            for (Booking booking : bookings) {
                if ( !(booking instanceof Return)) {
                    printer.printRecord(
                            booking.getReference(),
                            booking.getTravelclass(),
                            booking.getFlight(),
                            booking.getTotalprice()
                    );
                }
            }
        }
    }

    public static void exportReturns(String returnsFilePath, ArrayList<Booking> bookings) throws IOException {
        try (FileWriter writer = new FileWriter(returnsFilePath);
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Reference", "TravelClass", "Flight", "TotalPrice", "ReturnFlight"))) {

            for (Booking booking : bookings) {
                if (booking instanceof Return) {
                    Return returnBooking = (Return) booking;
                    printer.printRecord(
                            returnBooking.getReference(),
                            returnBooking.getTravelclass(),
                            returnBooking.getFlight(),
                            returnBooking.getTotalprice(),
                            returnBooking.getReturnflight()
                    );
                }
            }
        }
    }
    public static void exportPassengers(String passengersFilePath, ArrayList<Booking> bookings) throws IOException {
        try (FileWriter writer = new FileWriter(passengersFilePath);
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("BookingReference", "FirstName", "LastName", "Gender", "DOB", "Nationality"))) {

            for (Booking booking : bookings) {
                for (Passenger passenger : booking.getPassengers()) {
                    printer.printRecord(
                            booking.getReference(),
                            passenger.getFirstName(),
                            passenger.getLastName(),
                            passenger.getGender(),
                            passenger.getDOB(),
                            passenger.getNationality()
                    );
                }
            }
        }
    }

    public static void exportPlanes(String planesFilePath, ArrayList<Plane> planes) throws IOException {
        try (FileWriter writer = new FileWriter(planesFilePath);
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Registration", "AircraftType", "Economy", "Business", "First"))) {

            for (Plane plane : planes) {
                printer.printRecord(
                        plane.getRegistration(),
                        plane.getAircraftType(),
                        plane.getEconomy(),
                        plane.getBusiness(),
                        plane.getFirst()
                );
            }
        }
    }

    public static void exportAirports(String airportsFilePath, ArrayList<Airport> airports) throws IOException {
        try (FileWriter writer = new FileWriter(airportsFilePath);
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Code", "Name", "City", "Country", "Timezone"))) {

            for (Airport airport : airports) {
                printer.printRecord(
                        airport.getCode(),
                        airport.getName(),
                        airport.getCity(),
                        airport.getCountry(),
                        airport.getTimezone()
                );
            }
        }
    }

    public static void exportFlights(String flightsFilePath, ArrayList<Flight> flights) throws IOException {
        try (FileWriter writer = new FileWriter(flightsFilePath);
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("flightNumber", "flightId", "planeRegistration", "econPrice", "businessPrice", "firstPrice", "departure", "departureTime", "departureDate", "duration", "arrival", "arrivalTime", "arrivalDate"))) {

            for (Flight flight : flights) {
                printer.printRecord(
                        flight.getFlightNumber(),
                        flight.getFlightId(),
                        flight.getPlaneRegistration(),
                        flight.getEconprice(),
                        flight.getBusinessprice(),
                        flight.getFirstprice(),
                        flight.getDeparture(),
                        flight.getDepartureTime(),
                        flight.getDepartureDate(),
                        flight.getDuration(),
                        flight.getArrival(),
                        flight.getArrivalTime(),
                        flight.getArrivalDate()
                );
            }
        }
    }

    public ArrayList<String> search(){
        ArrayList<String> matches = new ArrayList<>();
        int passengers = Integer.parseInt(String.valueOf(pax.getText()));

        for (Flight flight : flights){
            if ((depart.getText().toString().equals(flight.getDeparture())) && (arrival.getText().toString().equals(flight.getArrival()))
            && (traveldate.getText().toString().equals(flight.getDepartureDate()))){
                if (selectedclass.equals("Economy")){
                    if(passengers <= flight.getEconomyavail()){
                        matches.add(flight.getFlightId());
                    }
                }else if (selectedclass.equals("Business")){
                    if(passengers <= flight.getBusinessavail()){
                        matches.add(flight.getFlightId());
                    }
                }else if (selectedclass.equals("First")){
                    if(passengers <= flight.getFirstavail()){
                        matches.add(flight.getFlightId());
                    }
                }
            }
        }

        return matches;
    }

    public ArrayList<String> searchReturn(){
        ArrayList<String> returnmatches = new ArrayList<>();
        int passengers = Integer.parseInt(String.valueOf(pax.getText()));

        for (Flight flight : flights){
            if ((arrival.getText().toString().equals(flight.getDeparture())) && (depart.getText().toString().equals(flight.getArrival()))
                    && (returndate.getText().toString().equals(flight.getDepartureDate()))){
                if (selectedclass.equals("Economy")){
                    if(passengers <= flight.getEconomyavail()){
                        returnmatches.add(flight.getFlightId());
                    }
                }else if (selectedclass.equals("Business")){
                    if(passengers <= flight.getBusinessavail()){
                        returnmatches.add(flight.getFlightId());
                    }
                }else if (selectedclass.equals("First")){
                    if(passengers <= flight.getFirstavail()){
                        returnmatches.add(flight.getFlightId());
                    }
                }
            }
        }

        return returnmatches;
    }

}