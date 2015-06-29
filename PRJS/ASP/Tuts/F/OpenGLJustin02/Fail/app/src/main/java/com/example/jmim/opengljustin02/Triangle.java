package com.example.jmim.opengljustin02;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

//Problem: LoadShader is not a recognized command.
//Solution: check your case. You declared a "loadShader" command
//which you are then using... But tried calling "LoadShader"

//needed to create our vertex buffer "vBuf":
import java.nio.FloatBuffer;


/**
 * Created by JMIM on 6/21/2015.
 */
public class Triangle {
	/**
	 * vBuf == Vertex Buffer. *
	 */
	private FloatBuffer vBuf;

	//vertex array requires "=" sign to declare.
	//http://stackoverflow.com/questions/1200621/declare-array-in-java
	private float[] vArr =
	{
	0.0f, 0.5f, 0.0f,
	-0.5f, -0.5f, 0.0f,
	0.5f, -0.5f, 0.0f
	};

	private float[] color =
	{1.0f, 0.6f, 1.0f, 1.0f};
	//r     g     b     a  //note: Different from AS3 which uses ARGB order.


	//Hack the shader code from the digipen talk into
	//this tutorial and see what we can get done.
	private final String digiVertexShaderCode=
	"attribute vec4 vPosition;" +
	"uniform mat4 uMVP;" +
	"void main(){" +
	"gl_Position = vPosition * uMVP;" +
	"}";

	private final String digiFragmentShaderCode=
	"precision mediump float;" +
	"void main(){" +
	"gl_FragColor = vec4(1.0,0.0,0.0,1.0);"+
	"}";

	private final String vertexShaderCode = digiVertexShaderCode;
	private final String fragmentShaderCode = digiFragmentShaderCode;

/*
	private final String vertexShaderCode =
	"attribute vec4 vPosition;" +
	"void main(){" +
	"gl_position vPosition;" +
	"}";

	//mediump looks like a typo. But it stand for "medium precision"
	//http://stackoverflow.com/questions/13780609/
	//what-does-precision-mediump-float-mean
	private final String fragmentShaderCode =
	"precision mediump float;" +
	"uniform vec4 vColor;" +
	"void main() {" +
	"gl_FragColor vColor;" +
	"}";

	*/



	/**
	 * Holds an integer ID that we use to reference
	 * the shader program. *
	 */
	private int shaderProgram;

	/**
	 * This function loads shaders. What exactly that means,
	 * You don't need to know yet.
	 *
	 * @param type            :Enum to identify type of shader I guess.
	 * @param shaderCode:Most likely your vertexShaderCode string
	 *                        or your fragmentShaderCode string in this
	 *                        example project.
	 * @return Returns an integer that we need to keep in memory
	 * for life of program. Believe it is an ID used
	 * to reference the compiled shader.
	 */
	public static int loadShader(int type, String shaderCode) {
		//compile shader:
		int shader = GLES20.glCreateShader(type);

		//I am guessing this is setting the shader as the active
		//shader to be used by OPENGL. From the looks of it,
		//OpenGL is based on a singleton/global class kind of model?
		//Kind of like in flixel where they have FlxG.game
		//and FlxG.camera and stuff. Just a guess.
		GLES20.glShaderSource(shader, shaderCode);

		//Now I am pretty sure my assumption on the return
		//type of this class is correct.
		return shader;
	}

	public Triangle() {

		//if less than 3 verts in our array,
		//we have a serious problem.
		if(vArr.length < 3)
		{
			Log.e("problem","Yes,weHaveAProblem");
		}

		//Each vert has 4 bytes??? But I thought this was 3D??
		//Unless these verts are RGBA colors, then that makes sense.
		//32 bit color is 4 bytes.
		ByteBuffer bb = ByteBuffer.allocateDirect(vArr.length * 4);

		//seems like a safe bet:
		bb.order(ByteOrder.nativeOrder());

		vBuf = bb.asFloatBuffer();
		vBuf.put(vArr); //set verticies.
		vBuf.position(0); //make sure we draw ALL verts by starting from
		//the beginning.

		int vertShaderID =
		loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);

		int fragShaderID =
		loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

		//creates an empty program and returns an integerID
		//to reference that program:
		shaderProgram = GLES20.glCreateProgram();
		GLES20.glAttachShader(shaderProgram, vertShaderID);
		GLES20.glAttachShader(shaderProgram, fragShaderID);

		//Linking compiles the program and makes it ready to
		//run by our android device:
		GLES20.glLinkProgram(shaderProgram);


		/** Notice the use of an integer array of size one.
		 This it to bypass not being able to pass integeral types by
		 anything but value in Java. This method will allow glGetShaderv
		 to modify the value of CompiledFlag. A non-zero result indicates
		 an error. **/
		int[] CompileFlag = new int[1];
		GLES20.glGetShaderiv(shaderProgram,
		                     GLES20.GL_COMPILE_STATUS, CompileFlag, 0);

		//set the result here so we can debug:
		//notes here:https://makegameshappen.wordpress.com/
		//2015/06/22/digipen-opengl-es2-0-talk/
		ShaderLog.compileFlag = CompileFlag[0];

		/**
		This will return the particular compilation error in a
		string allowing you to easily print to LogCat Android’s
		text output channel. You should wrap all of this up in a
		class with static functions to centralize this error checking.
		similar logic or linking. **/
		String LogOut = GLES20.glGetShaderInfoLog(shaderProgram);



	}//End Triangle

	public void draw()
	{
		GLES20.glUseProgram(shaderProgram);

		//gets a pointer so we.... know some type of entry point
		//for shader?
		/** pa == position attribute **/
		int pa =
		GLES20.glGetAttribLocation(shaderProgram, "vPosition");

		/**Activate the ability to figure out where it needs to go. **/
		GLES20.glEnableVertexAttribArray(pa);

		GLES20.glVertexAttribPointer
		(pa, 3, GLES20.GL_FLOAT, false, 0, vBuf);

		int colorUniform = GLES20.glGetUniformLocation
		(shaderProgram, "vColor");
		GLES20.glUniform4fv(colorUniform, 1, color, 0);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vArr.length / 3);

		//disable this so that we can avoid messing stuff up.
		//what this protects me against, I am not sure yet.
		GLES20.glDisableVertexAttribArray(pa);

	}


}//class Triangle. End.
