package com.example.jmim.dayfive;

import android.content.Context;
import android.opengl.GLES10;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Stage extends GLSurfaceView {
	private final class MyRenderer implements GLSurfaceView.Renderer {
		public final void onDrawFrame(GL10 gl) {
			gl.glPushMatrix();
			gl.glClear(GLES10.GL_COLOR_BUFFER_BIT);
			gl.glTranslatef(w / 2, h / 2, 0);
			gl.glScalef(120, 100, 0);

			gl.glColor4f(1, 1, 1, 1);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
			gl.glPopMatrix();
		}

		public final void onSurfaceChanged(GL10 gl, int width, int height) {
			gl.glClearColor(0, 0, 0, 1.0f);

			if(width > height) {
				h = 600;
				w = width * h / height;
			} else {
				w = 600;
				h = height * w / width;
			}

			screenWidth = width;
			screenHeight = height;

			gl.glViewport(0, 0, screenWidth, screenHeight);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, w, h, 0, -1, 1);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
		}

		public final void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// Set up alpha blending
			gl.glEnable(GL10.GL_ALPHA_TEST);
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);

			// We are in 2D. Who needs depth?
			gl.glDisable(GL10.GL_DEPTH_TEST);

			// Enable vertex arrays (we'll use them to draw primitives).
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

			// Enable texture coordination arrays.
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
	}

	/* Stage width and height */
	private float w, h;

	/* Screen width and height */
	private int screenWidth, screenHeight;

	/* Our native vertex buffer */
	private FloatBuffer vertexBuffer;

	public Stage(Context context, AttributeSet attrs) {
		super(context, attrs);
		setEGLConfigChooser(8, 8, 8, 8, 0, 0);
		setRenderer(new MyRenderer());

		float vertices[] = {
		-0.5f, -0.5f,  0.0f,  // 0. left-bottom
		0.5f, -0.5f,  0.0f,  // 1. right-bottom
		-0.5f,  0.5f,  0.0f,  // 2. left-top
		0.5f,  0.5f,  0.0f   // 3. right-top
		};

		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
	}
}

/*
package com.example.jmim.dayfive;

import android.opengl.GLES10;
import android.opengl.GLSurfaceView;
import android.content.Context; //<<ANDROID CONTEXT. Not OpenGL
import android.util.AttributeSet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


//Created by JMIM on 6/24/2015.

public class Stage extends GLSurfaceView
{
	private FloatBuffer vertexBuffer;


	//Width and height of our STAGE. (Game Space?)//
	private float w, h;

	//Screen width and height.//
	private int screenWid, screenHig;

	//OUr stage is born here! //
	public Stage(Context ctx, AttributeSet ats)
	{
		//For now we have only coded the constructor.
		//This specific constructor is needed by the layout inflater.
		//When our layout is being built by calling the
		//setContentView on our activity, it is this constructor of
		//each View that is called.

		//The last line in the constructor is required, and it
		//instructs GLSurfaceView to use RGBA color with 8Bits
		//for each component.

		//Give context and attribute set to super:
		super(ctx, ats);

		//To sue the MyRenderer class, we need to add these lines of code
		//below the Stage's constructor:
		//                  R G B A D S  D=Depth. S=Stencil.
		setEGLConfigChooser(8,8,8,8,0,0);
		setRenderer(new MyRenderer());

		makeTriangleData();

	}//end constructor.

	private void makeTriangleData()
	{
		float verts[] =
		{
			-0.5f, -0.5f, 0.0f, //left bottom.
			0.5f, -0.5f, 0.0f, //right bottom.
			-0.5f,  0.5f, 0.0f, //left-top
			0.5f,  0.5f, 0.0f, //right-top
		};

		//Convert our reference type array verts[] on the heap
		//into a solid chunk of binary data on the stack:
		ByteBuffer vbb = ByteBuffer.allocateDirect(verts.length*4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(verts);
		vertexBuffer.position(0);


	}


	//NOTE: ALT + INSERT was useful to implement stubs for these render methods in
	//the MyRenderer class.

	//A private inner class that is used to render the stage.
	//This is interesting. I have not seen it done this way before.
	private class MyRenderer implements GLSurfaceView.Renderer
	{
		/##
		 * Be CAREFUL: At this stage, the View might not be laid out yet and the
		 * dimensions might not be available. (ASYNC problem.)
		 * ^^I think I was running into this in other tutorials.
		 *
		 * This code is run only once when the object is created. SO we should
		 * only do one-off tasks here.
		 * @param gl
		 * @param config
		 ##/
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			//Set up alpha blending.
			gl.glEnable(GL10.GL_ALPHA_TEST);
			gl.glEnable(GL10.GL_BLEND);

			//We will discuss this line later along with textures:
			gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);

			//We are in 2D. Who needs depth?
			gl.glDisable(GL10.GL_DEPTH_TEST);

			//Enable vertex arrays. (We'll use them to draw primitives).
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

			//Enable Texture Coordinate arrays:
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		}//end on surface created function.

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			gl.glClearColor(0,0,0,1);

			//remove this declaration now that we are on the Day6 part
			//of the tutorial.
			///float w, h; //<--strange declaration...

			//set screen width and screen height:
			screenWid = width;
			screenHig = height;
			gl.glViewport(0,0,screenWid, screenHig);

			////Normalize so smallest axis is always 600.
			 * Why? No clue yet. ///
			int smallestAxisLen = 600;

			//// This fraction is GREATER THAN or equal to one ///
			float improperFraction;

			if(width > height)
			{
				h = smallestAxisLen;
				improperFraction = (width/height);
				w = h * improperFraction;
			}
			else
			{
				w = smallestAxisLen;
				improperFraction = (height/width);
				h = w * improperFraction;
			}

			//Edit projection matrix:
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity(); //<--must clear matrix before using ortho call on it.
			gl.glOrthof(0,w,h,0,-1,1);

			//Edit view matrix:
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();


		}//end on surface changed function.

		@Override
		public void onDrawFrame(GL10 gl)
		{
			gl.glClear(GLES10.GL_COLOR_BUFFER_BIT);

			//try to find bug:
			if(w==0 || h==0)
			{
				throw new Error("w or h is zero.");
			}

			gl.glPushMatrix();
			gl.glTranslatef(w/2, h/2,0);
			gl.glScalef(120,100,0);

			//Rendering code goes here. //
			gl.glVertexPointer(3,GL10.GL_FLOAT, 0, vertexBuffer);
			gl.glColor4f(0,1,0,1);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,4);


			//pop the modified matrix off the stack to "undo"
			//the transformations you did so next frame of render
			//does NOT compound the effects.
			//If you do not do this, you could end up with a black screen.
			gl.glPopMatrix();


		}//onDrawFrame
	}
}//End Class
*/















































