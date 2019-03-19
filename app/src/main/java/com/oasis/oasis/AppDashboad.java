package com.oasis.oasis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class AppDashboad extends AppCompatActivity {
    GridView grid;
    String text[] = {"Approved","Pending","Missed","Denied"};
    int image[] = {R.mipmap.profile,R.mipmap.profile,R.mipmap.profile,R.mipmap.profile};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboad);

        grid = (GridView)findViewById(R.id.simpleGrid);
        grid.setAdapter(new ImageAdapter(this,image,text));

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),text[position],Toast.LENGTH_LONG).show();
            }
        });
    }
}
