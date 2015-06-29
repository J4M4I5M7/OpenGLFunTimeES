package com.example.jmim.mypak;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by JMIM on 6/23/2015.
 */
public class MySVew extends GLSurfaceView
{

	public MySVew(Context context)
	{
		super(context);
		setEGLContextClientVersion(2);

		setEGLConfigChooser(8, 8, 8, 8, 16, 8);

		//setRenderer(new LessonOneRenderer());
	}

}
