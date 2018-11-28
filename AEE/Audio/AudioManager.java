package AEE.Audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * AudioManager.java
 * Responsible for loading and playing audio files.
 * Only one audio clip will be loaded at a time.
 *
 * Stephen Fleming 100963909
 */
public class AudioManager {
    /**
     * Container for an audio file, inc. play & stop methods
     */
    public class AudioFile {
        private Clip file;

        public AudioFile(Clip sound) {
            file = sound;
        }

        /**
         * Play the file
         */
        public void play() {
            //source: https://stackoverflow.com/questions/18709391/how-do-i-load-and-play-audio-files-in-a-java-application
            if( file.isRunning() )
                file.stop();

            file.setFramePosition( 0 );
            file.start();
            //end source
        }

        /**
         * Stop the file from playing
         */
        public void stop() {
            try {
                file.stop();
            } catch (NullPointerException e) {
                System.out.println("ERROR: NO AUDIO FILE EXISTS");
            }
        }
    }

    private static AudioManager instance;
    public static AudioManager getInstance() {
        if (instance == null)
            instance = new AudioManager();

        return instance;
    }

    private AudioFile currentSound;

    private AudioManager() { }

    /**
     * Play the currently loaded file
     */
    public void playCurrentFile() {
        if (currentSound != null)
            currentSound.play();
    }

    /**
     * Create a clip out of the passed filename, load as current sound
     * @param name
     */
    public void loadAudioFile(String name) {
        if (currentSound != null) {
            currentSound.stop();
            currentSound = null;
        }

        currentSound = new AudioFile(loadClipFromFile(name));
    }

    /**
     * Take a filename and produce an audio Clip out of it
     * @param name Filename, including file ext.
     * @return Audio clip, null if file does not exist
     */
    private Clip loadClipFromFile(String name) {
        //source: https://stackoverflow.com/questions/18709391/how-do-i-load-and-play-audio-files-in-a-java-application
        Clip in = null;

        try
        {
            URL origin = this.getClass().getResource(name);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(origin);

            in = AudioSystem.getClip();
            in.open( audioIn );
        } catch( Exception e )  {
            e.printStackTrace();
        }

        return in;
        // end source
    }
}
