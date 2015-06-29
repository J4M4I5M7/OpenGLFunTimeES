package MyNewPackage;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.Log;
import android.content.Context;


/**
 * Created by JMIM on 6/21/2015.
 */
public class MainActivity extends Activity
{

    //Lesson1 Of Justin's Tutorials. On Android OpenGL ES2.0

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityManager am =
        (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        boolean supportES2 = (info.reqGlEsVersion >= 0x20000);
        if(supportES2)
        {
            MainRenderer mRen = new MainRenderer();
            MainSurfaceView mSur = new MainSurfaceView(this);
            mSur.setEGLContextClientVersion(2); //need version 2.
            mSur.setRenderer(mRen);

            this.setContentView(mSur);


        }
        else
        {
            Log.e("OpenGLES 2","Your device doesn't support OpenGL ES2.0");
        }

    }//on Create

}//class
