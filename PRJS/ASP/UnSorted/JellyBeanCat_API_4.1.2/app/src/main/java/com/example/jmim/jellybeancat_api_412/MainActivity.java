package com.example.jmim.jellybeancat_api_412;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

//imports needed for openGL example:
//http://www.jayway.com/2013/05/09/opengl-es-2-0-tutorial-for-android-part-i-getting-started/
import android.content.Context;
import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;

//Needed for manipulating text areas:
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity
{
    private GLSurfaceView mSurfaceView;
    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView cow = (TextView)findViewById(R.id.textView01);
        cow.setText("RAAAAWWEERRR");



        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //What is this thingy? This makes it so you see the openGL render context?
        //Instead of the activity_main.xml layout?
        setContentView(mGLView);
    }

    private void initialize()
    {
        if (hasGLES20())
        {
            TextView cow = (TextView)findViewById(R.id.textView01);
            cow.setText("You got the GL!");

            mGLView = new GLSurfaceView(this);
            mGLView.setEGLContextClientVersion(2);
            mGLView.setPreserveEGLContextOnPause(true);
            mGLView.setRenderer(new GLES20Renderer());
        } else {
            // Time to get a new phone, OpenGL ES 2.0 not supported.
            TextView cow = (TextView)findViewById(R.id.textView01);
            cow.setText("NO OPEN GL ES 2.0 support.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }

    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }


    private boolean hasGLES20()
    {
        ActivityManager am = (ActivityManager)
                getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return info.reqGlEsVersion >= 0x20000;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
