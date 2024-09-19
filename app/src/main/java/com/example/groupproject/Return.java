package com.example.groupproject;
public class Return extends Booking{
    private String returnflight;

    Return(String reference, String travelclass, String flight, int totalprice, String returnflight){
        super(reference, travelclass, flight, totalprice);
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
