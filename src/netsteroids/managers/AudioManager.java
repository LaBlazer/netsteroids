package netsteroids.managers;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.FloatSample;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.unitgen.VariableRateStereoReader;
import com.jsyn.util.SampleLoader;

import java.io.File;

public class AudioManager {
    private static Synthesizer synth;
    private static VariableRateDataReader samplePlayer;
    private static LineOut lineOut;
    private static float volume;

    public AudioManager() {
        synth = JSyn.createSynthesizer();
        lineOut = new LineOut();
        volume = 5f;

        // Add an output mixer.
        synth.add(lineOut);

        this.Start();
    }

    public void Start() {
        synth.start();
        lineOut.start();
        System.out.println("[AudioManager] Started");
    }

    public void Stop() {
        lineOut.stop();
        synth.stop();
    }

    public static void SetVolume(float volume) {
        samplePlayer.amplitude = new UnitInputPort("Amplitude", 0.1 * volume);
    }

    public static void Play(String filename) {
        Play(filename, false, 1.0f);
    }

    public static void Play(String filename, boolean loop) {
        Play(filename, loop, 1.0f);
    }

    public static void Play(String filename, boolean loop, float sampleSpeed) {
        FloatSample sample;
        try {
            sample = SampleLoader.loadFloatSample(new File(filename));

            //System.out.println("[AudioManager] Playing: " + filename);
            if (sample.getChannelsPerFrame() == 1) {
                samplePlayer = new VariableRateMonoReader();
                samplePlayer.output.connect(0, lineOut.input, 0);
            } else if (sample.getChannelsPerFrame() == 2) {
                samplePlayer = new VariableRateStereoReader();
                samplePlayer.output.connect(0, lineOut.input, 0);
                samplePlayer.output.connect(1, lineOut.input, 1);
            } else {
                throw new RuntimeException("Can only play mono or stereo samples.");
            }

            SetVolume(volume);

            synth.add(samplePlayer);

            // Start synthesizer using default stereo output at 44100 Hz.
            samplePlayer.rate.set(sample.getFrameRate() * sampleSpeed);

            // We can simply queue the entire file.
            if(loop)
                samplePlayer.dataQueue.queueLoop(sample);
            else
                samplePlayer.dataQueue.queue(sample);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
