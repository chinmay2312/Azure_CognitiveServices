package com.chinmayg.hacks.myapplication;

/**
 * Created by chinm on 24-02-2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    final static String TABLE_NAME = "karanDB";
    final static String LOCATION = "location";
    final static String _ID = "_id";
    final static String FOOD_ORDER = "food_order";
    final static String[] columns = { _ID, LOCATION, FOOD_ORDER };

    final private static String CREATE_CMD =

            "CREATE TABLE karanDB (" + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + LOCATION + "TEXT NOT NULL, "
                    + FOOD_ORDER + " TEXT NOT NULL)";

    final private static String NAME = "karanDB";
    final private static Integer VERSION = 1;
    final private Context mContext;

    public DatabaseOpenHelper(Context context) {
        // Always call superclass's constructor
        super(context, NAME, null, VERSION);

        // Save the context that created DB in order to make calls on that context,
        // e.g., deleteDatabase() below.
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CMD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // N/A
    }

    // Calls ContextWrapper.deleteDatabase() by way of inheritance
    void deleteDatabase() {
        mContext.deleteDatabase(NAME);
    }

    boolean insertOrderDetails(String location, ArrayList<String> foodOrder)
    {
        try {
            ContentValues values = new ContentValues();
            values.put(LOCATION, location);
            StringBuilder foodOrderStr = new StringBuilder("");
            for (String orderItem : foodOrder) {
                foodOrderStr.append(orderItem + ", ");
            }
            String foodOrderString = foodOrderStr.toString();
            foodOrderString = foodOrderString.substring(0,foodOrderString.length()-2);
            values.put(FOOD_ORDER,foodOrderString);
            getWritableDatabase().insert(TABLE_NAME,null,values);
        }
        catch(Exception e)
        {
            Log.d("insert",e.getLocalizedMessage());
            return false;
        }

        return true;
    }

    String getOrderByLocation(String location)
    {
        String foodOrder = "";
        String[] cols = {FOOD_ORDER};
        Cursor c = getWritableDatabase().query(TABLE_NAME,cols, null, new String[] {LOCATION}, null, null, null);

        if(c!=null)
        {
            try
            {
                c.moveToFirst();
                foodOrder = c.getString(c.getColumnIndex(FOOD_ORDER));
            }
            finally {
                c.close();
            }
        }
        return foodOrder;
    }

    /*boolean updateOrderDetails(String location, ArrayList<String> foodOrder)
    {
        try
        {

        }
        catch (Exception e)
        {
            Log.d("update",e.getLocalizedMessage());
            return false;
        }
        return true;
    }*/

}
