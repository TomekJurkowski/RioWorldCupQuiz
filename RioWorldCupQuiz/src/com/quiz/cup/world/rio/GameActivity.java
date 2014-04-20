package com.quiz.cup.world.rio;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.quiz.cup.world.rio.tools.MusicManager;

public class GameActivity extends Activity {
	// constant holding the number of seconds for each question
	private static final int TIME = 45;
	
	private TextView timerValue;
	private int milliseconds_left;
	
	private Handler customHandler = new Handler();
	private long startTime = 0L;

	private ImageButton buttonPause;
	private ImageButton buttonYellowCard;
	private ImageButton buttonRedCard;
	private ImageButton buttonAskExpert;
	private ImageButton buttonAskFans;

	private TextView question;
	private Button buttonA;
	private Button buttonB;
	private Button buttonC;
	private Button buttonD;
	
	private boolean isPaused = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_game);

    	timerValue = (TextView) findViewById(R.id.timerValue);
    	
        setButtonsListeners();
        prepareNewQuestion();
    }

    private void setButtonsListeners() {
        buttonPause = (ImageButton) findViewById(R.id.btnPause);
        buttonPause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pauseGame(v);
			}
		});
        
        buttonYellowCard = (ImageButton) findViewById(R.id.btnYellowCard);
        buttonYellowCard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});
        
        buttonRedCard = (ImageButton) findViewById(R.id.btnRedCard);
        buttonRedCard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});

        buttonAskExpert = (ImageButton) findViewById(R.id.btnAskExpert);
        buttonAskExpert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});

        buttonAskFans = (ImageButton) findViewById(R.id.btnAskFans);
        buttonAskFans.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});
        
        buttonA = (Button) findViewById(R.id.btnA);
        buttonA.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});

        buttonB = (Button) findViewById(R.id.btnB);
        buttonB.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});

        buttonC = (Button) findViewById(R.id.btnC);
        buttonC.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});

        buttonD = (Button) findViewById(R.id.btnD);
        buttonD.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});
    }
    
    private void prepareNewQuestion() {
    	milliseconds_left = TIME * 1000;
    	timerValue.setText(String.format("%02d", TIME));
    	
		// the line below should have no effect, since when
		// the function start() is called there should be no
		// pending posts of updateTimerThread in the message queue
		customHandler.removeCallbacks(updateTimerThread);
        
    	startTime = SystemClock.uptimeMillis();
    	customHandler.postDelayed(updateTimerThread, 0);

    }
    
    private void resumeTimer() {
		startTime = SystemClock.uptimeMillis();
    	customHandler.postDelayed(updateTimerThread, 0);		
	}
    
	private void pauseTimer() {
        customHandler.removeCallbacks(updateTimerThread);
	}
    
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
        	long timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            startTime = SystemClock.uptimeMillis();
            
            milliseconds_left -= timeInMilliseconds;
            
            // Time's up
            if (milliseconds_left <= 0) {
            	customHandler.removeCallbacks(updateTimerThread);
            	gameOver();
            	return ;
            }
            
            int timeLeft = ((int) (milliseconds_left / 1000)) + 1;
        	timerValue.setText(String.format("%02d", timeLeft));
        	
        	// we can afford to rerun this function after about 100ms
            customHandler.postDelayed(this, 100);
        }
    };
    
    private void pauseGame(View v) {
    	isPaused = true;
    	
        pauseTimer();
        
        LayoutInflater layoutInflater =
        		(LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView =
        		layoutInflater.inflate(R.layout.pause_popup, null);  
	    final PopupWindow popupWindow =
	    		new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

	    Button btnResume = (Button) popupView.findViewById(R.id.resume);
    	btnResume.setOnClickListener(new Button.OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			popupWindow.dismiss();
    			resumeGame();
    		}
    	});
    	
	    Button btnRestart = (Button) popupView.findViewById(R.id.restart);
    	btnRestart.setOnClickListener(new Button.OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			//TODO
    		}
    	});

	    Button btnExit = (Button) popupView.findViewById(R.id.exit);
    	btnExit.setOnClickListener(new Button.OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			popupWindow.dismiss();
    			exitToMenu();
    		}
    	});

    	popupWindow.setFocusable(true);
    	popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
    }
    
    private void resumeGame() {
    	isPaused = false;
    	resumeTimer();
    }
    
    private void gameOver() {
    	//TODO
    	finish();
    }
    
    // Function that ends the game and returns to the menu,
    // without saving our score.
    private void exitToMenu() {
    	finish();
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
        
        if (!isPaused) {
            pauseGame(getWindow().getDecorView().findViewById(android.R.id.content));        	
        }
    }
}
