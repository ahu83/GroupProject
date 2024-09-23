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

import java.util.Random;

public class Payment extends AppCompatActivity {
    TextView bookingdetails;
    Button pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        System.out.println(MainActivity.bookings);


        bookingdetails = findViewById(R.id.bookingDetails);
        pay = findViewById(R.id.paybutton);

        Intent intent = getIntent();
        Booking booking;
        boolean isreturn = intent.getBooleanExtra("isreturn", false);
        if (isreturn == true){
            booking = (Return) intent.getSerializableExtra("booking");
        } else {
            booking =(Booking) intent.getSerializableExtra("booking");
        }
        bookingdetails.setText(booking.toString());

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean exists = true;
                String reference = "";
                while (exists == true) {
                    reference = referenceGenerator();
                    exists = false;
                    for (Booking ticket : MainActivity.bookings) {
                        if (reference.equals(ticket.getReference())){
                            exists = true;
                        }
                    }


                }
                booking.setReference(reference);
                MainActivity.bookings.add(booking);


                exists = true;
                Intent i = new Intent(Payment.this, paymentComplete.class);
                i.putExtra("reference", reference);
                startActivity(i);
            }
        });


    }

    public static String referenceGenerator(){
        String reference = "";
        String randomcharacters = "1234567890QWERTYUIOPASDFGHJKLZXCVBNM";
        Random rand = new Random();
        int i;
        for (i = 0; i < 6; i++){
            reference = reference + randomcharacters.charAt(rand.nextInt(36));
        }
        return reference;
    }
}