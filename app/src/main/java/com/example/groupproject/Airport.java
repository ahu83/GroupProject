package com.example.groupproject;
public class Airport {
    private String code;
    private String name;
    private String city;
    private String country;
    private int timezone;

    Airport(String code, String name, String city, String country, int timezone){
        this.code = code;
        this.name = name;
        this.city = city;
        this.country = country;
        this.timezone = timezone;
    }

    public String getName() {
        return name;
    }

    public int getTimezone() {
        return timezone;
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", timezone=" + timezone +
                '}';
    }
}
