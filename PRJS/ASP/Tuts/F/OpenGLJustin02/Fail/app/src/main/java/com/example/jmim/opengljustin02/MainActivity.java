package com.example.jmim.opengljustin02;

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
	protected void onCreate(Bundle si)
	{
		super.onCreate(si);

		ActivityManager am =
		(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo ci = am.getDeviceConfigurationInfo();

		boolean gl = (ci.reqGlEsVersion >= 0x20000);
		if(gl)
		{//We HAVE open GL capability:
			MainRenderer mRen = new MainRenderer();
			MainSurfaceView mSur = new MainSurfaceView(this);
			mSur.setEGLContextClientVersion(2);
			mSur.setRenderer(mRen);
			this.setContentView(mSur);
		}
		else
		{
			Log.e("BlaBla", "Device does not support OpenGLES2.0");
		}
	}

}
