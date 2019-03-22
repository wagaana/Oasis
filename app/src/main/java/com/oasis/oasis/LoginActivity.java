package com.oasis.oasis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

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

        final CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
        final TextView countrycodetxt=findViewById(R.id.countrycodetxt);
        final TextView countrytxt=findViewById(R.id.countrytxt);
        Button nextbtn=findViewById(R.id.nextbtn);
        final ViewFlipper mViewFlipper = (ViewFlipper) findViewById(R.id.viewfillper);
        final ImageButton backbtn=findViewById(R.id.goback);
        final TextView confirmbtn=findViewById(R.id.confirmbtn);
        final TextView resendCode=findViewById(R.id.resendCode);
        final EditText phonetxt=findViewById(R.id.phonetxt);
        final TextView sendtotxt=findViewById(R.id.sendtotxt);

        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                // Implement your code here
                countrycodetxt.setText(dialCode);
                countrytxt.setText(name);
                picker.dismiss();
            }
        });

        RelativeLayout select_country=findViewById(R.id.select_country);
        select_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phonetxt.getText().toString()!=null && !phonetxt.getText().toString().isEmpty()) {
                    String phone=countrycodetxt.getText().toString()+phonetxt.getText().toString();
                    sendtotxt.setText(phone);
                    //send code and then open the next flipper
                    Toast.makeText(LoginActivity.this, "Sending....", Toast.LENGTH_SHORT).show();
                    mViewFlipper.showNext();
                }else{
                    phonetxt.setError("Phone Is Required To Continue.");
                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewFlipper.showPrevious();
            }
        });

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here we shall have to validate the code and then callDashboad()
                callDashboad();
            }
        });

        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resend code
                Toast.makeText(LoginActivity.this, "Sending....", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void callDashboad(){
        Intent intent=new Intent(this, AppDashboad.class);
        startActivity(intent);
        finish();
    }
}
