package com.example.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ChangeBooking extends AppCompatActivity {
    ListView displayFlight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        displayFlight = findViewById(R.id.changeflightlist);

        Intent intent = getIntent();
        String reference = intent.getStringExtra("reference");
        String currentflightid = intent.getStringExtra("flightid");
        boolean changereturn = intent.getBooleanExtra("changereturn", false);
        String changedate = intent.getStringExtra("changedate");

        Flight currentflight = searchflight(currentflightid);
        Booking currentbooking = null;
        System.out.println(currentflightid);

        for (Booking booking : MainActivity.bookings){
            if (reference.equals(booking.getReference())){
                currentbooking = booking;
            }
        }

        ArrayList<String> flightlist = search(currentflight, currentbooking, changedate);
        ArrayList<String> displaystring = new ArrayList<>();



        ArrayList<String> matches = search(currentflight, currentbooking, changedate);
        for (String flightid : flightlist){
            for (Flight flight : MainActivity.flights){
                if (flightid.equals(flight.getFlightId())){
                    displaystring.add(flight.toString());
                }
            }
        }


        //display the flights
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, displaystring);
        displayFlight.setAdapter(adapter);

        displayFlight.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Intent i = new Intent(ChangeBooking.this, ConfirmBookingChange.class);
                i.putExtra("changedate", changedate);
                i.putExtra("reference", reference);
                i.putExtra("flightid", currentflightid);
                i.putExtra("changereturn", changereturn);
                i.putExtra("newflightid", matches.get(position));
                startActivity(i);


            }
        });
    }







    public Flight searchflight(String id){
        Flight bookedflight = null;
        for (Flight flight : MainActivity.flights){
            if (id.equals(flight.getFlightId())){
                bookedflight = flight;
            }

        }
        return bookedflight;

    }
    public ArrayList<String> search(Flight bookedflight, Booking currentbooking, String changedate){
        ArrayList<String> matches = new ArrayList<>();
        int passengers = currentbooking.getPassengers().size();

        for (Flight flight : MainActivity.flights){
            if ((bookedflight.getDeparture().equals(flight.getDeparture())) && (bookedflight.getArrival().equals(flight.getArrival()))
                    && (changedate.equals(flight.getDepartureDate()))){
                if (currentbooking.getTravelclass().equals("Economy")){
                    if(passengers <= flight.getEconomyavail()){
                        matches.add(flight.getFlightId());
                    }
                }else if (currentbooking.getTravelclass().equals("Business")){
                    if(passengers <= flight.getBusinessavail()){
                        matches.add(flight.getFlightId());
                    }
                }else if (currentbooking.getTravelclass().equals("First")){
                    if(passengers <= flight.getFirstavail()){
                        matches.add(flight.getFlightId());
                    }
                }
            }
        }

        return matches;
    }
}