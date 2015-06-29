package com.example.jmim.loglct01;

import android.opengl.GLES20;

/**
 * Created by JMIM on 6/25/2015.
 */
public class Shader
{

	public static int fragmentShaderHandle;
	public static int vertexShaderHandle;
	public static int programHandle;

	public static void buildShaders()
	{
		/** calling it "EvalCode" so I know it
		 *  is a string that will be evaluated.
		 */
		final String vertShader_EvalCode=
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

		final String fragShader_EvalCode=
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





		// Load in the vertex shader.
		vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);

		if (vertexShaderHandle != 0)
		{
			// Pass in the shader source.
			GLES20.glShaderSource(vertexShaderHandle, vertShader_EvalCode);

			// Compile the shader.
			GLES20.glCompileShader(vertexShaderHandle);

			// Get the compilation status.
			final int[] compileStatus = new int[1];
			GLES20.glGetShaderiv(vertexShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

			// If the compilation failed, delete the shader.
			if (compileStatus[0] == 0)
			{
				GLES20.glDeleteShader(vertexShaderHandle);
				vertexShaderHandle = 0;
			}
		}

		if (vertexShaderHandle == 0)
		{
			throw new RuntimeException("Error creating vertex shader.");
		}

		// Load in the fragment shader shader.
		fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

		if (fragmentShaderHandle != 0)
		{
			// Pass in the shader source.
			GLES20.glShaderSource(fragmentShaderHandle, fragShader_EvalCode);

			// Compile the shader.
			GLES20.glCompileShader(fragmentShaderHandle);

			// Get the compilation status.
			final int[] compileStatus = new int[1];
			GLES20.glGetShaderiv(fragmentShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

			// If the compilation failed, delete the shader.
			if (compileStatus[0] == 0)
			{
				GLES20.glDeleteShader(fragmentShaderHandle);
				fragmentShaderHandle = 0;
			}
		}

		if (fragmentShaderHandle == 0)
		{
			throw new RuntimeException("Error creating fragment shader.");
		}

		// Create a program object and store the handle to it.
		programHandle = GLES20.glCreateProgram();

		if (programHandle != 0)
		{
			// Bind the vertex shader to the program.
			GLES20.glAttachShader(programHandle, vertexShaderHandle);

			// Bind the fragment shader to the program.
			GLES20.glAttachShader(programHandle, fragmentShaderHandle);

			//JMIM NOTE: Spelling "a_Position" wrong does not affect program results.
			//JMIM NOTE: Spelling "a_Color" wrong does not affect program results.
			//What is it for?
			// Bind attributes
			GLES20.glBindAttribLocation(programHandle, 0, "a_Position");
			GLES20.glBindAttribLocation(programHandle, 1, "a_Color");

			// Link the two shaders together into a program.
			GLES20.glLinkProgram(programHandle);

			// Get the link status.
			final int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

			// If the link failed, delete the program.
			if (linkStatus[0] == 0)
			{
				GLES20.glDeleteProgram(programHandle);
				programHandle = 0;
			}
		}

		if (programHandle == 0)
		{
			throw new RuntimeException("Error creating program.");
		}
	}//build shaders function.

}//class.
