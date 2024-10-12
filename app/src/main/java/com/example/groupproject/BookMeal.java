package com.example.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class BookMeal extends AppCompatActivity {
    ListView meallist;
    TextView personnumber;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_meal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        meallist = findViewById(R.id.mealList);
        personnumber = findViewById(R.id.mealPerson);

        Intent intent = getIntent();
        Booking booking;
        boolean isreturn = intent.getBooleanExtra("isreturn", false);
        if (isreturn == true){
            booking = (Return) intent.getSerializableExtra("booking");
        } else {
            booking =(Booking) intent.getSerializableExtra("booking");
        }
        ArrayList<Passenger> passengers = booking.getPassengers();
        ArrayList<String> passengerNames = new ArrayList<>();
        for (Passenger passenger: passengers){
            String name = passenger.getFirstName() + " " + passenger.getLastName();
            passengerNames.add(name);
        }
        ArrayList<String> mealstring = new ArrayList<>();

        for (Meal meal: MainActivity.meals){
            mealstring.add(meal.toString());

        }
        personnumber.setText("Select a meal for " + passengerNames.get(0));


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, mealstring);
        meallist.setAdapter(adapter);
        meallist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (i < passengers.size()-1) {
                    booking.changeTotalprice(-(MainActivity.meals.get(passengers.get(i).getMeal()).getPrice()));
                    booking.addMeal(i, position);

                    booking.changeTotalprice(MainActivity.meals.get(position).getPrice());
                    i++;
                    personnumber.setText("Select a meal for " + passengerNames.get(i));
                } else {
                    booking.changeTotalprice(-(MainActivity.meals.get(passengers.get(i).getMeal()).getPrice()));
                    booking.addMeal(i, position);
                    System.out.println("previous meal "+ -(MainActivity.meals.get(passengers.get(i).getMeal()).getPrice()));
                    System.out.println("new meal " + MainActivity.meals.get(position).getPrice());

                    booking.changeTotalprice(MainActivity.meals.get(position).getPrice());
                    Intent i = new  Intent(BookMeal.this, Payment.class);
                    i.putExtra("booking", booking);
                    i.putExtra("isreturn", isreturn);
                    startActivity(i);

                }
            }
        });

    }
}