package com.example.jmim.opengljustin02;


import javax.microedition.khronos.egl.EGLConfig;



import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;

import javax.microedition.khronos.opengles.GL10;





import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;




/**
 * Created by JMIM on 6/21/2015.
 */
public class MainRenderer implements Renderer
{

	/** our triangle we are testing out. **/
	Triangle t01;

	float colorPer=0;
	float otherColorPer=0;

	//PROBLEM #1:
	//Error In Class Definition:
	//class "MainRenderer" must either be declared abstract or
	//or implement abstract method onSurfaceCreated(GL10, EGLConfig);

	//FIX PART1: Put the @Override decorator over each function
	//           definition.

	//FIX PART2: Check your spelling.
	//           I wrote "onSurfaceChange" when I needed "onSurfaceChanged"

	@Override
	public void onDrawFrame(GL10 gl)
	{

		if(0 == ShaderLog.compileFlag)
		{
			colorPer += 0.01;
			if(colorPer >1){ colorPer = 0;}

			otherColorPer = 1.0f;
		}
		else
		{
			//on error: We will have DARK red screen:
			colorPer = 0.5f;
			otherColorPer = 0;
		}


		GLES20.glClearColor(colorPer, otherColorPer, 0.0f, 1.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT |
		               GLES20.GL_DEPTH_BUFFER_BIT);

		//draw the triangle:
		//BUG: This is NOT drawing to the screen....
		t01.draw();
	}

	@Override //<-- ERROR: method does not override from it's superclass.
	public void onSurfaceChanged(GL10 gl, int wid, int hig)
	{
		//bool compilerErrorFixHack = true;
	}

	@Override //<-- ERROR: method does not override from it's superclass.
	public void onSurfaceCreated(GL10 gl, EGLConfig fig)
	{
		t01 = new Triangle();
	}
}
