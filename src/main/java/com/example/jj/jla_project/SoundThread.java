package com.example.jj.jla_project;




import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.media.SoundPool;
/**
 * Created by jj on 12/30/14.
 */
public class SoundThread extends Thread { //Will run as backgroundThread
    private SoundPool soundPool;
    public BlockingQueue<SoundItem> sounds = new LinkedBlockingQueue<SoundItem>();
    public boolean stop = false;

    /**
     * Constructor
     *
     * @param FileIO fileIO
     * @param String map
     */
    public SoundThread(SoundPool soundPool) {

        this.soundPool = soundPool;
    }

        /**
         * Dispose a sound
         *
         * @param int soundID
         */
    public void unloadSound(int soundID) {

        this.soundPool.unload(soundID);
    }

    @Override
    /**
     * Wait for sounds to play
     */
    public void run() {

        try {

            SoundItem item;
            while (!this.stop) {

                item = this.sounds.take();
                if (item.stop) {

                    this.stop = true;
                    break;
                }

                this.soundPool.play(item.soundID, item.volume, item.volume, 0, 0, 1);
            }

        } catch (InterruptedException e) {}
    }




}
