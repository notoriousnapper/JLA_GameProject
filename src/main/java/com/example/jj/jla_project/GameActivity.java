package com.example.jj.jla_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
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

import android.view.animation.AnimationUtils;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.lang.Thread.sleep;


public class GameActivity extends Activity implements View.OnClickListener {


    private static final int maxNumPerLine = 65;
    private CharSequence mText;
    private int mIndex, mIndex2, mIndex3;

    private CharSequence mTextII;
    private int mIndexII, mIndexII2;

    private long mDelay = 45;  //default delay for text
    private MediaPlayer mTypeSound;
    private MediaPlayer sound;
    private MediaPlayer theme;
    BufferedReader buffr;
    String currentLine;


    View frame;
    View nameframe;
    TextView text1;
    TextView text2;
    TextView nameTxt;
    TextView fullTextView;


    ImageView imgObj;
    ImageView imageL;
    ImageView imageC;
    ImageView imageR;
    ImageView background;
    Button btn_ans1;
    Button btn_ans2;
    Button btn_ans3;
    ImageView imgBtn;
    ImageView imgBt;
    LinearLayout testLay;
    FrameLayout frameCentral;
    FrameLayout frontFrame;

    final String SYM_FADEIN = "IN";
    final String SYM_FADEOUT = "OUT";
    final String SYM_SHAKE = "SHK";


    //singleton fields
    private boolean tappable = true, notTyping = false;
    private String filepath;

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

        theme = MediaPlayer.create(GameActivity.this, R.raw.typewriter);
        rl = (RelativeLayout) findViewById(R.id.RLLayout);

        imgObj = (ImageView) findViewById(R.id.imgObj);
        imageL = (ImageView) findViewById(R.id.imageLeft);
        imageR = (ImageView) findViewById(R.id.imageRight);
        imageC = (ImageView) findViewById(R.id.imageCenter);
        background = (ImageView) findViewById(R.id.imageView2);

        frame = (View) findViewById(R.id.separator);//assigning id
        nameframe = (View) findViewById(R.id.nameframe);
        //frame.getBackground().setAlpha(150);//set transparency of text frame
        nameframe = (View) findViewById(R.id.nameframe);
        //nameframe.getBackground().setAlpha(150);

        //imageL.setImageResource(getDrawable(this, "apollo1"));
        background.setImageResource(R.drawable.black);
        text1 = (TextView) findViewById(R.id.editText);
        text2 = (TextView) findViewById(R.id.editText2);
        fullTextView = (TextView) findViewById(R.id.fullTextView);
        nameTxt = (TextView) findViewById(R.id.nameTxt);

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
        //frontFrame = (FrameLayout) findViewById(R.id.frontFrame);

        //theme.start();
        // Present Object
        imgObj.setImageResource(getDrawable(GameActivity.this, "inventory"));
        //TESTING
        imgObj.setVisibility(View.GONE);

        //FLAG TESTINg

        // Inventory Object
        imgBtn.setImageResource(getDrawable(GameActivity.this, "inventory"));


        //fullTextView.setText("WAZZAT");
        //fullTextView.setAnimation(AnimationUtils.loadAnimation(GameActivity.this, android.R.anim.fade_in));



        //animateText("It was a cold, rainy day.");

        try {
            inputStream = getResources().openRawResource(R.raw.testingtesting);
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

    }

    private void shake(final View view) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        shake.setDuration(1000);
        view.startAnimation(shake);
    }

    private void fadeOutAndHideImage(final View v) {
        Animation fadeOut = new AlphaAnimation(2, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.INVISIBLE);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });

        v.startAnimation(fadeOut);
    }

    private void fadeInAndShowImage(final View v) {
        Animation fadein = new AlphaAnimation(0, 2);
        fadein.setInterpolator(new AccelerateInterpolator());
        fadein.setDuration(1000);

        fadein.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {

                v.setVisibility(View.VISIBLE);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });

        v.startAnimation(fadein);
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
    private Runnable characterAdderII = new Runnable() {
        @Override
        public void run() {
            if (mIndexII <= mTextII.length()) {
                fullTextView.setText(mText.subSequence(0, mIndex++));
                mIndexII2++;
            }
            /*else {
                text2.setText(mText.subSequence(mIndex - 1, mIndex2++));
                lineNum++;

            }
            */
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

    public void animateOtherText(CharSequence text) {
        mTextII = text;
        mIndexII = 0;
        mIndexII2 = 0;
        notTyping = false;//no tapping if string not finished typing
        fullTextView.setText("");
        //text2.setText("");

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


            //TESTING

            //fullTextView.setText("MWHAHAHAHAH");

            if ((TAPCLICK) && (!USES_BTN) || (!TAPCLICK)) {


                if ((clickedView == R.id.ans1) ||
                        (clickedView == R.id.ans2) ||
                        (clickedView == R.id.ans3)) {

                    // TEMPORARY FIX
                    theme = MediaPlayer.create(GameActivity.this,
                            getAudio(GameActivity.this, "buttonselected"));
                    theme.start();

                    // TEMPORARY FIX

                    switch (clickedView) {
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
                    btn_ans1.setVisibility(View.GONE);
                    btn_ans2.setVisibility(View.GONE);
                    btn_ans3.setVisibility(View.GONE);
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

                    //model.setbtnExists(false); // Reset Button marker...doesn't work
                }


                try {
                    // BACKGROUND IMAGE SETUP

                    try {
                        //if (model.getOverLay()) {
                            //layerImg.setVisibility(View.VISIBLE);
                        if (false)
                        {
                            FrameLayout mainbox = (FrameLayout)findViewById(R.id.mainboxframe);
                            // Reset OverLay
                            //layerImg.setAlpha((float) 300);
                            frontFrame.setVisibility(View.VISIBLE);
                            //frontFrame.setBackgroundResource(R.drawable.black);
                            //frontFrame.getBackground().setAlpha(200);
                            fullTextView.setText("YO MAMA");
                            //frontFrame.
                            //frontFrame.setBackground((float) 300);
                            model.setOverLay(false);
                            //MediaPlayer sound = MediaPlayer.create(GameActivity.this, getAudio(GameActivity.this, "menuselect1"));


                            // Set boxes to front
                            //mainbox.bringToFront();


                        } else {
                            frontFrame.setVisibility(View.GONE);
                        }
                    }

                catch(NullPointerException e) {
                e.printStackTrace();}


                    testLay.setBackgroundResource(getDrawable(GameActivity.this, model.getbackgroundImg()));

                    //TESTINg
                    //model.setBGAnimation("IN");
                    if (model.getBGAnimation()!=null) {
                        if (model.getBGAnimation().contains(SYM_FADEIN)) {
                            //shake(testLay);

                            fadeInAndShowImage(testLay);
                            //testLay.endViewTransition(testLay);
                            //fadein.hasEnded();

                            model.setBGAnimation(null); // Cancel it out
                            //fadeInAndShowImage(testLay);
                        }


                            else if (model.getBGAnimation().contains(SYM_SHAKE)) {
                                shake(testLay);
                                model.setBGAnimation(null);
                            }
                    }

                    try {

                        // THEME SETUP
                        if (model.getthemeMusic()!=null&&(!model.getthemeMusic().contains(""))) {

                            int test = getApplicationContext().getResources().getIdentifier
                                    (model.getthemeMusic(),
                                    "raw",getApplicationContext().getPackageName());
                            if (test!=0) //If does not return 0
                            {

                                theme = MediaPlayer.create(GameActivity.this,
                                        getAudio(GameActivity.this, model.getthemeMusic()));
                            }



                            //}
                            /*
                            catch(Resources.NotFoundException e)
                            {
                                e.printStackTrace();
                            }
                            */

                        }
                        if (model.getthemeMusic() != null) {
                            theme.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // SOUND SETUP
                    try {
                        if (model.getSoundFile() != null) {
                            sound = MediaPlayer.create(GameActivity.this,
                                    getAudio(GameActivity.this, model.getSoundFile()));
                        }
                    }
                    catch (Resources.NotFoundException e)
                    {e.printStackTrace();}


                    /* LEFT IMAGE SETUP */
                    if (model.getimgL() != null) {
                        imageL.setImageResource(getDrawable(GameActivity.this, model.getimgL()));
                        imageL.setVisibility(View.VISIBLE);

                        // Fade In
                        if (model.getIMGLAnimation().contains(SYM_FADEIN))  // Eventually, set fadein boolean in model
                        {
                                imageL.setVisibility(View.INVISIBLE);
                                fadeInAndShowImage(imageL);

                            //Reset
                            model.setIMGLAnimation(null);

                        }

                        // Fade Out
                        if(model.getIMGLAnimation().contains(SYM_FADEOUT))
                        {
                            //imageL.setVisibility(View.VISIBLE);
                            fadeOutAndHideImage(imageL);
                            model.setIMGLAnimation("");
                        }



                    /* RIGHT IMAGE SETUP */
                    }
                    if (model.getimgR() != null) {

                        try {
                        imageR.setImageResource(getDrawable(GameActivity.this, model.getimgR()));
                            // Fade In
                            if (model.getIMGRAnimation().contains(SYM_FADEIN))  // Eventually, set fadein boolean in model
                            {
                                imageR.setVisibility(View.INVISIBLE);
                                fadeInAndShowImage(imageR);

                                //Reset
                                model.setIMGRAnimation(null);

                            }

                            // Fade Out
                            if (model.getIMGRAnimation().contains(SYM_FADEOUT)) {
                                //imageL.setVisibility(View.VISIBLE);
                                fadeOutAndHideImage(imageR);
                                model.setIMGRAnimation("");
                            }
                        }
                        catch (NullPointerException e)
                        {e.printStackTrace();}
                    }

                    /* CENTER IMAGE SETUP */
                    if (model.getimgC() != null) {
                        //model.setIMGC("Apollo1");
                        try {
                        imageC.setImageResource(getDrawable(GameActivity.this, model.getimgC()));



                            // Fade In
                            if (model.getIMGCAnimation().contains(SYM_FADEIN))  // Eventually, set fadein boolean in model
                            {
                                imageC.setVisibility(View.INVISIBLE);
                                fadeInAndShowImage(imageC);

                                imageC.setScaleType(ImageView.ScaleType.FIT_XY);


                                //Reset
                                model.setIMGCAnimation(null);

                            }

                            // Fade Out
                            if (model.getIMGCAnimation().contains(SYM_FADEOUT)) {
                                //imageL.setVisibility(View.VISIBLE);
                                fadeOutAndHideImage(imageC);
                                model.setIMGCAnimation("");
                            }
                        }
                        catch(NullPointerException e)
                        {e.printStackTrace();}
                    }



                    // Text Presentation Setup
                    if ((!model.gettext().equals("")) && (model.gettext() != null)) {


                        System.err.println("FLAG3: NAMELEFT = " + model.getNameLeft());
                        // Caption for Character name
                        if (model.getNameLeft()!=null)
                        {
                            nameframe.setVisibility(View.VISIBLE);
                            nameTxt.setVisibility(View.VISIBLE);
                            nameTxt.setText(model.getNameLeft());
                            nameTxt.bringToFront();  // Brings to front
                            //RESET NameLeft
                            model.setNameLeft(null);

                        }
                        else{
                            nameframe.setVisibility(View.INVISIBLE);
                            nameTxt.setVisibility(View.INVISIBLE);
                        }


                        System.err.println(model.toString());

                        frame.setVisibility(View.VISIBLE);
                        text1.setVisibility(View.VISIBLE);
                        animateText(model.gettext());

                        /* TESTING */
                        if (model.getfulltext()!=null) {
                            fullTextView.setVisibility(View.VISIBLE);
                            fullTextView.setText(model.getfulltext());
                            model.setfulltext(null);
                        }

                        mTypeSound.reset();
                        mTypeSound.start();
                    } else {
                        frame.setVisibility(View.INVISIBLE);
                        text1.setVisibility(View.INVISIBLE);
                        nameframe.setVisibility(View.INVISIBLE);
                        nameTxt.setVisibility(View.INVISIBLE);

                        model.settext(null);

                    }
                    // Setting them off each time for now (Logic above works, so this is quick fix)
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (this.isFinishing()) {
            theme.stop();
            mTypeSound.stop();
        }

        Intent invIntent = new Intent(GameActivity.this, Inventory.class);
        startActivity(invIntent);


    }

    @Override
    protected void onResume() {
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
