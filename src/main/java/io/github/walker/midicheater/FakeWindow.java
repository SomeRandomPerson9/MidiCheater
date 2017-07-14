package io.github.walker.midicheater;

import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * Created by VASCTechLab3 on 7/13/2017.
 */
public class FakeWindow implements HotKeyListener{
    private JButton openMidiButton;
    private JLabel midiLabel;
    private JPanel contentPane;
    private JButton playButton;

    private MidiFile midi;
    private MidiChannel channel;

    public void create() throws MidiUnavailableException {

        Synthesizer midiSynth = MidiSystem.getSynthesizer();
        midiSynth.open();

        //get and load default instrument and channel lists
        Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
        final MidiChannel[] mChannels = midiSynth.getChannels();

        midiSynth.loadInstrument(instr[0]);//load an instrument

        channel = mChannels[0];

        JFrame frame = new JFrame("The Greatest");
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Provider provider = Provider.getCurrentProvider(true);
        provider.register(KeyStroke.getKeyStroke(' '), this);
        openMidiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(contentPane);
                if(chooser.getSelectedFile() != null) {
                    try {
                        setMidi(Loader.load(chooser.getSelectedFile()));
                    } catch (InvalidMidiDataException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onHotKey(null);
            }
        });
        frame.setVisible(true);
    }

    public void setMidi(MidiFile midi){
        this.midi = midi;
        this.midiLabel.setText("Current Midi: " + midi.getName());
    }

    public void onHotKey(HotKey hotKey) {
        System.out.println("keyPressed");
        midi.playNext(channel);
    }
}
