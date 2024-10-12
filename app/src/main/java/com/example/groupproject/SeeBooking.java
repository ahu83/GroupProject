package com.example.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SeeBooking extends AppCompatActivity {
    TextView depart, returnflight, booking, enterdate;
    Button changeR, changeD, changeReturn, changeDeparture;
    Booking seebooking;
    String departflightid, returnflightid, reference;
    EditText date;
    boolean isreturn;
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.changeD){
                changeDeparture.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                enterdate.setVisibility(View.VISIBLE);
                changeReturn.setVisibility(View.GONE);


            } else if (v.getId() == R.id.changeR){
                changeDeparture.setVisibility(View.GONE);
                date.setVisibility(View.VISIBLE);
                enterdate.setVisibility(View.VISIBLE);
                changeReturn.setVisibility(View.VISIBLE);
            } else if (v.getId() == R.id.changeReturn){
                String changedate = date.getText().toString();

                Intent i = new Intent(SeeBooking.this, ChangeBooking.class);
                i.putExtra("changedate", changedate);
                i.putExtra("reference", reference);
                i.putExtra("flightid", returnflightid);
                boolean changereturn = true;
                i.putExtra("changereturn", changereturn);
                startActivity(i);



            } else if (v.getId() == R.id.changeDepart){
                String changedate = date.getText().toString();
                Intent i = new Intent(SeeBooking.this, ChangeBooking.class);
                i.putExtra("reference", reference);
                i.putExtra("changedate", changedate);
                i.putExtra("flightid", departflightid);
                boolean changereturn = false;
                i.putExtra("changereturn", changereturn);
                startActivity(i);

            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_see_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        depart = findViewById(R.id.seeDepartflight);
        returnflight  = findViewById(R.id.seeReturnFlight);
        booking = findViewById(R.id.seebookingdetails);
        enterdate = findViewById(R.id.enterthedatetext);
        changeR = findViewById(R.id.changeR);
        changeD = findViewById(R.id.changeD);
        changeReturn = findViewById(R.id.changeReturn);
        changeDeparture = findViewById(R.id.changeDepart);
        date = findViewById(R.id.changeDate);


        enterdate.setVisibility(View.GONE);
        returnflight.setVisibility(View.INVISIBLE);
        changeR.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);
        changeReturn.setVisibility(View.GONE);
        changeDeparture.setVisibility(View.GONE);

        changeR.setOnClickListener(clickListener);
        changeD.setOnClickListener(clickListener);
        changeReturn.setOnClickListener(clickListener);
        changeDeparture.setOnClickListener(clickListener);


        Intent intent = getIntent();
        reference = intent.getStringExtra("reference");


        for (Booking booking : MainActivity.bookings){
            if (reference.equals(booking.getReference())){
                if (booking instanceof Return ){
                    seebooking = (Return) booking;
                    isreturn = true;
                    returnflight.setVisibility(View.VISIBLE);
                    changeR.setVisibility(View.VISIBLE);
                } else {
                    seebooking = booking;
                    isreturn = false;
                }
            }
        }

        booking.setText(seebooking.toString());

        for (Flight flight : MainActivity.flights){
            if (seebooking.getFlight().equals(flight.getFlightId())){
                depart.setText(flight.toString());
                departflightid = flight.getFlightId();
            }
            if(isreturn == true){
                if(seebooking instanceof Return) {
                    if (((Return) seebooking).getReturnflight().equals(flight.getFlightId())) {
                        returnflight.setText(flight.toString());
                        returnflightid = flight.getFlightId();
                    }
                }
            }
        }




    }
}