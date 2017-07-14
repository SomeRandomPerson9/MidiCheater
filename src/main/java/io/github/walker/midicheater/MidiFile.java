package io.github.walker.midicheater;

import javax.sound.midi.MidiChannel;
import java.util.ArrayList;

/**
 * Created by VASCTechLab3 on 7/13/2017.
 */
public class MidiFile {
    private ArrayList<Note> notes;
    private ArrayList<Long> counts = new ArrayList<Long>();

    private String name;

    private int count = 0;

    public MidiFile(String name, ArrayList<Note> notes){
        this.name = name;
        this.notes = notes;
        for(Note note : notes)
            if(!counts.contains(note.getStartTime()))
                counts.add(note.getStartTime());

    }

    public void addNote(Note note){
        notes.add(note);
        if(!counts.contains(note.getStartTime()))
            counts.add(note.getStartTime());
    }

    public String getName(){
        return this.name;
    }

    public void playNext(MidiChannel channel){
        for(Note note : notes)
            if(note != null && note.getStartTime() == counts.get(count))
                note.play(channel);
        count++;
    }
}
