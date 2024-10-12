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

        //find the listview and assign a variable
        displayFlight = findViewById(R.id.displayFlight);

        //get the variables passed from the previous view
        Intent intent = getIntent();
        ArrayList<String> flightlist = intent.getStringArrayListExtra("match") ;
        boolean isreturn = intent.getBooleanExtra("isreturn", false);
        int pax = intent.getIntExtra("pax", 0);
        String travelclass = intent.getStringExtra("travelclass");

        ArrayList<String> returnmatch = intent.getStringArrayListExtra("returnmatch") ;



        ArrayList<String> displaystring = new ArrayList<>();

        //get the flight object using the matched flight ids
        for (String flightid : flightlist){
            for (Flight flight : MainActivity.flights){
                if (flightid.equals(flight.getFlightId())){
                    displaystring.add(flight.toStringdisplay());
                }
            }
        }


        //display the flights
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, displaystring);
        displayFlight.setAdapter(adapter);

        displayFlight.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                String departFlight = flightlist.get(position);
                if(isreturn == true){
                    Intent i = new Intent(searchFlight.this, SelectReturnFlight.class);
                    i.putExtra("isreturn", isreturn);
                    i.putExtra("departflight", departFlight);
                    i.putStringArrayListExtra("match", flightlist);
                    i.putStringArrayListExtra("returnmatch", returnmatch);
                    i.putExtra("pax", pax);
                    i.putExtra("travelclass", travelclass);
                    startActivity(i);
                } else {
                    Intent i = new Intent(searchFlight.this, PassengerDetails.class);
                    String returnFlight = "";
                    i.putExtra("returnflight", returnFlight);
                    i.putExtra("isreturn", isreturn);
                    i.putExtra("departflight", departFlight);
                    i.putStringArrayListExtra("match", flightlist);
                    i.putExtra("pax", pax);
                    i.putExtra("travelclass", travelclass);
                    startActivity(i);
                }


            }
        });

    }
}