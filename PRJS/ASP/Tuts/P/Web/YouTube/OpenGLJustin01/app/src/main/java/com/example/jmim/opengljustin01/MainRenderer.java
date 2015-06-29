package com.example.jmim.opengljustin01;

//Source URL is from youtube for this project:
//https://www.youtube.com/watch?v=OtQBTUZeVv8

//NOT THIS ONE!
//If you use this one, it will tell you that the class
//is not correctly implemented.
//import android.opengl.EGLConfig;


import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import javax.microedition.khronos.opengles.GL10;

//import javax.microedition.khronos.egl.EGL10;
//import javax.microedition.khronos.egl.EGL11;
import javax.microedition.khronos.egl.EGLConfig;
//import javax.microedition.khronos.egl.EGLContext;
//import javax.microedition.khronos.egl.EGLDisplay;
//import javax.microedition.khronos.egl.EGLSurface;
//import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;


//import javax.microedition.khronos.opengles.GL10;

/**
 * Created by JMIM on 6/21/2015.
 */
public class MainRenderer implements Renderer
{

	@Override
	public void onDrawFrame(GL10 arg0)
	{
		GLES20.glClearColor(0.8f, 0.0f, 0.0f, 1.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT |
				       GLES20.GL_DEPTH_BUFFER_BIT);
	}

	@Override
	public void onSurfaceChanged(GL10 arg0, int arg1, int arg2)
	{

	}

	@Override
	public void onSurfaceCreated(GL10 arg0, EGLConfig arg1)
	{
		//super.onSurfaceCreated(arg0, arg1);
	}
}
