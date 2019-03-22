package com.oasis.oasis;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class EntryPortal extends AppCompatActivity {
    private static DatabaseManager databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_portal);
        openDatabase();

        if (!databaseHelper.checkUser()){
            callLoginactivity();
        }else{
            callactivity();
        }
    }

    public  void openDatabase(){
        databaseHelper = new DatabaseManager(this, "db.db", 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                String sql = "CREATE TABLE IF NOT EXISTS CURENT_USER " +
                        "(EMAIL TEXT, " +
                        " PASSWORD TEXT, " +
                        " NAME TEXT)";
                db.execSQL(sql);

                sql = "CREATE TABLE IF NOT EXISTS ACCOUNT_TYPE " +
                        "(TYPE TEXT, " +
                        " ORGANIZATION TEXT)";
                db.execSQL(sql);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS CURENT_USER");
                onCreate(db);
            }
        };//this opens the database
    }

    public void callactivity(){
        Intent intent=new Intent(this, AppDashboad.class);
        startActivity(intent);
        finish();
    }

    public void callLoginactivity(){
        Intent intent=new Intent(this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

}

