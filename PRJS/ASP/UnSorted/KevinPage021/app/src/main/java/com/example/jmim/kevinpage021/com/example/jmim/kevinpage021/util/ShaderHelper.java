package com.example.jmim.kevinpage021.com.example.jmim.kevinpage021.util;

import android.util.Log;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;


/**
 * Created by JMIM on 6/29/2015.
 */
public class ShaderHelper
{



	public static final String TAG = "ShaderHelper";

	/**
	 *
	 * @param sc: The vertex code source string.
	 * @return
	 */
	public static int compileVertexShader
	(String sc)
	{
	    int op = compileShader(GL_VERTEX_SHADER, sc);
		EU.glCheck();
		return op;
	}

	/**
	 *
	 * @param fc: The fragment code source string.
	 * @return
	 */
	public static int compileFragmentShader
	(String fc)
	{
		int op = compileShader(GL_FRAGMENT_SHADER, fc);
		EU.glCheck();
		return op;
	}

	/**
	 *
	 * @param typ: Type of shader.
	 * @param src: Shader source text.
	 * @return
	 */
	private static int compileShader
	(int typ, String src)
	{
		//make stub program, get handle:
		final int shaderObjectID = glCreateShader(typ);

		//Handle logging on error:
		if(0 == shaderObjectID)
		{
			if(LoggerConfig.ON)
			{
				Log.w(TAG,"CouldNotCreateNewShader.");
			}
			return 0;
		}

		//LOAD the source: Page:41:
		glShaderSource(shaderObjectID, src);

		//COMPILE the source: Page 41:
		glCompileShader(shaderObjectID);

		//check to see if compilation was success:
		final int[] compileStatus = new int[1];
		glGetShaderiv(shaderObjectID, GL_COMPILE_STATUS, compileStatus, 0);

		if(LoggerConfig.ON)
		{
			Log.v(TAG,"MSG==:" + "\n" + src + "\n" + ":");
			String sinfo = glGetShaderInfoLog(shaderObjectID);
			Log.v(TAG,"ILOG==[" + sinfo + "]");
		}

		if(0==compileStatus[0])
		{
			if(LoggerConfig.ON)
			{
				Log.w(TAG,"CompilationOfShaderFailed");
				throw new RuntimeException("CompilationOfShaderFailedBoss");
			}
			return 0;
		}//bad compilation status. 0 == failure.

		if(shaderObjectID <= 0)
		{
			if(0==shaderObjectID)
			{
				throw new RuntimeException("shaderObjectID==0");
			}
			else
			{
				throw new RuntimeException("NegativeShaderID==int-overflow");
			}
		}

		//return the ID/Handle of the compiled shader:
		return shaderObjectID;


	}//end function: Compile Shader.


	/**
	 *
	 * @param hVS: Handle of vertex shader.
	 * @param hFS: Handle of fragment shader.
	 * @return
	 */
	public static int linkProgram(int hVS, int hFS)
	{
		//Error check your input programs:
		/////////////////////////////////////////
		String msg = "";
		boolean hasInputError = false;
		if(hVS == 0)
		{
			hasInputError = true;
			msg += "[hVS==0]";
		}

		if(hFS == 0)
		{
			hasInputError = true;
			msg += "[hFS==0]";
		}

		if(hasInputError)
		{
			throw new RuntimeException(msg);
		}
		/////////////////////////////////////////


		/**hPO == Handle: Program Object. **/
		//final int hPO = glCreateProgram();
		int hPO = glCreateProgram(); //taking off final.

		if(0 == hPO)
		{
			if(LoggerConfig.ON)
			{
				Log.w(TAG,"CouldNotCreateNewProgram");
			}

			throw new RuntimeException("C_N_C_N_P");

		}//error!!!!

		//attach vert and frag shader:
		//page 45 of Kevin's book:
		glAttachShader(hPO, hVS);
		glAttachShader(hPO, hFS);

		//Join our shaders together into one program:
		glLinkProgram(hPO);

		final int[] stat = new int[1];
		glGetShaderiv(hPO, GL_LINK_STATUS, stat, 0);

		if(LoggerConfig.ON)
		{
			Log.v(TAG,"results:"+"\n"
			+ glGetProgramInfoLog(hPO) );
		}

		//return 0 if link status failed.
		//maybe throw error message in log. Page 45:
		if(0 == stat[0])
		{
			//if failed, delete the program object:
			glDeleteProgram(hPO);
			if(LoggerConfig.ON)
			{
				Log.w(TAG,"LinkingOfProgramFailed");
			}

			throw new RuntimeException("L_O_P_F");

		}//rawer...



		//return the program object handle: PAGE 46:
		return hPO;

	}//link Program.

	//added on page 47:

	/**
	 *
	 * @param hPO: The handle of our program object.
	 * @return
	 */
	public static boolean validateProgram(int hPO)
	{
		glValidateProgram(hPO);
		final int[] stat = new int[1];
		glGetProgramiv(hPO, GL_VALIDATE_STATUS, stat, 0);

		Log.v(TAG,"ResultsOfValidatingProgram:"+stat[0] + "\n" +
		"Log:" + glGetProgramInfoLog(hPO));

		//Ive got a better idea... If not successful. We need to crash.
		//Because hell, I can't ever see any of these messages from the logs.
		if(0 == stat[0])
		{   //adding code to crash because logs seem to be pointless for me.
			throw new Error("validateProgram has failed.");
		}

		return (0 != stat[0] );
	}

}//end of class.
