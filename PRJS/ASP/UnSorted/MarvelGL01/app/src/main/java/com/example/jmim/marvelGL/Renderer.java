package com.example.jmim.marvelGL;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import java.nio.ByteOrder;

// RESOURCE #1:
//https://makegameshappen.wordpress.com/2015/06/22/opengl-triangle-android/

/**
 * Created by JMIM on 6/22/2015.
 */
public class Renderer implements GLSurfaceView.Renderer
{


	private FloatBuffer vertBuffer;

	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		//At18:02 of RESOURCE #1:
		Shader.makeProgram();
		GLES20.glEnableVertexAttribArray(Shader.positionHandle);

		float[] verts =
		{
			0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f,
			1.0f, 1.0f, 0.0f
		};

		vertBuffer = makeFloatBuffer(verts);


	}


	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		GLES20.glViewport(0,0,width,height);
	}


	public void onDrawFrame(GL10 gl)
	{
		//JMIM EDIT: No triangle when I did this:
		//int theStride = 3*4;

		//JMIM EDIT: Guess: 8 bytes per float on 64 bit operating system?
		//int theStride = 3 * 8;


		//one more attempt. Use Float.SIZE to get size in bytes of a float.
		//rather than using magic numbers:
		//int theStride = 3 * Float.SIZE;

		//No luck with any... just set stride back to ZERO in this project.
		int theStride = 0;


		GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		GLES20.glUniform4f(Shader.colorHandle, 1.0f, 0.0f, 0.0f, 1.0f);
		GLES20.glVertexAttribPointer(Shader.positionHandle,3, GLES20.GL_FLOAT,
		false, theStride, vertBuffer);


		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

	}//onDrawFrame

	public FloatBuffer makeFloatBuffer(float[] fArr)
	{
		FloatBuffer fb = ByteBuffer.allocateDirect(fArr.length * 4).
		order(ByteOrder.nativeOrder()).asFloatBuffer();

		fb.put(fArr).position();

		//@27:12 of RESOURCE_01:
		//return the float buffer we have created.
		return fb;
	}
}
