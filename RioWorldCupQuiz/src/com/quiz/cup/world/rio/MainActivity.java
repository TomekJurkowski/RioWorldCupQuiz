package com.quiz.cup.world.rio;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.quiz.cup.world.rio.tools.MusicManager;

public class MainActivity extends Activity {

    private static final String PREFERENCES_NAME = "RioPreferencesFile";
    private static final String SOUND_PREFERENCE = "SoundPreference";

	private Button buttonInstructions;
	private Button buttonExit;
	
    private ImageButton buttonMute;
    private ImageButton buttonUnmute;
    private boolean isMuted;
    
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        buttonInstructions = (Button) findViewById(R.id.btnInstructions);
        buttonInstructions.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, InstructionsActivity.class);
			    startActivity(intent);			
			}
		});
        
        buttonExit = (Button) findViewById(R.id.btnExit);
        buttonExit.setOnClickListener(new OnClickListener() {

			@Override
        	public void onClick(View v) {
				MusicManager.release();
				finish();
        	}
        });
 	
 		// Setting the listener for 'Mute' button
         buttonMute = (ImageButton) findViewById(R.id.btnMute);
         buttonMute.setOnClickListener(new OnClickListener() {

        	 @Override
             public void onClick(View v) {
        		 MusicManager.mute();
        		 buttonMute.setVisibility(View.GONE);
                 buttonUnmute.setVisibility(View.VISIBLE);
                 isMuted = true;
                 saveSoundPreferences();
             }
         });
             
         // Setting the listener for 'Unmute' button
         buttonUnmute = (ImageButton) findViewById(R.id.btnUnmute);
         buttonUnmute.setOnClickListener(new OnClickListener() {

        	 @Override
		 	 public void onClick(View v) {
        		 MusicManager.unmute();
				 buttonUnmute.setVisibility(View.GONE);
				 buttonMute.setVisibility(View.VISIBLE);
				 isMuted = false;
				 saveSoundPreferences();
			 }
         });
    }

    @Override
    protected void onResume() {
    	super.onResume();
    	
    	MusicManager.start(this, MusicManager.MUSIC_MENU, getApplicationContext());
    	
        // Restoring sound preference if such exists
        SharedPreferences prefs = getSharedPreferences(PREFERENCES_NAME, 0);
        isMuted = prefs.getBoolean(SOUND_PREFERENCE, false);
		
		if (isMuted) {
			MusicManager.mute();
			buttonUnmute.setVisibility(View.VISIBLE);
			buttonMute.setVisibility(View.GONE);
		}
		else {
			buttonMute.setVisibility(View.VISIBLE);
			buttonUnmute.setVisibility(View.GONE);
		}
    }
    
    @Override
    public void onPause() {
        super.onPause();
        
        MusicManager.pause();
    }
    
    @Override
    public void onBackPressed() {
    	MusicManager.release();
    	
    	super.onBackPressed();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

 	// Saving sound preference
	 private void saveSoundPreferences() {
        SharedPreferences prefs = getSharedPreferences(PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(SOUND_PREFERENCE, isMuted);
        editor.commit();
	 }
}
