package com.ceebee.flooringhelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    private BuildingsDataSource buildingsDatasource;

    ArrayAdapter<String> aa;

    Button addButton;
    Button removeFirstButton;
    ListView lvBuildings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button)findViewById(R.id.btnAddBuilding);
        removeFirstButton = (Button)findViewById(R.id.btnRemoveFirstBuilding);
        lvBuildings = (ListView)findViewById(R.id.lvBuildings);

        addButton.setOnClickListener(this);
        removeFirstButton.setOnClickListener(this);

        try {
            buildingsDatasource = new BuildingsDataSource(this);
            buildingsDatasource.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> buildingsNames = buildingsDatasource.GetAllBuildingsNames();

        if (buildingsNames.isEmpty()){
            aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        }
        else{
            aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, buildingsNames);
        }

        lvBuildings.setAdapter(aa);

    }

    @Override
    protected void onResume() {
        try {
            buildingsDatasource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        buildingsDatasource.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void addItems(String item){
        if (item.length() > 0){
            aa.add(item);
        }
    }

    public void onClick(View v) {
        if(v == addButton){
            AlertDialog.Builder alertGetBuildingName = new AlertDialog.Builder(this);

            alertGetBuildingName.setTitle("Add Building");
            alertGetBuildingName.setMessage("Enter the name of a new building.");

            // Set an EditText view to get user input
            final EditText input = new EditText(this);
            alertGetBuildingName.setView(input);

            alertGetBuildingName.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String newName = input.getText().toString();

                    Building building = buildingsDatasource.CreateBuilding(newName);
                    aa.add(building.GetName());
                }
            });

            alertGetBuildingName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });

            alertGetBuildingName.show();
        }
        else if(v == removeFirstButton){
            String firstItem = aa.getItem(0);
            Building toRemove = buildingsDatasource.GetBuildingByName(firstItem);

            buildingsDatasource.DeleteBuilding(toRemove);
            aa.remove(firstItem);
        }
    }
    
}
