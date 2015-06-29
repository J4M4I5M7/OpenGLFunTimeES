package com.example.jmim.realtwodopengl;

import android.opengl.GLSurfaceView;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by JMIM on 6/23/2015.
 */
public class GLSurf extends GLSurfaceView
{
	private final GLRenderer mRenderer;

	/** hackish guard variable to try to avoid null pointer
	 *  exceptions and get this code running.*/
	private boolean hasRenderer = false;


	public GLSurf(Context context)
	{
		super(context);

		//set context to 2.0:
		setEGLContextClientVersion(2);

		//Trying this to fix bug:
		setEGLConfigChooser(8,8,8,8,16,0);

		//create renderer, and set view to use it:
		mRenderer = new GLRenderer(context);
		hasRenderer = true;
		if(null == mRenderer)
		{
			throw new Error("mRenderer, false positive flag detected.");
		}

		setRenderer(mRenderer);

		//render the view only when there is a change
		//in the drawing data:
		setRenderMode
		(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

		//RenderMode CONTINUOUSLY is the only option
		//for games. Because there is always something
		//dynamic happening on screen that will need
		//to be updated.

		//(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

	}//END FUNC.

	@Override
	public void onPause()
	{
		super.onPause();


		if(hasRenderer)
		{
			mRenderer.onPause();
		}

	}

	@Override
	public void onResume()
	{
		super.onResume();

		if(hasRenderer)
		{
			mRenderer.onResume();
		}

	}

}//Class ENd.
