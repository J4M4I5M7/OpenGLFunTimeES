package com.example.jmim.mypak;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

/**
 * Created by JMIM on 6/22/2015.
 */
public class LessonOneActivity extends Activity
{
	/**
	 * sVew for "Surface View"
	 * In the actual example the variable name is "mGLSurfaceView".
	 * But that is a mouth full.
	 *
	 * Why do we want to hold a reference to self?
	 *  Answer: We originally put this code in the surface-view class.
	 *  which it did NOT belong in.
	 *  http://www.learnopengles.com/android-lesson-one-getting-started/#comment-2480
	 * **/
	private GLSurfaceView sVew;

	/**
	 *
	 * @param b: Our saved instance state.
	 */

	public void onCreate(Bundle b)
	{


		super.onCreate(b);
		sVew = new MySVew(this);
		sVew.setEGLContextClientVersion(2);
		sVew.setRenderer( new LessonOneRenderer() );
		setContentView(sVew);

       /*
		////Try this snippet from working code if problem.
		////This block is from another project.
		 super.onCreate(b);
		 sVew = new GLSurfaceView(this);
		 sVew.setEGLContextClientVersion(2);
		 sVew.setRenderer( new LessonOneRenderer() );
		 setContentView(sVew);
		*/


		/*
		super.onCreate(b);


		//This is also really weird...
		//isn't this infinite recursion???
		//Not sure it is infinite recursion. But is IS crashing my app.
		sVew = new GLSurfaceView(this);



		//check if the system supports OpenGL ES 2.0:
		final ActivityManager am =
		(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

		final ConfigurationInfo ci = am.getDeviceConfigurationInfo();
		final boolean tf = ci.reqGlEsVersion > 0x0020000;

		//Crashes BEFORE here.


		if(tf)
		{ //true == supports open GL ES 2.0

			//Request and OPEN GL ES 2.0 compatable context:
			sVew.setEGLContextClientVersion(2);

			//Set the renderer to our demo renderer:
			sVew.setRenderer(new LessonOneRenderer());

		}
		else
		{
			//This is where you could create an OPEN GL ES 1.x compatible
			//renderer if you wanted to support both ES1 and ES2.
			return;
		}

		//UPDATE: We had this code in the surface-view, when
		//        it belonged in the activity class.
		//cannot resolve this function.
		//Are we in the correct file/class?
		//for this example code?
		setContentView(sVew);
		*/

	}//onCreate END


	protected void onResume()
	{
		//The activity must call the GL surface view's onResume()
		//on activity onResume()
		super.onResume();

		if(null != sVew)
		{
			sVew.onResume();
		}

	}


	protected void onPause()
	{
		//The activity must call the GL Surface view's onPause() on
		//activity onPause()
		//JMIM NOTE: These comments are telling me we probably should
		//have all this code in ACTIVITY class. not SurfaceView class.
		super.onPause();

		if(null != sVew)
		{
			sVew.onPause();
		}



	}

}//end lesson one activity class.
