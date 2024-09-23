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

public class paymentComplete extends AppCompatActivity {
    Button done;
    TextView bookingdetails, bookingreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_complete);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        done = findViewById(R.id.donebutton);
        bookingdetails = findViewById(R.id.finalconfirmation);
        bookingreference = findViewById(R.id.bookingreference);

        Intent intent = getIntent();
        String reference = intent.getStringExtra("reference");

        bookingreference.setText(String.format("your booking reference is %s", reference));

        MainActivity.exportBookings(this);
        MainActivity.exportReturns(this);
        MainActivity.exportPassengers(this);

        for (Booking booking : MainActivity.bookings){
            if (booking.getReference().equals(reference)){
                bookingdetails.setText(booking.toString());
            }
        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(paymentComplete.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}