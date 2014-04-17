package com.quiz.cup.world.rio.tools;

import java.io.IOException;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RioDbAdapter {
	protected static final String TAG = "RioDbAdapter";
	
	private static final String EasyQueriesTable = "EasyQueries";
	private static final String DifficultQueriesTable = "DifficultQueries";
	private static final String ExtremeQueriesTable = "ExtremeQueries";

    private final Context myContext;
    private SQLiteDatabase myDb;
    private DataBaseHelper myDbHelper;
    
    public RioDbAdapter(Context context) {
        this.myContext = context;
        myDbHelper = new DataBaseHelper(myContext);
    }

    public RioDbAdapter createDatabase() throws SQLException {
        try {
            myDbHelper.createDataBase();
        } 
        catch (IOException e) {
            Log.e(TAG, e.toString() + " UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }
    
    public RioDbAdapter open() throws SQLException {
        try {
            myDbHelper.openDataBase();
            myDbHelper.close();
            myDb = myDbHelper.getReadableDatabase();
        } 
        catch (SQLException e) {
            Log.e(TAG, "open >>"+ e.toString());
            throw e;
        }
        return this;
    }
    
    public void close() {
        myDbHelper.close();
    }
    
    public Cursor getAllEasyQueries() {
        try {
            String sql ="SELECT * FROM " + EasyQueriesTable;

            Cursor mCur = myDb.rawQuery(sql, null);
            if (mCur != null) {
               mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException e) {
            Log.e(TAG, "getTestData >>"+ e.toString());
            throw e;
        }
    }
    
    public Cursor getAllDifficultQueries() {
        try {
            String sql ="SELECT * FROM " + DifficultQueriesTable;

            Cursor mCur = myDb.rawQuery(sql, null);
            if (mCur != null) {
               mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException e) {
            Log.e(TAG, "getTestData >>"+ e.toString());
            throw e;
        }
    }
    
    public Cursor getAllExtremeQueries() {
        try {
            String sql ="SELECT * FROM " + ExtremeQueriesTable;

            Cursor mCur = myDb.rawQuery(sql, null);
            if (mCur != null) {
               mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException e) {
            Log.e(TAG, "getTestData >>"+ e.toString());
            throw e;
        }
    }
}
