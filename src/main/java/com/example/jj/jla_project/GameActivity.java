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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.lang.Thread.sleep;


public class GameActivity extends Activity implements View.OnClickListener{


    private static final int maxNumPerLine = 65;
    private CharSequence mText;
    private int mIndex, mIndex2, mIndex3;
    private long mDelay = 60;//default delay
    private MediaPlayer mTypeSound;
    private MediaPlayer sound;
    private MediaPlayer theme;
    BufferedReader buffr;
    String currentLine;


    View frame;
    TextView text1;
    TextView text2;
    ImageView imageL;
    ImageView background;
    ImageView imageR;
    Button btn_ans1;
    Button btn_ans2;
    Button btn_ans3;
    ImageView imgBtn;
    ImageView imgBt;
    LinearLayout testLay;
    FrameLayout frameCentral;


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
    //SingletonTest model = SingletonTest.getInstance();
    SceneSingleton model = SceneSingleton.getInstance();
    Tuple currentTuple = Tuple.getInstance();


    SceneSingleton sceneSingleton = SceneSingleton.getInstance();
    String filename;

    InputStream inputStream = null;


    private int scene = 1, lineNum = 1;
    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameactivity);

        theme = MediaPlayer.create(GameActivity.this, R.raw.openingtitles);
        rl = (RelativeLayout) findViewById(R.id.RLLayout);

        imageL = (ImageView) findViewById(R.id.leftImage);
        background = (ImageView) findViewById(R.id.imageView2);
        imageR = (ImageView) findViewById(R.id.imageRight);
        frame = (View) findViewById(R.id.separator);//assigning id
        frame.getBackground().setAlpha(150);//set transparency of text frame


        //imageL.setImageResource(getDrawable(this, "apollo1"));
        background.setImageResource(R.drawable.bedroom1);
        text1 = (TextView) findViewById(R.id.editText);
        text2 = (TextView) findViewById(R.id.editText2);
        mTypeSound = MediaPlayer.create(text1.getContext(), R.raw.typeshort);
        btn_ans1 = (Button) findViewById(R.id.ans1);
        btn_ans2 = (Button) findViewById(R.id.ans2);
        btn_ans3 = (Button) findViewById(R.id.ans3);
        imgBtn = (ImageView) findViewById(R.id.imageBtn);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();

            }
        });
        // USE THIS
        testLay = (LinearLayout) findViewById(R.id.testLinear);
        frameCentral = (FrameLayout) findViewById(R.id.frameCentral);
        //testLay.setBackgroundResource(getDrawable(GameActivity.this, "beach"));

        //theme.start();


        imgBtn.setImageResource(getDrawable(GameActivity.this, "inventory"));


        animateText("Hi! My Name is JJ!");

        try {
            inputStream = getResources().openRawResource(R.raw.scripttest);
            buffr = new BufferedReader(new InputStreamReader(inputStream));
            currentLine = buffr.readLine();
            model.setcurrentTuple(currentTuple);
            model.connectBufferedReader(buffr);

            //inputStream.close();
        } catch (IOException e) {
            Log.e("message", e.getMessage());
        }

        /*
         * Tap & Button Listeners Start Now
         *
         */



        rl.setOnClickListener(this);
        btn_ans1.setOnClickListener(this);
        btn_ans2.setOnClickListener(this);
        btn_ans3.setOnClickListener(this);









     /* TESTING **********/

/*
        Intent testIntent = new Intent(GameActivity.this, TestMod.class);
        startActivity(testIntent);
        finish();
*/

    /* TESTING */


        // Very First Connection Initial Prep

        /*

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (model.getbtnExists()) {
                    btn_ans1.setVisibility(View.VISIBLE);
                    btn_ans2.setVisibility(View.VISIBLE);
                    btn_ans3.setVisibility(View.VISIBLE);

                    btn_ans1.setText(model.getbtnTxt(0));
                    btn_ans2.setText(model.getbtnTxt(1));
                    btn_ans3.setText(model.getbtnTxt(2));


                    // Do Nothing
                    //System.err.println("I LOVE YOU ANGELICA " + model.getbtnTxt(0));

                    btn_ans1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //animateText(model.getbtnTxt(0));
                            background.setImageResource(getDrawable(GameActivity.this, "black"));
                            btn_ans1.setVisibility(View.INVISIBLE);
                            btn_ans2.setVisibility(View.INVISIBLE);
                            btn_ans3.setVisibility(View.INVISIBLE);

                            model.getcurrentTuple().updateTuple(model.getbtnAdd(0));

                            //model.waitingClick("3,0,1", buffr);

                        }
                    });

                    btn_ans2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //animateText(model.getbtnTxt(1));
                            background.setImageResource(getDrawable(GameActivity.this, "black"));
                            btn_ans1.setVisibility(View.INVISIBLE);
                            btn_ans2.setVisibility(View.INVISIBLE);
                            btn_ans3.setVisibility(View.INVISIBLE);
                            model.getcurrentTuple().updateTuple(model.getbtnAdd(1));
                        }
                    });

                    btn_ans3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //animateText(model.getbtnTxt(2));
                            background.setImageResource(getDrawable(GameActivity.this, "s3f4"));
                            btn_ans1.setVisibility(View.INVISIBLE);
                            btn_ans2.setVisibility(View.INVISIBLE);
                            btn_ans3.setVisibility(View.INVISIBLE);
                            model.getcurrentTuple().updateTuple(model.getbtnAdd(2));
                        }
                    });


                }


                model.pointToNext();
                System.err.println("Dan's currentLine is " + model.getcurrentLine());
                model.popSceneSingleton();


                try {


                    // Load Views
                    testLay.setBackgroundResource(getDrawable(GameActivity.this, model.getbackgroundImg()));
                    // Load Audio Files
                    //theme.stop(); // IF NEW THEME EXISTS ---> SET
                    //esound.stop();


                    try {
                        theme = MediaPlayer.create(GameActivity.this,
                                getAudio(GameActivity.this, model.getthemeMusic()));
                        if (model.getthemeMusic() != null) {
                            theme.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if (model.getSoundFile() != null)
                        sound = MediaPlayer.create(GameActivity.this,
                                getAudio(GameActivity.this, model.getSoundFile()));


                    if (model.getimgL() != null) {
                        imageL.setImageResource(getDrawable(GameActivity.this, model.getimgL()));
                        if (false)  // Eventually, set fadein boolean in model
                        {
                        } else {
                            imageL.setVisibility(View.GONE);
                            fadeInAndShowImage(imageL);
                        }


                    }
                    if (model.getimgR() != null) {
                        imageR.setImageResource(getDrawable(GameActivity.this, model.getimgR()));
                        imageR.setVisibility(View.GONE);
                        fadeInAndShowImage(imageR);
                    }


                    if ((!model.gettext().equals("")) && (model.gettext() != null)) {

                        System.err.println(model.toString());

                        frame.setVisibility(View.VISIBLE);
                        text1.setVisibility(View.VISIBLE);
                        animateText(model.gettext());
                        mTypeSound.reset();
                        mTypeSound.start();
                    } else {
                        frame.setVisibility(View.GONE);
                        text1.setVisibility(View.GONE);
                    }


                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


            }

            */
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

/*


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
                            background.setImageResource(getDrawable(GameActivity.this,"black"));
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
                            background.setImageResource(getDrawable(GameActivity.this,"black"));
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
                            background.setImageResource(getDrawable(GameActivity.this,"s3f4"));
                            btn_ans1.setVisibility(View.INVISIBLE);
                            btn_ans2.setVisibility(View.INVISIBLE);
                            btn_ans3.setVisibility(View.INVISIBLE);
                            tappable = true;
                        }
                    });


                } else if (tappable && notTyping) {

                    background.setImageResource(getDrawable(GameActivity.this, filename));
                    animateText(model.getuserText());
                    currentLine = model.getInstance().getCurrentTuple();



                }


                // If Decision = False  // Choice button will exist
                // Set values of button clicks to 0,
                // And set Buttons to invisible
                // else, set them as 1, 2, 3
*/

            //while (tappable) {
                    /*if (scene == 1 && tappable && notTyping) {
                        //tappable = false;

                        animateText("I just woke up and headache");
                        //imageL.setImageResource(getDrawable(GameActivity.this,"apolloshocked"));

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
                        //imageL.setImageResource(getDrawable(GameActivity.this, "apollosweating4"));
                        //imageR.setImageResource(getDrawable(GameActivity.this,"apollo1"));
                        scene++;

                    }
                    else if (scene == 3 && tappable && notTyping){
                        theme.stop();
                        background.setImageResource(getDrawable(GameActivity.this, "s1f3"));
                        animateText("Nice Beach!");

                    }
                //}


        });
        */


}

    private void fadeOutAndHideImage(final ImageView img) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                img.setVisibility(View.GONE);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });

        img.startAnimation(fadeOut);
    }

    private void fadeInAndShowImage(final ImageView img) {
        Animation fadein = new AlphaAnimation(0, 2);
        fadein.setInterpolator(new AccelerateInterpolator());
        fadein.setDuration(1000);

        fadein.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                img.setVisibility(View.VISIBLE);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });

        img.startAnimation(fadein);
    }

    //running texts
    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            if (mIndex <= maxNumPerLine) {
                text1.setText(mText.subSequence(0, mIndex++));
                mIndex2++;
            } else {
                text2.setText(mText.subSequence(mIndex - 1, mIndex2++));
                lineNum++;

            }
            mTypeSound.start();

            if (mIndex <= mText.length() && mIndex2 <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            } else {
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

        // Set clacking sound


        mHandler.removeCallbacks(characterAdder);

        mHandler.postDelayed(characterAdder, mDelay);

    }


    public void setCharacterDelay(long millis) {
        mDelay = millis;
    }

    //get drawable id
    public static int getDrawable(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public static int getAudio(Context context, String audio) {
        return context.getResources().getIdentifier(audio, "raw", context.getPackageName());
    }







    @Override
    public void onClick(View v) {
        int clickedView = v.getId();
        boolean USES_BTN = model.getbtnExists();
        boolean TAPCLICK = clickedView == R.id.RLLayout;


        // If Button is tapped, or if tap button is tapped while buttons are not used
        // MAY BE REDUNDANT, BUT MIGHT CHANGE LATER


        //if (((TAPCLICK) && (!USES_BTN))
        if (((TAPCLICK))
                ||
                (USES_BTN) && (!TAPCLICK))

                {

            if ((TAPCLICK)&&(!USES_BTN) || (!TAPCLICK)) {


                if ((clickedView == R.id.ans1) ||
                        (clickedView == R.id.ans2) ||
                        (clickedView == R.id.ans3)) {


                    switch(clickedView) {
                        case R.id.ans1:
                            model.getcurrentTuple().updateTuple(model.getbtnAdd(0));
                            break;
                        case R.id.ans2:
                            model.getcurrentTuple().updateTuple(model.getbtnAdd(1));
                            break;
                        case R.id.ans3:
                            model.getcurrentTuple().updateTuple(model.getbtnAdd(2));
                            break;
                    }

                    // Hide Buttons, No matter ---> Unless you're leaving ---> Need to change if its
                    // Multiple Buttons
                    btn_ans1.setVisibility(View.INVISIBLE);
                    btn_ans2.setVisibility(View.INVISIBLE);
                    btn_ans3.setVisibility(View.INVISIBLE);

                    // model.setbtnExists(false);
                }



            /* FALLS THROUGH TO THIS */

                model.pointToNext();

                System.err.println("Dan's currentLine is " + model.getcurrentLine());
                model.popSceneSingleton();

                if (model.getbtnExists()) {                     // Will have buttons if just populated
                    btn_ans1.setVisibility(View.VISIBLE);
                    btn_ans2.setVisibility(View.VISIBLE);
                    btn_ans3.setVisibility(View.VISIBLE);

                    btn_ans1.setText(model.getbtnTxt(0));
                    btn_ans2.setText(model.getbtnTxt(1));
                    btn_ans3.setText(model.getbtnTxt(2));
                }


                try {
                    // BACKGROUND SETUP
                    testLay.setBackgroundResource(getDrawable(GameActivity.this, model.getbackgroundImg()));
                    try {

                        // THEME SETUP
                        theme = MediaPlayer.create(GameActivity.this,
                                getAudio(GameActivity.this, model.getthemeMusic()));
                        if (model.getthemeMusic() != null) {
                            theme.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // SOUND SETUP
                    if (model.getSoundFile() != null)
                        sound = MediaPlayer.create(GameActivity.this,
                                getAudio(GameActivity.this, model.getSoundFile()));


                    // IMG SETUP
                    if (model.getimgL() != null) {
                        imageL.setImageResource(getDrawable(GameActivity.this, model.getimgL()));
                        if (false)  // Eventually, set fadein boolean in model
                        {
                        } else {
                            imageL.setVisibility(View.GONE);
                            fadeInAndShowImage(imageL);
                        }
                    }
                    if (model.getimgR() != null) {
                        imageR.setImageResource(getDrawable(GameActivity.this, model.getimgR()));
                        imageR.setVisibility(View.GONE);
                        fadeInAndShowImage(imageR);
                    }


                    if ((!model.gettext().equals("")) && (model.gettext() != null)) {

                        System.err.println(model.toString());

                        frame.setVisibility(View.VISIBLE);
                        text1.setVisibility(View.VISIBLE);
                        animateText(model.gettext());
                        mTypeSound.reset();
                        mTypeSound.start();
                    } else {
                        frame.setVisibility(View.GONE);
                        text1.setVisibility(View.GONE);
                    }



                    // Setting them off each time for now (Logic above works, so this is quick fix)





                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

        }

    }



    @Override
    protected void onPause(){
        super.onPause();
        if(this.isFinishing()){
            theme.stop();
            mTypeSound.stop();
        }

        Intent invIntent = new Intent (GameActivity.this, Inventory.class);
        startActivity(invIntent);



    }
    @Override
    protected void onResume(){
        super.onResume();
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
