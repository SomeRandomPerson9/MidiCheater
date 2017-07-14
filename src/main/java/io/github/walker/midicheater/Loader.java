package io.github.walker.midicheater;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by VASCTechLab3 on 7/13/2017.
 */
public class Loader {
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;

    public static MidiFile load(File file) throws InvalidMidiDataException, IOException {
        ArrayList<Note> notes = new ArrayList<Note>();

        Sequence sequence = MidiSystem.getSequence(file);

        for (Track track : sequence.getTracks()) {
            //System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                System.out.print("@" + event.getTick() + " ");
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    System.out.print("Channel: " + sm.getChannel() + " ");
                    if (sm.getCommand() == NOTE_ON) {
                        int key = sm.getData1();
                        int octave = (key / 12) - 1;
                        int note = key % 12;
                        //String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        //System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);

                        notes.add(new Note(key, velocity, event.getTick()));

                    } else if (sm.getCommand() == NOTE_OFF) {
                        int key = sm.getData1();
                        int octave = (key / 12) - 1;
                        int note = key % 12;
                        //String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();

                        //System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);

                        for (Note note1 : notes)
                            if (!note1.isClosed())
                                if (note1.getKey() == key)
                                    note1.setEndTime(event.getTick());
                    } else {
                        System.out.println("Other message: " + message.getClass());
                    }
                }

                System.out.println();
            }
        }
        return new MidiFile(file.getName(), notes);

    }
}
