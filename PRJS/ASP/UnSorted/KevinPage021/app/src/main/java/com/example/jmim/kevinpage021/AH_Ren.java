package com.example.jmim.kevinpage021;

//import android.opengl.GLSurfaceView;

//Static var imports???
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

import com.example.jmim.kevinpage021.com.example.jmim.kevinpage021.util.LoggerConfig;
import com.example.jmim.kevinpage021.com.example.jmim.kevinpage021.util.ShaderHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;



/**
 * Created by JMIM on 6/28/2015.
 */
public class AH_Ren implements Renderer
{
	//added on page 49:
	private static final String A_POSITION = "a_Position";
	private int aPositionLocation;


	///U_COLOR and uColorLocation added on page 48:
	private static final String U_COLOR = "u_Color";
	private int uColorLocation;

	/** Store ID of our linked shader program. Page 46. **/
	private int program;

	private final Context context;

	//2 components per position vertex:
	private static final int POSITION_COMPONENT_COUNT = 2;

	private static final int BYTES_PER_FLOAT = 4;

	/** Our vertex data array. **/
	private final FloatBuffer vData;

	/** constructor. **/
	public AH_Ren(Context context)
	{
		//set context: Page 39:
		this.context = context;

		float n = (0-0.5f);
		float p = 0.5f;
		float z = 0f;

		/** negative half. **/
		float nH = 0-0.25f;

		/** positive half. **/
		float pH = 0.25f;

		/** Vertices defining our table. tVerts == Table Verts. **/
		float[] tVerts =
		{
			//Triangle 1:
			n, n,
			p, p,
			n, p,

			//Triangle 2:
			n,n,
			p,n,
			p,p,

			//Line 1:
			n,z,
			p,z,

			//Mallets:
			z,nH,
			z,pH
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
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		glClearColor(1.0f,0.0f,1.0f,0.0f);

		//What is the "context" variable on page 38 referring to?
		//var context:EGLContext

		String vertexShaderSource = TextResourceReader
		.readTextFileFromResource(context,R.raw.simple_vertex_shader);

		String fragmentShaderSource = TextResourceReader
		.readTextFileFromResource(context,R.raw.simple_fragment_shader);

		//Page 43 of Kevin's Book:
		int hVS = ShaderHelper.compileVertexShader(vertexShaderSource);
		int hFS = ShaderHelper.compileFragmentShader(fragmentShaderSource);

		//page 46:
		program = ShaderHelper.linkProgram(hVS,hFS);

		//Page 47:
		if(LoggerConfig.ON)
		{
			ShaderHelper.validateProgram(program);
		}

		//Yeah! Use this program when drawing to the screen:
		glUseProgram(program);

		//Page 48:
		uColorLocation = glGetUniformLocation(program, U_COLOR);

		//Page 49:
		aPositionLocation = glGetAttribLocation(program, A_POSITION);

		//Tell OpenGL where to find the data for our attribute a_Position.
		//Wait.. is not aPositionLocation already doing that?! Page 49.
		vData.position(0);
		glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT,
		GL_FLOAT, false, 0, vData);

		//Page 51: Enableing the Vertex Array:
		glEnableVertexAttribArray(aPositionLocation);


	}//end on surface created.

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		glViewport(0,0,width,height);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		glClear(GL_COLOR_BUFFER_BIT);

		//page 51: glDrawArrays will draw TWO(2) triangles
		//because it has been instructed to read in SIX(6) verticies.
		glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
		glDrawArrays(GL_TRIANGLES, 0, 6);

		//Drawing dividing lines: Page 52:
		glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 0.0f);
		glDrawArrays(GL_TRIANGLES, 6, 2);

		//blue mallet:
		glUniform4f(uColorLocation,0.0f,0.0f,1.0f,1.0f);
		glDrawArrays(GL_POINTS, 8, 1);

		//red mallet:
		glUniform4f(uColorLocation,1.0f, 0.0f, 0.0f, 1.0f);
		glDrawArrays(GL_POINTS, 9, 1);



	}
}//End Class "Ren".