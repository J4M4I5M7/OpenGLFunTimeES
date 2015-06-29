package com.example.jmim.mypak;

import android.opengl.GLES20;

/**
 * Termonology: UNIFORM: A combined matrix containing all of our transformations.
 *
 *
 * So... This super comprehensive tutorial that I think holds
 * the answer for me is actually more convoluted than I thought.
 * The big problem with it is it doesn't always tell me what
 * classes the code belongs in.
 *
 * So I am taking a lot of guesses.
 * Kind of glad that I did those other tutorials.
 *
 * Created by JMIM on 6/22/2015.
 */
public class LessonOneShader
{

	/** calling it "EvalCode" so I know it
	 *  is a string that will be evaluated.
	 */
	final static String vertShader_EvalCode=
	"uniform mat4 u_MVPMatrix;     \n"+ //const representing model+view+prj matrix.

	"attribute vec4 a_Position;    \n"+ //Per-vertex position information
	                                    //we will pass in.

	"attribute vec4 a_Color;       \n"+ //Per-vertex color information we will
	                                    //pass in.

	"varying vec4 v_Color;         \n"+ //This will be passed into the fragment
	                                    //shader.

	"void main()                   \n"+  //entry point of vertex shader.
	"{                             \n"+
	"    v_Color = a_Color;        \n"+  //pass color to fragment shader.
	                                     //it will be interpolated across the
	                                     //triangle.

	"    gl_Position = u_MVPMatrix \n"+  //gl_Position is a special variable
	                                     //used to store the final position.

	"                * a_Position; \n"+ //Multiply the vertex by the matrix to
	"}                             \n"; //get the final point in normalized
	                                    //screen coordinates.

	final static String fragShader_EvalCode=
	"precision mediump float;        \n"+ //Set the default precision to medium.
	                                      //we don't need as high of a precision
	                                      //in the fragment shader.

	"varying vec4 v_Color;           \n"+ //This is the color from the vertex shader
	                                      //interpolated across the triangle per
	                                      //fragment.

	"void main()                     \n"+ //Entry point of our fragment shader.
	"{                               \n"+
	"    gl_FragColor = v_Color;     \n"+ //Pass the color directly though the
	"}                               \n"; //Pipeline with no modifications.

	public static int vertShaderHandle;
	public static int fragShaderHandle;
	public static int shaderProgHandle; //

	public static void init()
	{
		makeShaderProgram();
	}

	/** Creates vertex and fragment shader and then links them
	 *  into a program.
	 */
	private static void makeShaderProgram()
	{

		//create the shaders we need to link before doing steps:
		makeVertexShader();
		makeFragmentShader();

		//create a program object and store the handle to it:
		shaderProgHandle = GLES20.glCreateProgram();

		if(shaderProgHandle != 0)
		{
			//Bind the vertex shader to the program:
			GLES20.glAttachShader(shaderProgHandle, vertShaderHandle);

			//Bind the fragment shader to the program:
			GLES20.glAttachShader(shaderProgHandle, fragShaderHandle);

			//Bind attributes:
			GLES20.glBindAttribLocation(shaderProgHandle, 0, "a_Position");
			GLES20.glBindAttribLocation(shaderProgHandle, 1, "a_Color");

			//Link the two shaders together into a program:
			GLES20.glLinkProgram(shaderProgHandle);

			//Get the link status:
			final int[] linkStatus = new int[1];
			GLES20.glGetProgramiv
			(shaderProgHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

			//If the link failed, delete the program:
			if(0 == linkStatus[0] )
			{
				GLES20.glDeleteProgram(shaderProgHandle);
				shaderProgHandle = 0;
			}
		}//if shader prog handle is NOT zero.

		if(0==shaderProgHandle)
		{
			throw new RuntimeException("Error creating program.");
		}

	}//make shader program. End of function.

	/** makes shader program and returns handle. **/
	private static void makeVertexShader()
	{
		//load in the vertex shader:
		vertShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
		if(vertShaderHandle != 0)
		{
			//Pass in the shader source:
			GLES20.glShaderSource(vertShaderHandle, vertShader_EvalCode);

			//Compile the shader:
			GLES20.glCompileShader(vertShaderHandle);

			//Get the compilation status:
			final int[] compileStatus = new int[1];
			GLES20.glGetShaderiv
			(vertShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

			//if the compilation failed, delete the shader:
			if(compileStatus[0] == 0)
			{
				GLES20.glDeleteShader(vertShaderHandle);
				vertShaderHandle = 0;
			}

		}

		if(0==vertShaderHandle)
		{
			throw new RuntimeException("Error creating vertex shader.");
		}


	}//make vertex shader.

	private static void makeFragmentShader()
	{
		fragShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
		if(fragShaderHandle != 0)
		{
			GLES20.glShaderSource(fragShaderHandle, fragShader_EvalCode);
			GLES20.glCompileShader(fragShaderHandle);
			final int[] stat = new int[1];
			GLES20.glGetShaderiv
			(fragShaderHandle, GLES20.GL_COMPILE_STATUS, stat, 0);

			if(0==stat[0] )
			{
				GLES20.glDeleteShader(fragShaderHandle);
				fragShaderHandle = 0;
			}
		}

		if(0 == fragShaderHandle)
		{
			throw new RuntimeException("Error creating fragment shader.");
		}
	}//make fragment shader end.


}//Lesson One Shader class.

