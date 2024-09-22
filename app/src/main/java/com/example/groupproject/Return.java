package com.example.groupproject;

import java.io.Serializable;

public class Return extends Booking implements Serializable
{
    private String returnflight;

    Return(String reference, String travelclass, String flight, int totalprice, String returnflight){
        super(reference, travelclass, flight, totalprice);
        this.returnflight = returnflight;
    }

    Return(String travelclass, String flight, int totalprice, String returnflight){
        super(travelclass, flight, totalprice);
        this.returnflight = returnflight;
    }

    public String getReturnflight() {
        return returnflight;
    }

    @Override
    public String toString() {
        return super.toString() + "Return{" +
                "returnflight='" + returnflight + '\'' +
                '}';
    }
}
