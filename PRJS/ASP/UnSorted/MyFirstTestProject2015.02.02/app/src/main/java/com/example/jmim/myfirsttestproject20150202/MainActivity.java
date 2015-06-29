//package com.javacodegeeks.android.buttonexample;
package com.example.jmim.myfirsttestproject20150202;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(View view);
        setContentView(R.layout.activity_main);

        addListenerOnButton();

    }

    public void doStuff(View view)
    {

    }

    public void addListenerOnButton() {

        //Select a specific button to bundle it with the action you want
        button = (Button) findViewById(R.id.button1);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                button.setText("MEOW");
                //button.setImeActionLabel("Hello",0);
               // Intent openBrowser =  new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.javacodegeeks.com"));
                //startActivity(openBrowser);
            }

        });

    }

}