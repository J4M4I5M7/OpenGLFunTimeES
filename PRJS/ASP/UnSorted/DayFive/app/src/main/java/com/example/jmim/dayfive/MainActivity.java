package com.example.jmim.dayfive;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

//Info on the "R" object import:
//http://stackoverflow.com/questions/
//25808260/android-studio-and-r-class
import com.example.jmim.dayfive.R;

/**
 * Created by JMIM on 6/24/2015.
 */
public class MainActivity extends Activity
{

	private Stage stage;
	/**
	 * @param sis : Saved instance state.
	 */
	@Override
	public void onCreate(Bundle sis)
	{
		super.onCreate(sis);
		setContentView(R.layout.main_layout);

		//Games usually have these behaviors:
		//1: They are full-screen.
		//2: They override normal back-light and screen
		//   behavior and make screen always on.
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		//JMIM NOTE: About Above: Why do we have BOTH FullScreen and Not-FullScreen flags?

		stage =(Stage)findViewById(R.id.my_stage);

	}//end onCreate

	@Override
	protected  void onPause()
	{
		super.onPause();
		stage.onPause(); //Stop open-gl from rendering when activity is paused.
		                 //Don't abuse user's processor.
	}

	@Override
	protected void onResume()
	{
		super.onPause();
		stage.onPause();
	}


}//class
