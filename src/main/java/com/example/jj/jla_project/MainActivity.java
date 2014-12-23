package com.example.jj.jla_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.lang.Thread.sleep;


public class MainActivity extends Activity {

    TextView text1;
    TextView text2;
    Button btn_ans1, btn_ans2, btn_ans3;
    private static final int maxNumPerLine = 65;
    private CharSequence mText;
    private int mIndex, mIndex2, mIndex3;
    private long mDelay = 15;//default delay
    private MediaPlayer mTypeSound;
    private MediaPlayer theme;
    BufferedReader buffr;
    String currentLine;
    //singleton fields
    private boolean tappable = true, notTyping = false;
    private String filepath;
    /*public boolean decision;
    public boolean jump;
    public String currentTuple;
    public String nextTuple;
    //public String[] instructions;
    public String imgName;
    public String musicName;*/
    SingletonTest model = SingletonTest.getInstance();




    SceneSingleton sceneSingleton = SceneSingleton.getInstance();
    String filename;

    InputStream inputStream = null;











    private int scene = 1, lineNum = 1;
    RelativeLayout rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theme = MediaPlayer.create(MainActivity.this, R.raw.openingtitles);

        rl = (RelativeLayout) findViewById(R.id.RLLayout);

        final ImageView imageL = (ImageView) findViewById(R.id.leftImage);
        final ImageView background = (ImageView) findViewById(R.id.imageView2);
        final ImageView imageR = (ImageView) findViewById(R.id.imageRight);
        View frame = (View) findViewById(R.id.separator);//assigning id
        frame.getBackground().setAlpha(150);//set transparency of text frame

        //imageL.setImageResource(getDrawable(this, "apollo1"));
        background.setImageResource(R.drawable.bedroom1);
        text1 = (TextView) findViewById(R.id.editText);
        text2 = (TextView) findViewById(R.id.editText2);
        mTypeSound = MediaPlayer.create(text1.getContext(), R.raw.typeshort);
        final Button btn_ans1 = (Button) findViewById(R.id.ans1);
        final Button btn_ans2 = (Button) findViewById(R.id.ans2);
        final Button btn_ans3 = (Button) findViewById(R.id.ans3);

        theme.start();
        animateText("Hi! My Name is JJ!");


        try {
            inputStream = getResources().openRawResource(R.raw.script);
            buffr = new BufferedReader(new InputStreamReader(inputStream));
            currentLine = buffr.readLine();
            inputStream.close();
        }
        catch (IOException e) {
            Log.e("message", e.getMessage());
        }

        /*
         * Tap & Button Listeners Start Now
         *
         */









     /* TESTING **********/


        Intent testIntent = new Intent(MainActivity.this, TestMod.class);
        startActivity(testIntent);


    /* TESTING */





        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            // Take current Tuple, and load everything.
            // Load Views from Singleton
            /* Load from SceneSingleton (1,1,1)
             *  Theme Music
             *  Sounds
             *  Image
             *  BackGround
             *  Text
             *  Button T/F (= Tappable by itself)
             */


            // After Click, which is now,
            // set new currentTuple -------> Singleton
            // set new Ptr to Buffr

















                // Start of Process
                // Based on input, populate the model
                String array2[] = model.populateS(buffr, currentLine);
                model.readS(array2);

                String filename = model.getImageName();
                tappable = model.getTappable();
                //model.populate

                if (model.getDecision()) {
                    btn_ans1.setVisibility(View.VISIBLE);
                    btn_ans2.setVisibility(View.VISIBLE);
                    btn_ans3.setVisibility(View.VISIBLE);
                    final String[] decText = model.getDec();

                    model.decision(decText);
                    final String[] dA = model.getDecAddress();
                    // / decText = model.getDecStrings();
                    //String temp = decText[1];
                    //temp = decText[2];
                    // if jump, jump
                    if(model.getJump() == true)
                    {currentLine = model.getNextTuple();}
                    else
                    {currentLine = model.getCurrent(); }
                    btn_ans1.setText(decText[0]);
                    btn_ans2.setText(decText[2]);
                    btn_ans3.setText(decText[4]);

                    final String[] decAddress = model.getDecAddress();

                    btn_ans1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            animateText("JJ left all of his money under the mattress and goes out to a movie theater instead of a bank. 3 hours later...");
                            background.setImageResource(getDrawable(MainActivity.this,"black"));
                            btn_ans1.setVisibility(View.INVISIBLE);
                            btn_ans2.setVisibility(View.INVISIBLE);
                            btn_ans3.setVisibility(View.INVISIBLE);
                            tappable = true;
                            //model.waitingClick("3,0,1", buffr);

                        }
                    });

                    btn_ans2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            animateText("JJ: I guess mom was right. The bank really is a safer place for my money.\n" +
                                    "My money should be kept in a safe place where I can’t lose it. ");
                            background.setImageResource(getDrawable(MainActivity.this,"black"));
                            btn_ans1.setVisibility(View.INVISIBLE);
                            btn_ans2.setVisibility(View.INVISIBLE);
                            btn_ans3.setVisibility(View.INVISIBLE);
                            tappable = true;
                        }
                    });

                    btn_ans3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            animateText("JJ: It’s all gone! All...gone.");
                            background.setImageResource(getDrawable(MainActivity.this,"s3f4"));
                            btn_ans1.setVisibility(View.INVISIBLE);
                            btn_ans2.setVisibility(View.INVISIBLE);
                            btn_ans3.setVisibility(View.INVISIBLE);
                            tappable = true;
                        }
                    });


                } else if (tappable && notTyping) {

                    background.setImageResource(getDrawable(MainActivity.this, filename));
                    animateText(model.getuserText());
                    currentLine = model.getInstance().getCurrentTuple();



                }


                // If Decision = False  // Choice button will exist
                // Set values of button clicks to 0,
                // And set Buttons to invisible
                // else, set them as 1, 2, 3


                //while (tappable) {
                    /*if (scene == 1 && tappable && notTyping) {
                        //tappable = false;

                        animateText("I just woke up and headache");
                        //imageL.setImageResource(getDrawable(MainActivity.this,"apolloshocked"));

                        scene++;
                        background.setImageResource(R.drawable.s1f1);
                        btn_ans1.setText("Fuck Me!!! Yea");
                        btn_ans1.setVisibility(View.VISIBLE);

                        btn_ans2.setText("Fuck Me!!! Nop");
                        btn_ans2.setVisibility(View.VISIBLE);

                        btn_ans3.setText("What are the Necessary Things!!! Yea?");
                        btn_ans3.setVisibility(View.VISIBLE);

                        btn_ans1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                tappable = true;
                                animateText("Damn It!!!");
                                btn_ans1.setVisibility(View.INVISIBLE);
                            }
                        });
                        tappable = false;

                    }
                    else if (scene == 2 && tappable && notTyping) {
                        //tappable = false;
                        animateText("Holyshit");
                        background.setImageResource(R.drawable.s1f2);
                        //imageL.setImageResource(getDrawable(MainActivity.this, "apollosweating4"));
                        //imageR.setImageResource(getDrawable(MainActivity.this,"apollo1"));
                        scene++;

                    }
                    else if (scene == 3 && tappable && notTyping){
                        theme.stop();
                        background.setImageResource(getDrawable(MainActivity.this, "s1f3"));
                        animateText("Nice Beach!");

                    }
                //}*/
            }

        });
    }

    //running texts
    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            if(mIndex <= maxNumPerLine )
            {
                text1.setText(mText.subSequence(0, mIndex++));
                mIndex2++;
            }
            else {
                text2.setText(mText.subSequence(mIndex-1, mIndex2++));
                lineNum++;

            }
            mTypeSound.start();

            if(mIndex <= mText.length() && mIndex2 <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            }
            else{
                notTyping = true;//allow tap if string finish typing
            }

        }
    };

    //typewriting text
    public void animateText(CharSequence text) {
        mText = text;

        mIndex = 0;
        mIndex2 = 0;
        notTyping = false;//no tapping if string not finished typing
        text1.setText("");
        text2.setText("");

        mHandler.removeCallbacks(characterAdder);

        mHandler.postDelayed(characterAdder, mDelay);

    }

    public void setCharacterDelay(long millis) {
        mDelay = millis;
    }

    //get drawable id
    public static int getDrawable(Context context, String name)
    {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    @Override
    protected void onPause(){
        if(this.isFinishing()){
            theme.stop();
            mTypeSound.stop();
        }
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
