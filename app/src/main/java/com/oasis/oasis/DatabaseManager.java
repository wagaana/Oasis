package com.oasis.oasis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

abstract public class DatabaseManager {
    abstract public void onCreate(SQLiteDatabase db);
    abstract public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    public void onOpen(SQLiteDatabase db){}
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    public void onConfigure(SQLiteDatabase db){}

    static private class DBSQLiteOpenHelper extends SQLiteOpenHelper {

        DatabaseManager databaseManager;
        private AtomicInteger counter = new AtomicInteger(0);

        public DBSQLiteOpenHelper(Context context, String name, int version, DatabaseManager databaseManager) {
            super(context, name, null, version);
            this.databaseManager = databaseManager;
        }

        public void addConnection(){
            counter.incrementAndGet();
        }
        public void removeConnection(){
            counter.decrementAndGet();
        }
        public int getCounter() {
            return counter.get();
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            databaseManager.onCreate(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            databaseManager.onUpgrade(db, oldVersion, newVersion);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            databaseManager.onOpen(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            databaseManager.onDowngrade(db, oldVersion, newVersion);
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            databaseManager.onConfigure(db);
        }
    }

    private static final ConcurrentHashMap<String,DBSQLiteOpenHelper> dbMap = new ConcurrentHashMap<String, DBSQLiteOpenHelper>();

    private static final Object lockObject = new Object();


    private DBSQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase db;
    private Context context;
    private boolean isActive;

    public DatabaseManager(Context context, String name, int version) {
        String dbPath = context.getApplicationContext().getDatabasePath(name).getAbsolutePath();
        synchronized (lockObject) {
            sqLiteOpenHelper = dbMap.get(dbPath);
            if (sqLiteOpenHelper==null) {
                sqLiteOpenHelper = new DBSQLiteOpenHelper(context, name, version, this);
                dbMap.put(dbPath,sqLiteOpenHelper);
            }
			//SQLiteOpenHelper class caches the SQLiteDatabase, so this will be the same SQLiteDatabase object every time
            db = sqLiteOpenHelper.getWritableDatabase();
        }
        this.context = context.getApplicationContext();
        this.isActive = true;
    }

     @Deprecated
	public SQLiteDatabase getDb(){
		return open();
	}

    public boolean isOpen(){
        return (db!=null&&db.isOpen());
    }

    public boolean close(){
    	if (this.isActive){
    		sqLiteOpenHelper.removeConnection();	
    	}
    	int count = sqLiteOpenHelper.getCounter();
    	if (count==0){
    	    synchronized (lockObject){
                if (db.inTransaction())db.endTransaction();
                if (db.isOpen())db.close();
                db = null;
            }
    	}
       return (count==0);
    }

	public SQLiteDatabase open(){
		if (!this.isActive){
			sqLiteOpenHelper.addConnection();
		}
		if (db==null||!db.isOpen()){
			synchronized (lockObject){
	          		db = sqLiteOpenHelper.getWritableDatabase();
	        	 }
		}
		this.isActive = true;
		return db;
	}

    public static String getDate(){
        Date date=new Date();
        String rtn=String.format("%tB %<te, %<tY", date);
        return rtn;
    }

    public String[] getUser(){
        SQLiteDatabase db=open();
        String query="SELECT EMAIL, PASSWORD FROM CURENT_USER";
        Cursor cursor = db.rawQuery(query, null);
        String[] str = new String[]{};
        if (cursor != null && cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                str = addToArr(str, cursor.getString(0));
                str = addToArr(str, cursor.getString(1));
            }
        }
        cursor.close();
        db.close();
        return str;
    }

    public boolean checkUser(){
        SQLiteDatabase db=open();
        String query="SELECT EMAIL, PASSWORD FROM CURENT_USER";
        Cursor cursor = db.rawQuery(query, null);
        String str = null;
        boolean rtn=false;
        if (cursor != null && cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                str = cursor.getString(0);
            }
        }
        cursor.close();
        db.close();
        if (str!=null){
            rtn=true;
        }
        return rtn;
    }

    public String getUserEmail(){
        SQLiteDatabase db=open();
        String query="SELECT EMAIL FROM CURENT_USER";
        Cursor cursor = db.rawQuery(query, null);
        String str = null;
        if (cursor != null && cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                str = cursor.getString(0);
            }
        }
        return str;
    }

    public String getUserPassword(){
        SQLiteDatabase db=open();
        String query="SELECT PASSWORD FROM CURENT_USER";
        Cursor cursor = db.rawQuery(query, null);
        String str = null;
        if (cursor != null && cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                str = cursor.getString(0);
            }
        }
        return str;
    }

    public String getUserName(){
        SQLiteDatabase db=open();
        String query="SELECT NAME FROM CURENT_USER";
        Cursor cursor = db.rawQuery(query, null);
        String str = null;
        if (cursor != null && cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                str = cursor.getString(0);
            }
        }
        return str;
    }

    public void login(String emall, String pass, String full_name){
        SQLiteDatabase db = open();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("EMAIL", emall);
        contentValues2.put("PASSWORD", pass);
        contentValues2.put("NAME", full_name);
        db.insert("CURENT_USER", null, contentValues2);

        db.close();
    }

    public String[] check_account(){
        SQLiteDatabase db=open();
        String query="SELECT TYPE, ORGANIZATION FROM ACCOUNT_TYPE";
        Cursor cursor = db.rawQuery(query, null);
        String[] str = new String[]{};
        if (cursor != null && cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                str = addToArr(str, cursor.getString(0));
                str = addToArr(str, cursor.getString(1));
            }
        }
        cursor.close();
        db.close();
        return str;
    }

    public void add_acc_type(String type){
        SQLiteDatabase db = open();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("TYPE", type);
        db.insert("ACCOUNT_TYPE", null, contentValues2);

        db.close();
    }

    public void logout(){
        SQLiteDatabase db = open();
        db.execSQL("DROP TABLE IF EXISTS CURENT_USER");//
        db.execSQL("DROP TABLE IF EXISTS ACCOUNT_TYPE");//
        onCreate(db);
    }

    public static String[] addToArr(String[] originalArray, String newItem){
        int currentSize = originalArray.length;
        int newSize = currentSize + 1;
        String[] tempArray = new String[ newSize ];
        for (int i=0; i < currentSize; i++)
        {
            tempArray[i] = originalArray [i];
        }
        tempArray[newSize- 1] = newItem;
        return tempArray;
    }
}

