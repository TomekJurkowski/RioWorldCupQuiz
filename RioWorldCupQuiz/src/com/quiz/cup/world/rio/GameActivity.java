package com.quiz.cup.world.rio;

import com.quiz.cup.world.rio.tools.MusicManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameActivity extends Activity {

	private Button buttonBack;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_game);
        
        buttonBack = (Button)findViewById(R.id.btnBack);
        buttonBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
    }

    @Override
    protected void onResume() {
    	super.onResume();
    	
    	MusicManager.start(this, MusicManager.MUSIC_GAME, getApplicationContext());
    }

    @Override
    public void onPause() {
        super.onPause();

        MusicManager.pause();
    }
}
