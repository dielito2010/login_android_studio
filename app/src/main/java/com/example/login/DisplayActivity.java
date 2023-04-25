package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDataBaseHelper myDB;
    ArrayList<String> id, fName, lName;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });

        myDB = new MyDataBaseHelper(DisplayActivity.this);
        id = new ArrayList<>();
        fName = new ArrayList<>();
        lName = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(DisplayActivity.this, id, fName, lName);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayActivity.this));

    }

    void storeDataInArrays() {
        Cursor cursor = myDB.displayAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                fName.add(cursor.getString(1));
                lName.add(cursor.getString(2));
            }
        }


    }
}