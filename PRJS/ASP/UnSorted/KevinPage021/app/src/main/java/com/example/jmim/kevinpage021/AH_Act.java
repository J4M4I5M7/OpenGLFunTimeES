package com.example.jmim.kevinpage021;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.app.Activity;
import android.widget.Toast;


/**
 * Act == Activity.
 * Created by JMIM on 6/28/2015.
 */
public class AH_Act extends Activity
{
	/** The view object responsible for rendering. **/
	private GLSurfaceView vew;

	/** Do we have a renderer set for the surface view? **/
	private boolean hasRen = false;

	/**
	 *
	 * @param sis : Saved instance state.
	 */
	@Override
	public void onCreate(Bundle sis)
	{
		super.onCreate(sis);


		//removed and replaced with openGL surface view code:
		//setContentView(R.layout.layout_act);






		//do we have open GL ES2.0 on this machine:
		final boolean hasES2 = decideIfHasES2();

		if(hasES2)
		{
			vew = new GLSurfaceView(this);
			vew.setEGLContextClientVersion(2);

			//Do this before set renderer:
			vew.setEGLConfigChooser(8,8,8,8,16,0);

			/**This object IS the context. Page 39. **/
			vew.setRenderer(new AH_Ren(this) );
			hasRen = true;
			setContentView(vew);
		}
		else
		{
			//A toast is a non-clickable message box that times out
			//and vanishes automatically.
			//http://developer.android.com/guide/topics/ui/notifiers/toasts.html
			//Bail out and show message:
			Toast.makeText
			(this,"This device does not support OpenGL ES2.0",
			Toast.LENGTH_LONG).show();
			//setContentView(R.layout.layout_act);
			return;
		}





	}//on create end.

	private boolean decideIfHasES2()
	{
		final ActivityManager actMan =
		(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

		final ConfigurationInfo conFig =
		actMan.getDeviceConfigurationInfo();

		if(conFig.reqGlEsVersion >= 0x20000){ return true;}
		if(getIsEmulator()){ return true;}
		return false;
	}

	private boolean getIsEmulator()
	{
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
		{
			if(Build.FINGERPRINT.startsWith("generic")){return true;}
			if(Build.FINGERPRINT.startsWith("unknown")){return true;}
			if(Build.MODEL.contains("google_sdk")){return true;}
			if(Build.MODEL.contains("Emulator")){return true;}
			if(Build.MODEL.contains("Android SDK built for x86")){return true;}
		}

		//OtherWise Return False:
		return false;

	}//getIsEmulator.

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		//Documentation ON MenuInflater:
		//http://developer.android.com/reference/android/view/MenuInflater.html
		getMenuInflater().inflate(R.menu.menu_act, menu);
		return true;
	}

	@Override
	public void onPause()
	{
		super.onPause();

		//PAGE 10 and 11:
		//This gaurd is strange.
		//But now I see why MY guard did not work.
		//If has RENDERER, the pause the VIEW (which uses rendere)
		//check for "hasVew" instead of "hasRen" would not have
		//same behavior.
		if(hasRen)
		{
			vew.onPause();
		}
	}

	@Override
	public void onResume()
	{

		super.onResume();

		if(hasRen)
		{
			vew.onResume();
		}
	}


}//class

