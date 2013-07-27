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

    public Building(){
        // a new building needs a name, and needs to be inserted into the database to get an id

        // try to insert the new building and get its id, or throw an exception

    }

    public long GetID(){ return this.id; }
    public void SetID(long id){ this.id = id; }

    public String GetName(){ return this.name; }
    public void SetName(String name){ this.name = name; }

    public ArrayList<Room> GetRooms(){ return this.Rooms; }

    public void AddRoom(Room r){

    }

    public boolean RemoveRoom(Room r){
        boolean removed = false;


        return removed;
    }

    @Override
    public String toString() {
        return name;
    }

}
