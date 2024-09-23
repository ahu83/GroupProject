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

public class ManageBooking extends AppCompatActivity {

    Button searchbutton;
    EditText reference;
    TextView errormessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        System.out.println(MainActivity.bookings);

        searchbutton = findViewById(R.id.bookingsearchbutton);
        reference = findViewById(R.id.bookingReferenceInput);
        errormessage = findViewById(R.id.managebookingerror);

        errormessage.setVisibility(View.GONE);
        searchbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean exists = false;

                String inputreference = reference.getText().toString();

                for (Booking booking : MainActivity.bookings){
                    if (inputreference.equals(booking.getReference())){
                        exists = true;
                        errormessage.setVisibility(View.GONE);
                    }
                }

                if (exists == false){
                    errormessage.setVisibility(View.VISIBLE);
                } if (exists == true){
                    Intent i = new Intent(ManageBooking.this, SeeBooking.class);
                    i.putExtra("reference", inputreference);
                    startActivity(i);
                }
            }
        });




    }


}