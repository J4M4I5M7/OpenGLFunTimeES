package com.example.jmim.realtwodopengl;


//for this tutorial:
//http://androidblog.reindustries.com/
// a-real-open-gl-es-2-0-2d-tutorial-part-1/

import android.app.Activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import org.apache.commons.logging.Log;

/**
 * Created by JMIM on 6/23/2015.
 */
public class MainActivity extends Activity
{
	//Our OpenGL SurfaceView:
	protected GLSurfaceView glSurfaceView;

	/** Hack to attempt to get past null pointer exception. **/
	protected boolean hasSurfaceView = false;

	@Override
	protected void onCreate(Bundle savedInstance)
	{
		//Turn off the window's title bar:
		//requestWindowFeature(Window.FEATURE_NO_TITLE);

		//Super:
		super.onCreate(savedInstance);

		/*
		//Fullscreen mode:
		getWindow().setFlags
		(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */

		//We create our new GL Surface view for our OpenGL here:
		glSurfaceView = new GLSurfaceView(this); //typo in tutorial.
		hasSurfaceView = true;
		if(null == glSurfaceView)
		{
			throw new Error("glSurfaceView, false positive flag detected.");
		}

		//Commenter Otebej abcde says to remove this:
		/*
		//set our view:
		//this is NOT applicable in our no-activity project.
		setContentView(R.layout.doritolayout); //<--original tutorial code.
		//setContentView(glSurfaceView);//<--Hack by Andi Schmid.

		//ALSO NOT APPLICABLE with blank activity:
		//Retrieve our relative doritolayout from our main doritolayout we
		//just set to our view:
		   RelativeLayout doritolayout = (RelativeLayout)
	       findViewById(R.id.doritolayout);

		//Attach our surface view to our relative doritolayout from our
		//main doritolayout:
		RelativeLayout.LayoutParams glParams =
		new RelativeLayout.LayoutParams
		(RelativeLayout.LayoutParams.MATCH_PARENT,
		RelativeLayout.LayoutParams.MATCH_PARENT);
		//
		doritolayout.addView(glSurfaceView, glParams);
		*/

		//Commenter Otebej abcde says to ADD THIS in it's place:
		setContentView(glSurfaceView);


	}//onCreate end.

	@Override
	protected void onPause()
	{
		super.onPause();


		if(hasSurfaceView)
		{
			glSurfaceView.onPause();
		}

	}

	@Override
	protected  void onResume()
	{
		super.onResume();

		if(hasSurfaceView)
		{
			//for some reason, our gaurd variable seems not to be working.
			//let's confirm this with a log:
			if(null == glSurfaceView)
			{
				//android.util.Log.println(0,"debug","glSurfaceView is null!");
				//System.out.println("please print me in the console");

				//android.util.Log.v("LogCat", "Bad Times Dude");
				throw new Error("What the hell man???");
			}

			glSurfaceView.onResume();
		}

	}


}//end class
