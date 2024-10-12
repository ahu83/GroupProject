package com.example.groupproject;

import java.io.Serializable;

public class Passenger implements Serializable {
    private String firstName;
    private String lastName;
    private String gender;
    private String DOB;
    private String nationality;
    private int meal = 0;

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

    public void setMeal(int meal) {
        this.meal = meal;
    }

    public int getMeal() {
        return meal;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", DOB='" + DOB + '\'' +
                ", nationality='" + nationality + '\'' +
                ", meal='" + meal + '\'' +
                '}';
    }
}
