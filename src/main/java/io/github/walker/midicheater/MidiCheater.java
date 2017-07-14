package io.github.walker.midicheater;

import javax.sound.midi.MidiUnavailableException;

/**
 * Created by VASCTechLab3 on 7/13/2017.
 */
public class MidiCheater {
    public static void main(String[] args) throws MidiUnavailableException {
        new FakeWindow().create();
    }
}
