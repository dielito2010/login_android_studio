package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class HomeActivity extends AppCompatActivity {
    TextView txtHome;
    Button bntSair;
    FirebaseUser user;
    FirebaseAuth auth;
    SQLiteDatabase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        txtHome = findViewById(R.id.getUser);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        getUserDb();

        bntSair = findViewById(R.id.bntLogout);

        bntSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void getUserDb() {
        try {
            dataBase = openOrCreateDatabase("DB_LOGIN_APP", MODE_PRIVATE, null);
                Cursor myCursor = dataBase.rawQuery("SELECT Id, First_Name, Last_Name FROM User", null);

            dataBase.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        txtHome.setText(String.valueOf(user.getEmail()));
    }
}