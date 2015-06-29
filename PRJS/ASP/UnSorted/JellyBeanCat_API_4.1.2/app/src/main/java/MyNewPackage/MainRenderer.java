package MyNewPackage;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by JMIM on 6/21/2015.
 */
public class MainRenderer implements Renderer
{


    @Override
    public void onDrawFrame(GL10 arg0) {
        GLES20.glClearColor(0.8f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT |
                       GLES20.GL_DEPTH_BUFFER_BIT );

    }//onDrawFrame



    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }//onSurfaceChanged



    @Override
    public void onSurfaceCreated(GL10 arg0, EGLConfig arg1){

    }//onSurfaceCreated




}//class
