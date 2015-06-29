package com.example.jmim.marvelGL;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

/**
 * Created by JMIM on 6/22/2015.
 */
public class MainActivity extends Activity
{

	private GLSurfaceView sur;

	public void onCreate(Bundle bun)
	{
		super.onCreate(bun);
		sur = new GLSurfaceView(this);
		sur.setEGLContextClientVersion(2);
		sur.setRenderer( new Renderer() );

		setContentView(sur);
	}


	public void onPause()
	{
		super.onResume();
		sur.onPause();
	}

	public void onResume()
	{
		super.onResume();
		sur.onResume();
	}
}
