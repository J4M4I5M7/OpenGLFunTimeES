package com.example.jmim.realtwodopengl;

import android.content.Context;
//import android.opengl.EGLConfig;
import javax.microedition.khronos.egl.EGLConfig;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * This is NOT fixed yet. But trying.
 *
 *
 * SOURCE TUTORIAL: http://androidblog.reindustries.com/
 *                  a-real-open-gl-es-2-0-2d-tutorial-part-1/
 * Created by JMIM on 6/23/2015.
 */
public class GLRenderer implements GLSurfaceView.Renderer
{
	/** Projection Matrix. **/
	private float[] prjMat = new float[16];

	/** View Matrix **/
	private float[] vewMat = new float[16];

	/** Projection AND View Matrix: **/
	private float[] pavMat = new float[16];

	//Geometric variables:
	public static float[] verticies;
	public static short[] indicies;
	public static FloatBuffer vertexBuffer;
	public static ShortBuffer drawListBuffer;

	//Our Screen resolution:
	float mScreenWid = 1280;
	float mScreenHig  = 768;

	//Misc:
	Context mContext; //<<Rendering context?
	long mLastTime; //<<prev var?
	int mProgram; //<<Program handle I think.

	public GLRenderer(Context c)
	{
		mContext = c;
		mLastTime = System.currentTimeMillis() + 100;
	}

	public void onPause()
	{
		//do stuff to pause the renderer.
	}

	public void onResume()
	{
		//do stuff to unpause the renderer.
		mLastTime = System.currentTimeMillis();
	}

	@Override
	public void onDrawFrame(GL10 unused)
	{
		//get the current time
		long now = System.currentTimeMillis();

		//We should make sure we are valid and sane:
		if(mLastTime > now){return;}

		//Get the amount of time the last frame took:
		long elapsed = now - mLastTime;

		//update our example:
		//TODO: update our example.

		//Render our example:
		//using projection and view matrix.
		Render(pavMat);
	}//end onDraw Frame function.

	private void Render(float[] m)
	{
		//clear screen and depth buffer, we have set
		//the clear color as black.
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT |
		               GLES20.GL_COLOR_BUFFER_BIT);

		//Get handle to vertex shader's vPosition member:
		int mPositionHandle =
		GLES20.glGetAttribLocation(riGraphicTools.sp_SolidColor, "vPosition");

		//enable generic vertex attribute array:
		GLES20.glEnableVertexAttribArray(mPositionHandle);

		//prepare the triangle coordinate data:
		GLES20.glVertexAttribPointer
		(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);

		//Get the handle to the shape's transformation matrix.
		int matHan = GLES20.glGetUniformLocation
		(riGraphicTools.sp_SolidColor, "uMVPMatrix");

		//Apply the projection and view transformation:
		GLES20.glUniformMatrix4fv(matHan, 1, false, m, 0);

		//Draw the triangle:
		GLES20.glDrawElements
		(GLES20.GL_TRIANGLES, indicies.length,
		GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		//Disable vertex array:
		GLES20.glDisableVertexAttribArray(mPositionHandle);

	}//end render function.

	@Override
	public void onSurfaceChanged(GL10 glIgnore, int wid, int hig)
	{
		//we need to know the current width and height of screen:
		mScreenWid = wid;
		mScreenHig = hig;

		//Redo the viewport, making it fullscreen.
		GLES20.glViewport(0,0,(int)mScreenWid, (int)mScreenHig);

		//Clear our matricies:
		for(int i = 0; i <16; i++)
		{
			prjMat[i] = 0.0f;
			vewMat[i] = 0.0f;
			pavMat[i] = 0.0f;
		}//next i.

		//Matrix.orthoM(prjMat,matOffset,left,right,bottom,top,near,far);
		//Setup our screen width and height for normal sprite translation:
		Matrix.orthoM(prjMat    , 0, 0f,
		              mScreenWid, 0.0f,  //RIGHT is == mScreenWid.
		              mScreenHig, 0, 50);//TOP is == mScreenHig.

		//Set the camera position (View Matrix)
		//Matrix.setLookAtM(matrix, matrixOS,
						//  eyeX, eyeY, eyeZ,
						//  cenX, cenY, cenZ,
						//  upX , upY , upZ );
		Matrix.setLookAtM(vewMat, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

		//Calculate the Projection and View transformation:
		//Zeros are offset values.
		//I like that result of multiplication goes into a 3rd matrix.
		//Because I heard A= A*B is UNSAFE for openGL standard matrix library.
		Matrix.multiplyMM(pavMat,0,prjMat, 0, vewMat, 0);


	}//end on surface changed function.

	@Override
	public void onSurfaceCreated(GL10 glIgnore, EGLConfig config)
	{
		//Create the triangle:
		SetupTriangle();

		//set the clear color to black:
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1);

		//Create the shaders:
		int vertexShader = riGraphicTools.loadShader
		(GLES20.GL_VERTEX_SHADER, riGraphicTools.vs_SolidColor);

		int fragmentShader = riGraphicTools.loadShader
		(GLES20.GL_FRAGMENT_SHADER, riGraphicTools.fs_SolidColor);

		//create empty shader program.
		//sp == shader program. I am guessing.
		riGraphicTools.sp_SolidColor =
		GLES20.glCreateProgram(); //empty program.

		//add vertex shader to the program:
		GLES20.glAttachShader(riGraphicTools.sp_SolidColor, vertexShader);

		//add fragment shader to the program:
		GLES20.glAttachShader(riGraphicTools.sp_SolidColor, fragmentShader);

		//link the program:
		//Creates OpenGL ES program executables:
		GLES20.glLinkProgram(riGraphicTools.sp_SolidColor);


		//Set our shader program:
		GLES20.glUseProgram(riGraphicTools.sp_SolidColor);


	}//End onSurface created function.


	public void SetupTriangle()
	{
		//We have create the verticies of our view:
		verticies = new float[]
		{
			10.0f, 200f, 0.0f,
			10.0f, 100f, 0.0f,
			100f , 100f, 0.0f
		};

		//loop in the android official tutorial
		//opengles why different order?
		indicies = new short[]{0,1,2};

		//The vertex buffer:
		//VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		ByteBuffer bb = ByteBuffer.allocateDirect
		(verticies.length * 4);

		bb.order(ByteOrder.nativeOrder());
		vertexBuffer = bb.asFloatBuffer();
		vertexBuffer.put(verticies);
		vertexBuffer.position(0);
		//VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV

		//initialize byte buffer for the draw list:
		//DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD
		ByteBuffer dlb = ByteBuffer.allocateDirect
		(indicies.length * 2);
		dlb.order(ByteOrder.nativeOrder());

		drawListBuffer = dlb.asShortBuffer();
		drawListBuffer.put(indicies);
		drawListBuffer.position(0);
		//DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD






	}//setup triangle function end.


}//end class.
