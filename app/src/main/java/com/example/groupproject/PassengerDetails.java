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

import java.util.ArrayList;

public class PassengerDetails extends AppCompatActivity {
    Button next, back, confirm;
    TextView errormessage, personnumber;
    EditText firstname, lastname, gender, DOB, nationality;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_passenger_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        next = findViewById(R.id.nextperson);
        back = findViewById(R.id.backperson);
        errormessage = findViewById(R.id.textView13);
        firstname = findViewById(R.id.firstName);
        lastname = findViewById(R.id.lastname);
        gender = findViewById(R.id.gender);
        DOB = findViewById(R.id.DOB);
        nationality = findViewById(R.id.Nationality);
        personnumber = findViewById(R.id.personString);
        confirm = findViewById(R.id.confirm);

        errormessage.setVisibility(TextView.GONE);
        back.setVisibility(View.GONE);
        confirm.setVisibility(View.GONE);




        Intent intent = getIntent();
        int pax = intent.getIntExtra("pax", 0);
        boolean isreturn = intent.getBooleanExtra("isreturn", false);
        String departflight = intent.getStringExtra("departflight");
        String returnflight = intent.getStringExtra("returnflight");
        String travelclass = intent.getStringExtra("travelclass");
        System.out.println(departflight);
        System.out.println(returnflight);
        System.out.println(travelclass);


        ArrayList<Passenger> passengers = new ArrayList<>();

        if (pax == 1){
            next.setVisibility(View.GONE);
            back.setVisibility(View.GONE);
            confirm.setVisibility(View.VISIBLE);
        }

        //create onclicklistener
        View.OnClickListener clickListener = new View.OnClickListener() {
            int i = 0;
            String personstring;
            @Override
            public void onClick(View v) {
                String fname = firstname.getText().toString();
                String lname = lastname.getText().toString();
                String genderinput = gender.getText().toString();
                String dob  = DOB.getText().toString();
                String country = nationality.getText().toString();





                if (v.getId() == R.id.nextperson) {
                    if(!fname.isEmpty() || !lname.isEmpty() || !genderinput.isEmpty() || !dob.isEmpty() || !country.isEmpty()) {
                        errormessage.setVisibility(View.GONE);
                        if (i == 0) {
                            back.setVisibility(View.VISIBLE);
                            Passenger newpassenger = new Passenger(fname, lname, genderinput, dob, country);
                            passengers.add(i, newpassenger);
                            firstname.setText("");
                            lastname.setText("");
                            gender.setText("");
                            DOB.setText("");
                            nationality.setText("");
                            try{
                                Passenger nextpassenger = passengers.get(i+1);
                                firstname.setText(nextpassenger.getFirstName());
                                lastname.setText(nextpassenger.getLastName());
                                gender.setText(nextpassenger.getGender());
                                DOB.setText(nextpassenger.getDOB());
                                nationality.setText(nextpassenger.getNationality());
                            } catch (Exception e){

                            }
                            i++;
                            int j = i+1;
                            personstring = "Person " + j;
                            personnumber.setText(personstring);
                            if (i >= pax-1){
                                next.setVisibility(View.GONE);
                                confirm.setVisibility(View.VISIBLE);
                            }


                        } else {
                            Passenger newpassenger = new Passenger(fname, lname, genderinput, dob, country);
                            passengers.add(i, newpassenger);
                            firstname.setText("");
                            lastname.setText("");
                            gender.setText("");
                            DOB.setText("");
                            nationality.setText("");
                            try{
                                Passenger nextpassenger = passengers.get(i+1);
                                firstname.setText(nextpassenger.getFirstName());
                                lastname.setText(nextpassenger.getLastName());
                                gender.setText(nextpassenger.getGender());
                                DOB.setText(nextpassenger.getDOB());
                                nationality.setText(nextpassenger.getNationality());
                            } catch (Exception e){

                            }
                            i++;
                            int j = i+1;
                            personstring = "Person " + j;
                            personnumber.setText(personstring);


                        }
                    } else {
                        errormessage.setVisibility(View.VISIBLE);
                    }
                } else if (v.getId() == R.id.backperson) {
                    errormessage.setVisibility(View.GONE);
                    if(i == pax-1){
                        next.setVisibility(View.VISIBLE);
                    }

                    i--;
                    Passenger nextpassenger = passengers.get(i);
                    firstname.setText(nextpassenger.getFirstName());
                    lastname.setText(nextpassenger.getLastName());
                    gender.setText(nextpassenger.getGender());
                    DOB.setText(nextpassenger.getDOB());
                    nationality.setText(nextpassenger.getNationality());
                    passengers.remove(i);
                    if (i == 0){
                        back.setVisibility(View.GONE);
                    }
                    int j = i+1;
                    personstring = "Person " + j;
                    personnumber.setText(personstring);

                } else if (v.getId() == R.id.confirm){
                    if(!fname.isEmpty() || !lname.isEmpty() || !genderinput.isEmpty() || !dob.isEmpty() || !country.isEmpty()) {
                        Passenger newpassenger = new Passenger(fname, lname, genderinput, dob, country);
                        passengers.add(i, newpassenger);
                        int totalprice = 0;
                        if (isreturn == true){
                            for (Flight flight: MainActivity.flights){
                                if (departflight.equals(flight.getFlightId())){
                                    if (travelclass.equals("Economy")){
                                        totalprice = totalprice + (flight.getEconprice()*pax);
                                    } else if (travelclass.equals("Business")){
                                        totalprice = totalprice + (flight.getBusinessprice()*pax);
                                    } else if (travelclass.equals("First")){
                                        totalprice = totalprice + (flight.getFirstprice()*pax);
                                    }
                                }
                                if (returnflight.equals(flight.getFlightId())){
                                    if (travelclass.equals("Economy")){
                                        totalprice = totalprice + (flight.getEconprice()*pax);
                                    } else if (travelclass.equals("Business")){
                                        totalprice = totalprice + (flight.getBusinessprice()*pax);
                                    } else if (travelclass.equals("First")){
                                        totalprice = totalprice + (flight.getFirstprice()*pax);
                                    }
                                }
                            }
                            Return newbooking = new Return(travelclass, departflight,  totalprice, returnflight);
                            newbooking.addPassenger(passengers);
                            Intent i = new  Intent(PassengerDetails.this, Payment.class);
                            i.putExtra("booking", newbooking);
                            startActivity(i);

                        }else {
                            for (Flight flight : MainActivity.flights) {
                                if (departflight.equals(flight.getFlightId())) {
                                    if (travelclass.equals("Economy")) {
                                        totalprice = totalprice + (flight.getEconprice() * pax);
                                    } else if (travelclass.equals("Business")) {
                                        totalprice = totalprice + (flight.getBusinessprice() * pax);
                                    } else if (travelclass.equals("First")) {
                                        totalprice = totalprice + (flight.getFirstprice() * pax);
                                    }
                                }
                                Booking newbooking = new Booking(travelclass, departflight, totalprice);
                                newbooking.addPassenger(passengers);
                                Intent i = new  Intent(PassengerDetails.this, Payment.class);
                                i.putExtra("booking", newbooking);
                                i.putExtra("isreturn", isreturn);
                                startActivity(i);


                            }
                        }
                    } else {
                        errormessage.setVisibility(View.VISIBLE);
                    }
                }

            }
        };

        //set onclicklistener to buttons
        back.setOnClickListener(clickListener);
        next.setOnClickListener(clickListener);
        confirm.setOnClickListener(clickListener);





    }
}