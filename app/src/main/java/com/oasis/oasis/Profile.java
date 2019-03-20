package com.oasis.oasis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        EditText fullName=findViewById(R.id.names);
        Button submitUpdates=findViewById(R.id.applyUpdate);
    }
}

