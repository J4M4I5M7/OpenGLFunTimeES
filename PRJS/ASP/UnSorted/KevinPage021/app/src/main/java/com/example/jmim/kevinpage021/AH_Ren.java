package com.example.jmim.kevinpage021;

//import android.opengl.GLSurfaceView;

//Static var imports???
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


/**
 * Created by JMIM on 6/28/2015.
 */
public class AH_Ren implements Renderer
{
	//2 components per position vertex:
	private static final int POSITION_COMPONENT_COUNT = 2;

	private static final int BYTES_PER_FLOAT = 4;

	/** Our vertex data array. **/
	private final FloatBuffer vData;

	/** constructor. **/
	public AH_Ren()
	{
		/** Vertices defining our table. tVerts == Table Verts. **/
		float[] tVerts =
		{
			//Triangle 1:
			0f, 0f,
			9f, 14f,
			0f, 14f,

			//Triangle 2:
			0f, 0f,
			9f, 0f,
			9f, 14f,

			//Line 1:
			0f, 7f,
			9f, 7f,

			//Mallets:
			4.5f, 2f,
			4.5f, 12f
		};

		//Allocate space in byte buffer:
		vData = ByteBuffer.allocateDirect
		(tVerts.length * BYTES_PER_FLOAT)
		.order(ByteOrder.nativeOrder())
		.asFloatBuffer();

		//Put the verts into byte buffer:
		vData.put(tVerts);

		//Endianness on page 29 of Kevin's book.

	}//end constructor.


	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(1.0f,0.0f,1.0f,0.0f);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		glViewport(0,0,width,height);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		glClear(GL_COLOR_BUFFER_BIT);
	}
}//End Class "Ren".