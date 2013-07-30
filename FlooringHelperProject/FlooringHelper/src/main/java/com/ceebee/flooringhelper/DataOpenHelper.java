package com.ceebee.flooringhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gottabcb on 7/22/13.
 */
public class DataOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    public static final String ROOMS_TABLE_NAME = "rooms";

    public static final String ROOMS_ID_COLUMN = "_id";
    public static final String ROOMS_NAME_COLUMN = "name";
    public static final String ROOMS_LENGTH_COLUMN = "length";
    public static final String ROOMS_WIDTH_COLUMN = "width";

    private static final String ROOMS_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + ROOMS_TABLE_NAME + " (" +
                    ROOMS_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ROOMS_NAME_COLUMN + " TEXT NOT NULL, " +
                    ROOMS_LENGTH_COLUMN + " REAL, " +
                    ROOMS_WIDTH_COLUMN + " REAL);";

    public static final String BUILDINGS_TABLE_NAME = "buildings";
    public static final String BUILDINGS_ID_COLUMN = "_id";
    public static final String BUILDINGS_NAME_COLUMN = "name";

    private static final String BUILDINGS_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + BUILDINGS_TABLE_NAME + " (" +
                    BUILDINGS_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BUILDINGS_NAME_COLUMN + " TEXT NOT NULL);";

    public static final String BUILDINGS_ROOMS_TABLE_NAME = "buildings";
    public static final String BUILDINGS_ROOMS_BUILDINGID_COLUMN = "buildingID";
    public static final String BUILDINGS_ROOMS_ROOMID_COLUMN = "roomID";
    private static final String BUILDINGS_ROOMS_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + BUILDINGS_ROOMS_TABLE_NAME + " (" +
                    BUILDINGS_ROOMS_BUILDINGID_COLUMN + " INTEGER NOT NULL, " +
                    BUILDINGS_ROOMS_ROOMID_COLUMN + " INTEGER NOT NULL);";

    DataOpenHelper(Context context) {
        super(context, FHProperties.getInstance().DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ROOMS_TABLE_CREATE);
        db.execSQL(BUILDINGS_TABLE_CREATE);
        db.execSQL(BUILDINGS_ROOMS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 212)
        {
            // this is just an example for future use
        }
    }
}
