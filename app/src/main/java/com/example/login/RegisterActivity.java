package com.example.login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    EditText txtEmail, txtPass, txtFirstName, txtLastName;
    MaterialButton bntReg;
    TextView gotoLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    SQLiteDatabase dataBase;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        txtEmail = findViewById(R.id.inputEmail);
        txtPass = findViewById(R.id.inputSenha);
        txtFirstName = findViewById(R.id.inputFirstName);
        txtLastName = findViewById(R.id.inputLastName);
        bntReg = findViewById(R.id.bntSubmit);
        gotoLogin = findViewById(R.id.logNow);
        progressBar = findViewById(R.id.progressbar);

        bntReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                String email, passwd, firstName, lastName;

                email = txtEmail.getText().toString();
                passwd = String.valueOf(txtPass.getText());
                firstName = txtFirstName.getText().toString();
                lastName = txtLastName.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(passwd) || TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
                    Toast.makeText(RegisterActivity.this, R.string.invalid_data, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    cadastrarNoBanco();
                    mAuth.createUserWithEmailAndPassword(email, passwd)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressBar.setVisibility(View.GONE);
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(RegisterActivity.this, R.string.welcome, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterActivity.this, R.string.authentication_failed,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void cadastrarNoBanco() {
        txtFirstName = findViewById(R.id.inputFirstName);
        txtLastName = findViewById(R.id.inputLastName);
        txtEmail = findViewById(R.id.inputEmail);
        String email, firstName, lastName;
        email = txtEmail.getText().toString();
        firstName = txtFirstName.getText().toString();
        lastName = txtLastName.getText().toString();

        try {
            dataBase = openOrCreateDatabase("DB_LOGIN_APP", MODE_PRIVATE, null);
            dataBase.execSQL("CREATE TABLE IF NOT EXISTS User(" +
                    " Id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ", Email VARCHAR, Firts_Name VARCHAR, Last_Name VARCHAR)");
            String sql = "INSERT INTO User (Email, First_Name, Last_Name) VALUES (?, ?, ?)";
            SQLiteStatement stmt = dataBase.compileStatement(sql);
            stmt.bindString(1, email);
            stmt.bindString(2, firstName);
            stmt.bindString(3, lastName);
            stmt.executeInsert();
            dataBase.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}