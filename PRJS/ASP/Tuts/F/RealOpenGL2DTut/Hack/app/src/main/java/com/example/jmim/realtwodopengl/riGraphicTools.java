package com.example.jmim.realtwodopengl;


import android.opengl.GLES20;

/**
 * Created by JMIM on 6/23/2015.
 */
public class riGraphicTools
{
	//program handle: sp == "shader program"
	public static int sp_SolidColor;

	/**
	 * SHADER Solid
	 * This shader is for rendering a colored primitive*/
	public static final String vs_SolidColor =
	"uniform   mat4 uMVPMatrix;" +
	"attribute vec4 vPosition;" +
	"void main(){"+
	"gl_FragColor = vec4(0.5, 0, 0, 1);" +
	"}";

	public static final String fs_SolidColor =
	"precision mediump float;" +
	"void main(){" +
	"gl_FragColor = vec4(0.5, 0, 0, 1);"+
	"}";

	public static int loadShader(int type, String shaderCode)
	{

		//create a vertex shader type: (GLES20.GL_VERTEX_SHADER)
		//or a fragment shader type: (GLES20.GL_FRAGMENT_SHADER)

		int shaderHandle = GLES20.glCreateShader(type);

		//add the source code to the shader and compile:
		GLES20.glShaderSource(shaderHandle, shaderCode);
		GLES20.glCompileShader(shaderHandle);

		//return the shader handle:
		return shaderHandle;


	}//rawer. Done loading shader.



}//End Class
