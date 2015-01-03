package com.example.jj.jla_project;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.TextView;

import android.os.Handler;

/**
 * Created by Jacky on 11/21/2014.
 */
public class Typewriter extends TextView {
    private CharSequence mText;
    private int mIndex;
    private long mDelay = 500; //Default 500ms delay
    final MediaPlayer mtypeSound;

    public Typewriter(Context context) {
        super(context);
        mtypeSound = MediaPlayer.create(context, R.raw.typeshort);
    }

    public Typewriter(Context context, AttributeSet attrs) {
        super(context, attrs);
        mtypeSound = MediaPlayer.create(context, R.raw.typewriter);
    }

    private Handler mHandler = new Handler();

    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(mText.subSequence(0, mIndex++));
            mtypeSound.start();
            if (mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            }
        }
    };

    public void animateText(CharSequence text) {
        mText = text;
        mIndex = 0;

        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    public void setCharacterDelay(long millis) {
        mDelay = millis;
    }
}
