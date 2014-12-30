package com.example.jj.jla_project;

/**
 * Created by jj on 12/30/14.
 */
public class SoundItem {
    // Fields
    public int soundID;
    public float volume;
    public boolean stop = false;

    /**
     * Default constructor
     *
     * @param int soundID
     * @param float volume
     *
     */

    public SoundItem(int soundID, float volume)
    {
        this.stop = stop;
        this.volume = volume;
    }

    /**
     * Constructor for the item
     * which will kill the current thread
     *
     * @param boolean stop
     */
    public SoundItem(boolean stop) {

        this.stop = stop;
    }
}
