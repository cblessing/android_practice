package com.ceebee.flooringhelper;

/**
 * Created by gottabcb on 7/22/13.
 */
public class Room {
    private double length;
    private double width;
    private String name;
    private long id;

    // PUBLIC STUFF
    public Room(){}

    public long GetID(){ return this.id; }
    public void SetID(long id){ this.id = id; }

    public double GetLength(){ return this.length; }
    public void SetLength(double length){ this.length = length; }

    public double GetWidth(){ return this.width; }
    public void SetWidth(double width){ this.width = width; }

    public String GetName(){ return this.name; }
    public void SetName(String name){ this.name = name; }

    @Override
    public String toString() {
        return name;
    }
}
