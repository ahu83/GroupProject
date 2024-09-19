package com.example.groupproject;
public class Passenger {
    private String firstName;
    private String lastName;
    private String gender;
    private String DOB;
    private String nationality;

    Passenger(String firstName, String lastName, String gender, String DOB, String nationality){

        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.DOB = DOB;
        this.nationality = nationality;
    }

    public String getGender() {
        return gender;
    }

    public String getDOB() {
        return DOB;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", DOB='" + DOB + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
