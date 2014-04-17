package com.quiz.cup.world.rio.tools;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	private static String DB_PATH = "com.quiz.cup.world.rio/databases/";
    private static String DB_NAME = "RioDatabase.db";

    private SQLiteDatabase myDataBase; 
    private final Context myContext;

    // Takes and keeps a reference of the passed context
    // in order to access the application assets and resources.
    public DataBaseHelper(Context context) {
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
    // Creates an empty database on the system and rewrites it
    // with your own database.
    public void createDataBase() throws IOException {
    	boolean dbExist = checkDataBase();
 
    	if(!dbExist) {
        	this.getReadableDatabase(); 
        	try {
    			copyDataBase();
    		}
        	catch (IOException e) {
        		throw new Error("Error copying database");
        	}
    	}
    }
    
    // Check if the database already exist to avoid re-copying the file
    // each time the user opens the application.
    private boolean checkDataBase() {
    	SQLiteDatabase checkDB = null;
    	
    	try {
    		String myPath = myContext.getFilesDir().getPath() + DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	}
    	catch(SQLiteException e) {
    		// database does't exist yet
    	}
 
    	boolean ret = checkDB != null ? true : false;
    	if(checkDB != null) {
    		checkDB.close();
    	}
 
    	return ret;
    }

    // Copies the database from the local assets-folder to
    // the just created empty database in the system folder,
    // from where it can be accessed and handled.
    // This is done by transferring bytestream.
    private void copyDataBase() throws IOException {
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = myContext.getFilesDir().getPath() + DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer)) > 0) {
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close(); 
    }
 
    public void openDataBase() throws SQLException {
        String myPath = myContext.getFilesDir().getPath() + DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }
    
    @Override
	public synchronized void close() {
    	if(myDataBase != null) {
		    myDataBase.close();    		
    	}
	    super.close();
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
}
