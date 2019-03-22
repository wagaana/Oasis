package com.oasis.oasis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button googleLogin=findViewById(R.id.googleLogin);
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDashboad();
            }
        });
        //call callDashboad() on success login

        findViewById(R.id.btn_play_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager prefManager = new PrefManager(getApplicationContext());
                prefManager.setFirstTimeLaunch(true);
                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                finish();
            }
        });
    }

    public void callDashboad(){
        Intent intent=new Intent(this, AppDashboad.class);
        startActivity(intent);
    }
}
