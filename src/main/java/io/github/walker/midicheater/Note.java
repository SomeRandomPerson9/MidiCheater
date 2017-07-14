package io.github.walker.midicheater;

import javax.sound.midi.MidiChannel;

/**
 * Created by VASCTechLab3 on 7/13/2017.
 */
public class Note {
    private int key;

    private long startTime;

    private long endTime;

    private int velocity;

    public Note(int key, int velocity, long startTime){
        this.key = key;
        this.velocity = velocity;
        this.startTime = startTime;
    }

    public int getKey(){
        return this.key;
    }

    public boolean isClosed(){
        return endTime != 0;
    }

    public long getStartTime(){ return this.startTime; }

    public void setEndTime(long time){
        this.endTime = time;
    }

    public void play(final MidiChannel channel){
        new Thread(){
            @Override
            public void run(){
                try {
                    if(endTime - startTime > 0) {
                        channel.noteOn(key, velocity);
                        Thread.sleep(endTime - startTime);
                        channel.noteOff(key, velocity);
                    } else{
                        channel.noteOn(key, velocity);
                        Thread.sleep(1000);
                        channel.noteOff(key, velocity);
                    }
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }.start();
    }
}
