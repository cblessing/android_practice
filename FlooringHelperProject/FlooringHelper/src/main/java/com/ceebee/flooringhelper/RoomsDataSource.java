package com.ceebee.flooringhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gottabcb on 7/22/13.
 */
public class RoomsDataSource {

    // database fields
    private SQLiteDatabase db;
    private DataOpenHelper dbHelper;

    private final String[] allColumns = {DataOpenHelper.ROOMS_ID_COLUMN, DataOpenHelper.ROOMS_NAME_COLUMN, DataOpenHelper.ROOMS_LENGTH_COLUMN, DataOpenHelper.ROOMS_WIDTH_COLUMN};

    private final String buildingsRoomsQuery =  "SELECT * " +
                                                " FROM " + DataOpenHelper.ROOMS_TABLE_NAME +
                                                " LEFT OUTER JOIN " + DataOpenHelper.BUILDINGS_ROOMS_TABLE_NAME +
                                                " ON " + DataOpenHelper.ROOMS_TABLE_NAME + "." + DataOpenHelper.ROOMS_ID_COLUMN + " = " + DataOpenHelper.BUILDINGS_ROOMS_TABLE_NAME + "." + DataOpenHelper.BUILDINGS_ROOMS_ROOMID_COLUMN +
                                                " WHERE " + DataOpenHelper.BUILDINGS_ROOMS_TABLE_NAME + "." + DataOpenHelper.BUILDINGS_ROOMS_BUILDINGID_COLUMN + " = ?" ;

    public RoomsDataSource(Context context){
        dbHelper = new DataOpenHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Room CreateRoom(String name){
        ContentValues values = new ContentValues();
        values.put(DataOpenHelper.ROOMS_NAME_COLUMN, name);

        // do the insert
        long insertID = db.insert(DataOpenHelper.ROOMS_TABLE_NAME, null, values);

        // get a cursor for the inserted record
        Cursor cursor = db.query(DataOpenHelper.ROOMS_TABLE_NAME, allColumns, DataOpenHelper.ROOMS_ID_COLUMN + " = " + insertID, null, null, null, null);

        cursor.moveToFirst();

        Room newRoom = cursorToRoom(cursor);
        cursor.close();

        return newRoom;
    }

    public Room CreateRoom(String name, double length, double width){
        ContentValues values = new ContentValues();
        values.put(DataOpenHelper.ROOMS_NAME_COLUMN, name);
        values.put(DataOpenHelper.ROOMS_LENGTH_COLUMN, length);
        values.put(DataOpenHelper.ROOMS_WIDTH_COLUMN, width);

        // do the insert
        long insertID = db.insert(DataOpenHelper.ROOMS_TABLE_NAME, null, values);

        // get a cursor for the inserted record
        Cursor cursor = db.query(DataOpenHelper.ROOMS_TABLE_NAME, allColumns, DataOpenHelper.ROOMS_ID_COLUMN + " = " + insertID, null, null, null, null);

        cursor.moveToFirst();

        Room newRoom = cursorToRoom(cursor);
        cursor.close();

        return newRoom;
    }

    public void DeleteRoom(Room room){
        long id = room.GetID();
        db.delete(DataOpenHelper.ROOMS_TABLE_NAME, DataOpenHelper.ROOMS_ID_COLUMN + " = " + id, null);

        // probably wouldn't hurt to query the requested room to make sure it exists and that the id and name match
    }

    public List<Room> GetAllRooms(){
        List<Room> rooms = new ArrayList<Room>();

        Cursor cursor = db.query(DataOpenHelper.ROOMS_TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            Room room = cursorToRoom(cursor);
            rooms.add(room);
            cursor.moveToNext();
        }

        cursor.close();
        return rooms;
    }

    public List<Room> GetBuildingsRooms(Building building){
        List<Room> rooms = new ArrayList<Room>();

        Cursor cursor = db.rawQuery(buildingsRoomsQuery, new String[]{String.valueOf(building.GetID())});
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            Room room = cursorToRoom(cursor);
            rooms.add(room);
            cursor.moveToNext();
        }

        cursor.close();
        return rooms;
    }

    private Room cursorToRoom(Cursor cursor){
        Room room = new Room();
        room.SetID(cursor.getLong(cursor.getColumnIndex(DataOpenHelper.ROOMS_ID_COLUMN)));
        room.SetName(cursor.getString(cursor.getColumnIndex(DataOpenHelper.ROOMS_NAME_COLUMN)));
        room.SetLength(cursor.getDouble(cursor.getColumnIndex(DataOpenHelper.ROOMS_LENGTH_COLUMN)));
        room.SetWidth(cursor.getDouble(cursor.getColumnIndex(DataOpenHelper.ROOMS_WIDTH_COLUMN)));
        return room;
    }
}
