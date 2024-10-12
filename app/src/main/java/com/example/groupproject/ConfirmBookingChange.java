package com.example.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Method;

public class ConfirmBookingChange extends AppCompatActivity {
    TextView flightdetails, pricechangetext;
    Button pay;
    Flight newflight = null;
    Flight oldflight = null;
    Booking currentbooking = null;
    int pricechange = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirm_booking_change);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        flightdetails = findViewById(R.id.newflightdetails);
        pricechangetext = findViewById(R.id.pricechange);
        pay = findViewById(R.id.newpay);



        Intent intent = getIntent();
        String reference = intent.getStringExtra("reference");
        final String currentflightid = intent.getStringExtra("flightid");
        final boolean changereturn = intent.getBooleanExtra("changereturn", false);
        String changedate = intent.getStringExtra("changedate");
        final String newflightid = intent.getStringExtra("newflightid");






        for (Booking booking : MainActivity.bookings){
            if (reference.equals(booking.getReference())){
                if(booking instanceof Return){
                    currentbooking =(Return) booking;
                } else{
                    currentbooking = booking;
                }

            }
        }

        int pax = currentbooking.getPassengers().size();

        String travelclass = currentbooking.getTravelclass();


        for (Flight flight: MainActivity.flights){
            if (currentflightid.equals(flight.getFlightId())){
                oldflight = flight;
                if (travelclass.equals("Economy")){
                    pricechange = pricechange - (flight.getEconprice()*pax);
                } else if (travelclass.equals("Business")){
                    pricechange = pricechange - (flight.getBusinessprice()*pax);
                } else if (travelclass.equals("First")){
                    pricechange = pricechange - (flight.getFirstprice()*pax);
                }
            }
        }


        for (Flight flight: MainActivity.flights){
            if (newflightid.equals(flight.getFlightId())){
                newflight = flight;
                if (travelclass.equals("Economy")){
                    pricechange = pricechange + (flight.getEconprice()*pax);
                } else if (travelclass.equals("Business")){
                    pricechange = pricechange + (flight.getBusinessprice()*pax);
                } else if (travelclass.equals("First")){
                    pricechange = pricechange + (flight.getFirstprice()*pax);
                }
            }
        }

        flightdetails.setText(newflight.toStringdisplay());
        String changepricestring;


        if (pricechange > 0){
            changepricestring = String.format("You will have to pay $%s", pricechange);
            pricechangetext.setText(changepricestring);
            pay.setText("Pay");
        } else if (pricechange < 0) {
            int positivepricechange = pricechange * -1;
            changepricestring = String.format("You will be refunded $%s to your original payment method.", positivepricechange);
            pricechangetext.setText(changepricestring);
            pay.setText("Confirm");
        } else {
            changepricestring = String.format("There are no changes to the original price", pricechange);
            pricechangetext.setText(changepricestring);
            pay.setText("Confirm");
        }

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldflight.removeBooking(currentbooking);
                newflight.addBooking(currentbooking);
                currentbooking.changeTotalprice(pricechange);
                if(currentbooking instanceof Return){
                    if (changereturn == true){
                        ((Return) currentbooking).setReturnflight(newflightid);
                    } else {
                        ((Return) currentbooking).setFlight(newflightid);
                    }
                } else{
                    currentbooking.setFlight(newflightid);
                }
                Intent i = new Intent(ConfirmBookingChange.this, SeeBooking.class);
                i.putExtra("reference", reference);
                startActivity(i);

            }
        });
    }
}