package com.oasis.oasis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class AppDashboad extends AppCompatActivity {
    GridView grid;
    String text[] = {"Reserve","Pending","Missed","Approved"};
    int image[] = {R.mipmap.reserve,R.mipmap.pending,R.mipmap.missed,R.mipmap.approved};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboad);

        grid = (GridView)findViewById(R.id.simpleGrid);
        grid.setAdapter(new ImageAdapter(this,image,text){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                int color = 0x00FFFFFF; // Transparent
                if (position==0) {
                    color = 0xFFFF00FF;
                }

                if (position==1) {
                    color = 0xFF8000FF;
                }

                if (position==2) {
                    color = 0xFF00FFF;
                }

                if (position==3) {
                    color = R.color.colorblue;
                }

                view.setBackgroundColor(color);

                return view;
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),text[position],Toast.LENGTH_LONG).show();
                if(text[position].equals("Reserve")){
                    Intent intent=new Intent(AppDashboad.this, BookAppointment.class);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(this, Profile.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
