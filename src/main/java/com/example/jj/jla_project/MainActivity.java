package com.example.jj.jla_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);

        final MediaPlayer sound = MediaPlayer.create(MainActivity.this,
                getAudio(MainActivity.this, "menuselect1"));
        final MediaPlayer theme = MediaPlayer.create(MainActivity.this,
                getAudio(MainActivity.this, "openingtitles"));

        //handler.postDelayed(runnable, 100);

        theme.start();
        ImageView test = (ImageView) findViewById(R.id.androidView);
        test.setImageResource(getDrawable(MainActivity.this, "coolsymbol"));
        Button startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                sound.start();
/*
                Intent startGame = new Intent(MainActivity.this, TestMod.class);
                startActivity(startGame);
                finish();
*/
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        Intent startGame = new Intent(MainActivity.this, GameActivity.class);
                        startActivity(startGame);
                        finish();
                    }
                }, 200);


                // Stop OpeningTitle
                theme.stop();

            }
        });


    }

    public static int getDrawable(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public static int getAudio(Context context, String audio) {
        return context.getResources().getIdentifier(audio, "raw", context.getPackageName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_screen, menu);
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
