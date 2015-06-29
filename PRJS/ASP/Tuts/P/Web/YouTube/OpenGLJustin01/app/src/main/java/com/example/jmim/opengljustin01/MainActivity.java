package com.example.jmim.opengljustin01;

//Source URL is from youtube for this project:
//https://www.youtube.com/watch?v=OtQBTUZeVv8

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.Log;



/**
 * Created by JMIM on 6/21/2015.
 */
public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle sis)
    {
        //sis == saved instance state
        super.onCreate(sis);
        ActivityManager am =
        (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

		ConfigurationInfo cin = am.getDeviceConfigurationInfo();

        boolean hasES2 = (cin.reqGlEsVersion >= 0x20000);

        if(hasES2)
        {
			MainRenderer mRen = new MainRenderer();

			//Justin's Tutorial example wants us to supply
			//.this reference to MainSurfaceView constructor.
			//But android studio tells us that is wrong.
			//
			//UPDATE: Looks like you have to create a constructor
			//for MainSurfaceView so that it can take
			//the Activity as some sort of Context.

			MainSurfaceView mSur = new MainSurfaceView(this);
			mSur.setEGLContextClientVersion(2);
			mSur.setRenderer(mRen);

			this.setContentView(mSur);

        }
		else
		{
			Log.e("OpenGLES2", "No openGL for you!");
		}


    }//onCreate
}//class
