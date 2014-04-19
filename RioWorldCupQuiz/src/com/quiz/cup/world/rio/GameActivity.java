package com.quiz.cup.world.rio;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.quiz.cup.world.rio.tools.MusicManager;

public class GameActivity extends Activity {
	// constant holding the number of seconds for each question
	private static final int time = 45;
	

	private Button buttonPause;
	private Button buttonYellowCard;
	private Button buttonRedCard;
	private Button buttonAskExpert;
	private Button buttonAskFans;

	private TextView question;
	private Button buttonA;
	private Button buttonB;
	private Button buttonC;
	private Button buttonD;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_game);
        
        setButtonsListeners();
    }

    private void setButtonsListeners() {
        buttonPause = (Button)findViewById(R.id.btnPause);
        buttonPause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});
        
        buttonYellowCard = (Button)findViewById(R.id.btnYellowCard);
        buttonYellowCard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});
        
        buttonRedCard = (Button)findViewById(R.id.btnRedCard);
        buttonRedCard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});

        buttonAskExpert = (Button)findViewById(R.id.btnAskExpert);
        buttonAskExpert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});

        buttonAskFans= (Button)findViewById(R.id.btnAskFans);
        buttonAskFans.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});
        
        buttonA = (Button)findViewById(R.id.btnA);
        buttonA.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});

        buttonB = (Button)findViewById(R.id.btnB);
        buttonB.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});

        buttonC = (Button)findViewById(R.id.btnC);
        buttonC.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});

        buttonD = (Button)findViewById(R.id.btnD);
        buttonD.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
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
