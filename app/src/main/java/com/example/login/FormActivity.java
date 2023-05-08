package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormActivity extends AppCompatActivity {

    public static final String EXTRA_FNAME = "package com.motoacademy.lablogin.FNAME";
    public static final String EXTRA_LNAME = "package com.motoacademy.lablogin.LNAME";

    private EditText mFname, mEmail;
    private EditText mLname;
    private Button mButtonSave;
    private Button mButtonDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mFname  = findViewById(R.id.main_fname);
        mLname  = findViewById(R.id.main_lname);
        mEmail = findViewById(R.id.main_email);
        mButtonSave = findViewById(R.id.main_button_save);
        mButtonDisplay = findViewById(R.id.main_button_display);

        mButtonSave.setEnabled(false);
        mLname.setEnabled(false);

        mFname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mLname.setEnabled(!editable.toString().isEmpty());
            }
        });

        mLname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mButtonSave.setEnabled(!editable.toString().isEmpty());
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fName = mFname.getText().toString();
                String lName = mLname.getText().toString();
                String email = mEmail.getText().toString();

                MyDataBaseHelper myDB = new MyDataBaseHelper(FormActivity.this);
                myDB.addPerson(fName.trim(), lName.trim(), email.trim());



            }
        });

        mButtonDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyDataBaseHelper myDB = new MyDataBaseHelper(FormActivity.this);

                Intent intent = new Intent(FormActivity.this, DisplayActivity.class);
                startActivity(intent);

            }
        });

    }
}