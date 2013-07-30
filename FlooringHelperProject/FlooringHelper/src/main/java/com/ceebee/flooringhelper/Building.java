package com.ceebee.flooringhelper;

import java.util.ArrayList;

/**
 * Created by gottabcb on 7/22/13.
 */
public class Building {
    private ArrayList<Room> Rooms;
    private String name;
    private long id;

    // PUBLIC STUFF
    public Building(){}

    public long GetID(){ return this.id; }
    public void SetID(long id){ this.id = id; }

    public String GetName(){ return this.name; }
    public void SetName(String name){ this.name = name; }

    public ArrayList<Room> GetRooms(){ return this.Rooms; }

    public void AddRoom(Room r){
        // not used yet, haven't decided if this is the way to go
    }

    public boolean RemoveRoom(Room r){
        // not used yet, haven't decided if this is the way to go
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

}
