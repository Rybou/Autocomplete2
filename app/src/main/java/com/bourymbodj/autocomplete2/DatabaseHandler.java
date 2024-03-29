package com.bourymbodj.autocomplete2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bourymbodj on 16-07-01.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // For the logs
    public static final String TAG = "DatabseHandler.java";
    //Datbase version
    public static final int DATABASE_VERSION = 4;
    // DATABASE NAME
    protected static final String DATABASE_NAME = "NinjaDatabase2";
    //Table details
    public String tableName= "locations";
    public String fieldObjectId= "id";
    public String fieldObjectName="name";

    // constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="";

        sql += "CREATE TABLE " + tableName;
        sql += " ( ";
        sql += fieldObjectId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += fieldObjectName + " TEXT ";
        sql += " ) ";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         String sql= "DROP TABLE IF EXISTS" + tableName;
         db.execSQL(sql);

        onCreate(db);
    }
    //Create new record

    public boolean create ( MyObject myObj) {
        boolean createSuccessful = false;

        if (!checkIfExists(myObj.objectName)) {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(fieldObjectName, myObj.objectName);
            createSuccessful = db.insert(tableName, null, values) > 0;
            db.close();
            if (createSuccessful) {
                Log.e(TAG, myObj.objectName + "created.");
            }
        }
        return createSuccessful;
    }

    // Check if a record exists so it won't insert the next time you run the </code>
    public boolean checkIfExists(String objectName){

        boolean recordExists = false;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT" + fieldObjectId + "FROM " + tableName + "WHERE" + fieldObjectName
                + " = " + objectName + "'" , null);

        if ( cursor!= null){
            if(cursor.getCount()>0) {
                recordExists = true;
            }
        }

        cursor.close();
        db.close();

        return recordExists;
    }
    // Read records related to the search term
    public List<MyObject> read(String searchTerm) {

    List<MyObject> recordsList = new ArrayList<MyObject>();

     // select query
      String sql = "";
     sql += "SELECT * FROM " + tableName;
    sql += " WHERE " + fieldObjectName + " LIKE '%" + searchTerm + "%'";
    sql += " ORDER BY " + fieldObjectId + " DESC";
    sql += " LIMIT 0,5";

    SQLiteDatabase db = this.getWritableDatabase();

    // execute the query
    Cursor cursor = db.rawQuery(sql, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {

            // int productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(fieldProductId)));
            String objectName = cursor.getString(cursor.getColumnIndex(fieldObjectName));
            MyObject myObject = new MyObject(objectName);

            // add to list
            recordsList.add(myObject);

        } while (cursor.moveToNext());
    }

    cursor.close();
    db.close();

    // return the list of records
    return recordsList;
}
        }



