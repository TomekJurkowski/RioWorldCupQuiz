package com.quiz.cup.world.rio.tools;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

import com.quiz.cup.world.rio.R;

// Static class responsible for playing background music. There are two
// types (modes) of music that can be played - one designed for every activity
// except for the actual game and the other for the game.
public class MusicManager {
	private static final String TAG = "MusicManager";

	// Arrays of songs to be played in particular situations
	private static int[] menuMusic = { R.raw.wakawaka1,
		R.raw.theme1,
		R.raw.theme3,
		R.raw.theme5,
		R.raw.theme7};
	private static int[] gameMusic = { R.raw.wakawaka2,
		R.raw.theme2,
		R.raw.theme4,
		R.raw.theme6,
		R.raw.theme8};

	// Constants used only inside this static class
	private static final int arraySize = 5;
	private static final int NO_MUSIC = -1;

	// Constants visible to activities, representing the two existing modes of music
	public static final int MUSIC_MENU = 0;
	public static final int MUSIC_GAME = 1;
	
	// Static attributes of a static class
	private static MediaPlayer mp;
	private static Context myContext = null;
	private static int currentMusic = -1;
	private static boolean isPaused = false;
	
	// The only valid values for music argument are MUSIC_MENU and MUSIC_GAME - 
	// representing the modes of the music that we want to play
	public static void start(Context context, int music, Context appContext) {
		innitiateMyContext(appContext);
		
		if (isPaused && currentMusic == music && currentMusic != NO_MUSIC) {
			// Happens when the music has been paused and we want to start it again,
			// but the mode we are on is the same it has been right before pausing
			// the music (in other words currentMusic == music)
			isPaused = false;
			mp.start();
			
			return ;
		}
		
		if (currentMusic == music && currentMusic != NO_MUSIC) {
			// Happens when the music is already playing (might be paused) and
			// it is of valid mode
			return ;
		}
		
		if (currentMusic == NO_MUSIC) {
			// Happens when no music is playing
			currentMusic = music;
			if (currentMusic == MUSIC_MENU) {
				mp = MediaPlayer.create(context, menuMusic[getRandomSongId()]);
			}
			else {
				mp = MediaPlayer.create(context, gameMusic[getRandomSongId()]);
			}
			mp.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer arg0) {
			        if (currentMusic == MUSIC_MENU) {
			        	stopAndPlay(menuMusic[getRandomSongId()]);
			        }
			        else {
			        	stopAndPlay(gameMusic[getRandomSongId()]);
			        }
				}
			});
			mp.start();
		}
		else {
			// Happens when some music is playing (might also be paused
			// at the moment) and we are changing it
			currentMusic = music;
			if (currentMusic == MUSIC_MENU) {
				stopAndPlay(menuMusic[getRandomSongId()]);
			}
			else {
				stopAndPlay(gameMusic[getRandomSongId()]);
			}
		}
	}

	private static void innitiateMyContext(Context c) {
		if (myContext == null) {
			myContext = c;
		}
	}
	
	private static void stopAndPlay(int resid) {
	    AssetFileDescriptor afd = myContext.getResources().openRawResourceFd(resid);

	    try
	    {   
	    	mp.stop();
	        mp.reset();
	        mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
	        mp.prepare();
	        mp.start();
	        afd.close();
	    }
	    catch (IllegalArgumentException e)
	    {
	        Log.e(TAG, "Unable to play audio queue do to exception: " + e.getMessage(), e);
	    }
	    catch (IllegalStateException e)
	    {
	        Log.e(TAG, "Unable to play audio queue do to exception: " + e.getMessage(), e);
	    }
	    catch (IOException e)
	    {
	        Log.e(TAG, "Unable to play audio queue do to exception: " + e.getMessage(), e);
	    }
	}

	public static void pause() {
		if(mp != null) {
		    if (mp.isPlaying()) {
		    	mp.pause();
		    }
		}
		
		isPaused = true;
	}
	
	public static void release() {
	    Log.d(TAG, "Releasing media players");
		try {
			if (mp != null) {
				if (mp.isPlaying()) {
					mp.stop();
				}
				mp.release();
				mp = null;
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}

		myContext = null;
		currentMusic = NO_MUSIC;
	}
	
	// Mute the music
	public static void mute() {
		if (mp != null) {
			mp.setVolume(0.0f, 0.0f);
		}
	}

	// Unmute the music
	public static void unmute() {
		if (mp != null) {
			mp.setVolume(1.0f, 1.0f);
		}
	}
	
	// Private function returning a random integer from range [0, arraySize-1],
	// used to select a random song to be played
	private static int getRandomSongId() {
		return (int)(Math.random() * arraySize);
	}
}
