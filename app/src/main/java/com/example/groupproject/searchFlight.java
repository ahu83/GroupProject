package com.example.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class searchFlight extends AppCompatActivity {
    ListView displayFlight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_flight);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        displayFlight = findViewById(R.id.displayFlight);
        Intent intent = getIntent();

        ArrayList<String> flightlist = intent.getStringArrayListExtra("match") ;
        ArrayList<String> displaystring = new ArrayList<>();
        for (String flightid : flightlist){
            for (Flight flight : MainActivity.flights){
                if (flightid.equals(flight.getFlightId())){
                    displaystring.add(flight.toString());
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, displaystring);
        displayFlight.setAdapter(adapter);

    }
}