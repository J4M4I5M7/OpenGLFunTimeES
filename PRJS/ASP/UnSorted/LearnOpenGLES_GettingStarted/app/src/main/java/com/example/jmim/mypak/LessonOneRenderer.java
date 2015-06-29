package com.example.jmim.mypak;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by JMIM on 6/22/2015.
 */
public class LessonOneRenderer implements GLSurfaceView.Renderer
{

	//new class members.
	//In example called mTriangle1Vertices. I like this better.
	/** Store our model data in a float buffer. **/
	private final FloatBuffer triVerts01;

	//looks like example code does not have source
	//for these two triangles. Just leave them out for now.
	//private final FloatBuffer triVerts02;
	//private final FloatBuffer triVerts03;



	/**
	 * Store the view matrix. This can be thought of as our
	 * camera. This matrix transforms world space [......]
	 * it positions things relative to our eye.
	 */
	private float[] vewMat = new float[16];

	//Setting the perspective projection:
	//New class members:
	/** Store the projection matrix. This is used to project the scene onto a
	 *  2D viewport. **/
	private float[] prjMat = new float[16];

	/** Store the Model matrix. This matrix is used to move models from object space
	 *  (where each model can be thought of being located at the center of the univers)
	 *  to world space. **/
	private float[] modMat = new float[16];

	/**
	 *  called mMVPMatrix in example.
	 *  I will call it mvpMat to keep with 3 letter matrix names with
	 *  postfix "mat" convention.
	 *
	 *  Allocate storage for the final combined matrix.
	 *  This will be passed into the shader program. */
	private float[] mvpMat = new float[16];


	/** Magic number prevention. How many bytes are
	 *  in a single float value. 32Bit data types
	 *  8Bits per byte. 8*4 = 32. ARGB = 32bits. 4Bytes.
	 *  Everything checks out. */
	private final int mBytesPerFloat = 4;


	/** How many elements per vertex.
	 *  MULTIPLIED by how many bytes are taken up by each element.
	 *  In this example, each vertex contains 7 float values.
	 *  3 used for XYZ, 4 used for RGBA, for a total of 7 float values.
	 *  Each float takes up 4Bytes.
	 *  (We may want to check if that is actually 8 on 64Bit targets.)
	 * **/
	private final int mStrideBytes = 7 * mBytesPerFloat;

	/** Offset of the position data. **/
	private final int mPositionOffset = 0;

	/** Size of the position data in elements. **/
	private final int mPositionDataSize = 3;

	/** Offset of the color data. **/
	private final int mColorOffset = 3;

	/** Size of the color data in elements. **/
	private final int mColorDataSize = 4;

	//New class members:
	//Directions in Learn Open GL ES2.0 tutorial are ambigious
	//as to where this belongs.
	//////////////////////////////////////////////////////////
	/** This will be used to pass in the transformation matrix. **/
	private int mMVPMatrixHandle;

	/** This will be used to pass in model position information. **/
	private int mPositionHandle;

	/** This will be used to pass in model color information. **/
	private int mColorHandle;

	//////////////////////////////////////////////////////////



	/** Constructor where we will make the triangle data
	 *  that we want to render.
	 */
	public LessonOneRenderer()
	{

		/** this is NOT triVerts01 var. **/
		final float[] triVerts01_dat =
		{
		    //POS                  //COLOR
			//X,    Y,     Z,        R,    G,    B,    A
			-0.5f, -0.25f, 0.0f,     1.0f, 0.0f, 0.0f, 1.0f,
		     0.5f, -0.25f, 0.0f,     0.0f, 0.0f, 1.0f, 1.0f,
			 0.0f,  0.55f, 0.0f,     0.0f, 1.0f, 0.0f, 1.0f
		};

		//initialize our buffers:
		//We do our coding in Java on Android, but the underlying
		//implimentation if actually written in C. Before we
		//pass our data to OpenGL, we need to convert it to
		//a form that is is going to understand.
		//
		//Just remember: The ByteBuffer is an extra step
		//we need to do before passing data to OPENGL.
		//Basically for hardware/system compatibility
		//like endianess.
		//
		//Note: Float Buffers are SLOW on Froyo and moderately
		//faster on Gingerbread, so you dont want to be changing
		//them too often.
		triVerts01 = ByteBuffer.allocateDirect
		(triVerts01_dat.length * mBytesPerFloat).
		order(ByteOrder.nativeOrder()).asFloatBuffer();

		triVerts01.put(triVerts01_dat).position(0);



	}//constructor end.

	private void drawTriangle(final FloatBuffer triBuf)
	{
		//Pass in the position information:
		triBuf.position( mPositionOffset);
		GLES20.glVertexAttribPointer
		(mPositionHandle, mPositionDataSize,
		GLES20.GL_FLOAT, false,mStrideBytes, triBuf);

		GLES20.glEnableVertexAttribArray(mPositionHandle);

		//Pass in the color information:
		triBuf.position(mColorOffset);
		GLES20.glVertexAttribPointer
		(mColorHandle, mColorDataSize,
		GLES20.GL_FLOAT, false,mStrideBytes, triBuf);

		GLES20.glEnableVertexAttribArray(mColorHandle);

		//This multiplies the view matrix by the model matrix,
		//and stores the result in the MVP matrix
		//(Which currently contains model * view).
		Matrix.multiplyMM(mvpMat, 0, vewMat, 0, modMat, 0);


		//JMIM NOTE: For some reason I though matrix multiplication
		//           WITHOUT A TEMP varaible was NOT SAFE...
		//This multiplies the modelview matrix by the projection
		//matrix and stores the result in the MVP matrix.
		//(Which now contains model * view * projection)
		Matrix.multiplyMM(mvpMat, 0, prjMat, 0, mvpMat, 0);

		GLES20.glUniformMatrix4fv
		(mMVPMatrixHandle, 1, false, mvpMat, 0);

		GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,3);

		//we already did this earlier.
		// //Do you rememember those buffers we defined when we
		// //originally created our RENDERER??
		// //We are finally going to use them.
		// //Pass in the position information:
		// triBuf.position(mPositionOffset);
		//
		// GLES20.glVertexAttribPointer
		// (mPositionHandle, mPositionDataSize,
		// GLES20.GL_FLOAT, false, mStrideBytes, triBuf);
		//
		// GLES20.



	}//end drawTriangle.

	/**
	 * This method is called when the surface is first created.
	 * It is also called if we lose our surface context and it is
	 * later re-created by the system.
	 * @param gl
	 * @param config
	 */
	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config)
	{
		//initialize our shader code:
		LessonOneShader.init();

		//prevent compiler from complaining:
		ignoreCompileHack( glUnused );

		//Set the background clear color to gray:
		//JMIM NOTE: Why is ALPHA channel 50% opacity???
		//this would result in fading motion blurr effects
		//correct?
		GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);

		//position the eye behind the origin:
		//JMIM NOTE:
		//from looking at this... I am guessing
		//openGL is done in a Z-up world. Which is the
		//kind I am fond of.
		final float eyeX = 0.0f;
		final float eyeY = 0.0f;
		final float eyeZ = 1.5f;

		//We are looking into the distance:
		final float lookX = 0.0f;
		final float lookY = 0.0f;
		final float lookZ = -5.0f;

		//set up our vector. This is where our head
		//would be pointing were we holding the camera.
		final float upX = 0.0f;
		final float upY = 1.0f;
		final float upZ = 0.0f;

		//Set the view matrix. This matrix can be said to
		//represent the camera position.
		//NOTE: In OpenGL 1, a ModelView matrix is used.
		//which is a combination of a model and a view matrix.
		//In open GL 2, we  can keep track of these matricies
		//separately if we choose.
		Matrix.setLookAtM(
		vewMat, 0,
		eyeX, eyeY, eyeZ,  //EYE xyz
		lookX, lookY, lookZ,  //LOOK xyz
		upX, upY, upZ);  //UP xyz

		//On clear color:
		//I guess the clear color will be unused in this example
		//so it doesn't matter that it is transparent? Ok.

		//need to set up matricies:
		//1. model matrix: Places the model somewhere in the world.
		//2. view matrix: Places our CAMERA somewher ein the world.
		//3. Projects 3D image onto 2D plane. (our screen == plane)
		//More info: SongHo's OpenGL Tutorials.

		//make temp var for shader program handle so I don't have to type:
		int sph = LessonOneShader.shaderProgHandle;

		//set program handles. These will later be used to pass in values to the program.
		mMVPMatrixHandle = GLES20.glGetUniformLocation(sph, "u_MVPMatrix");
		mPositionHandle  = GLES20.glGetAttribLocation (sph, "a_Position");
		mColorHandle     = GLES20.glGetAttribLocation (sph, "a_Color");

		//Tell OpenGL to use this program when rendering:
		GLES20.glUseProgram(sph);

		//Think this paragraph is talking about code ABOVE.
		//AFTER we successfully link our program, we finish up with a copule more
		//tasks so we can actually use it. The first task is obtaining references so
		//we can pass data into the program. Then we tell OpenGL to use this
		//program when drawing. Since we only use one program in this lesson, we can put
		//this in the onSurfaceCreated() instead of the onDrawFrame().




	}

	/** This is called whenever our surface changes. For
	 *  example, when switching from portrait to landscape.
	 *  It is also called after the surface has been created.
	 * @param gl
	 * @param wid
	 * @param hig
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int wid, int hig)
	{
		//Set the OpenGL viewport to the same size as the surface:
		GLES20.glViewport(0,0,wid,hig);

		//create a new perspecitive projection matrix. The height will stay
		//the same while the width will vary as per aspect ratio.
		final float ratio  = (float)(wid/hig);
		final float left   = -ratio;
		final float right  = ratio;
		final float bottom = -1.0f;
		final float top    =  1.0f;
		final float near   =  1.0f;
		final float far    =  10.0f;

		//java.lang.IllegalArgumentException: left == right
		//at android.opengl.Matrix.frustumM(Matrix.java:320)
		//at com.example.jmim.mypak.LessonOneRenderer.onSurfaceChanged(LessonOneRenderer.java:309)
		//at android.opengl.GLSurfaceView$GLThread.guardedRun(GLSurfaceView.java:1548)
		//at android.opengl.GLSurfaceView$GLThread.run(GLSurfaceView.java:1281)
		Matrix.frustumM(prjMat, 0, left, right, bottom, top, near, far);

	}

	/**
	 * This is called whenever it's time to draw a new frame.
	 * @param gl
	 */
	@Override
	public void onDrawFrame(GL10 gl)
	{
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

		//Do a complete rotation every 10 seconds:
		long time = SystemClock.uptimeMillis() % 10000L;
		float angleInDegrees = (360.0f / 10000.0f) * ((int) time);

		//Draw the triangle facing straight on:
		Matrix.setIdentityM(modMat, 0);
		Matrix.rotateM(modMat, 0, angleInDegrees, 0.0f, 0.0f, 1.0f);
		drawTriangle(triVerts01);
	}

	private static void ignoreCompileHack(GL10 glIgnore)
	{
		//Do nothing.
	}

}//End Class. Lesson One Renderer.
