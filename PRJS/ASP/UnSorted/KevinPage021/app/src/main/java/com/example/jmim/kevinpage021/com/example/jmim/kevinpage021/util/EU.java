package com.example.jmim.kevinpage021.com.example.jmim.kevinpage021.util;

import android.opengl.GLES20;
/**
 * Created by JMIM on 6/30/2015.
 */
public class EU
{
	public static void glCheck()
	{
		int status = GLES20.glGetError();
		if(status != GLES20.GL_NO_ERROR)
		{
			throw new RuntimeException("EU.glCheck::ERROR");
		}
	}

}//end class.
