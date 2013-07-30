package com.ceebee.flooringhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gottabcb on 7/28/13.
 */
public class BuildingsDataSource {

    // database fields
    private SQLiteDatabase db;
    private DataOpenHelper dbHelper;

    private final String[] allColumns = {DataOpenHelper.BUILDINGS_ID_COLUMN, DataOpenHelper.BUILDINGS_NAME_COLUMN};

    public BuildingsDataSource(Context context){
        dbHelper = new DataOpenHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Building CreateBuilding(String name){
        ContentValues values = new ContentValues();
        values.put(DataOpenHelper.BUILDINGS_NAME_COLUMN, name);

        // do the insert
        long insertID = db.insert(DataOpenHelper.BUILDINGS_TABLE_NAME, null, values);

        // get a cursor for the inserted record
        Cursor cursor = db.query(DataOpenHelper.BUILDINGS_TABLE_NAME, allColumns, DataOpenHelper.BUILDINGS_ID_COLUMN + " = " + insertID, null, null, null, null);

        cursor.moveToFirst();

        Building newBuilding = cursorToBuilding(cursor);
        cursor.close();

        return newBuilding;
    }

    public void DeleteBuilding(Building building){
        long id = building.GetID();
        db.delete(DataOpenHelper.BUILDINGS_TABLE_NAME, DataOpenHelper.BUILDINGS_ID_COLUMN + " = " + id, null);

        // probably wouldn't hurt to query the requested room to make sure it exists and that the id and name match
    }

    public Building GetBuildingById(long buildingId){

        Cursor cursor = db.query(DataOpenHelper.BUILDINGS_TABLE_NAME, allColumns, DataOpenHelper.BUILDINGS_ID_COLUMN + " = " + buildingId, null, null, null, null);
        cursor.moveToFirst();

        Building building = cursorToBuilding(cursor);
        cursor.close();

        return building;
    }

    public Building GetBuildingByName(String buildingName){

        Building building = null;
        Cursor cursor;

        try {
            cursor = db.query(DataOpenHelper.BUILDINGS_TABLE_NAME, allColumns, DataOpenHelper.BUILDINGS_NAME_COLUMN + " = '" + buildingName + "'", null, null, null, null);

            cursor.moveToFirst();

            building = cursorToBuilding(cursor);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return building;
    }

    public List<Building> GetAllBuildings(){
        List<Building> buildings = new ArrayList<Building>();

        Cursor cursor = db.query(DataOpenHelper.BUILDINGS_TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            Building building = cursorToBuilding(cursor);
            buildings.add(building);
            cursor.moveToNext();
        }

        cursor.close();
        return buildings;
    }

    public List<String> GetAllBuildingsNames(){
        List<String> buildings = new ArrayList<String>();

        Cursor cursor = db.query(DataOpenHelper.BUILDINGS_TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            String building = cursorToBuildingName(cursor);
            buildings.add(building);
            cursor.moveToNext();
        }

        cursor.close();
        return buildings;
    }

    private Building cursorToBuilding(Cursor cursor){
        Building building = new Building();
        building.SetID(cursor.getLong(cursor.getColumnIndex(DataOpenHelper.BUILDINGS_ID_COLUMN)));
        building.SetName(cursor.getString(cursor.getColumnIndex(DataOpenHelper.BUILDINGS_NAME_COLUMN)));
        return building;
    }

    private String cursorToBuildingName(Cursor cursor){
        return cursor.getString(cursor.getColumnIndex(DataOpenHelper.BUILDINGS_NAME_COLUMN));
    }
}
