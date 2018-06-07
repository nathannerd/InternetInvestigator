package classes;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.File;

/**
 * @author Nathan Henry
 * @since 2018-05-19
 * @version 2
 *
 * Version 1:
 * 2018-05-19
 * Hours spent on TextScreen-related classes: 2
 * Description: Used Sun Audio to make an enter resources.sound on a typewriter as a runnable.
 *
 * Version 2:
 * 2018-06-01
 * Hours spent on TextScreen-related classes: 1
 * Description: Sun Audio wouldn't work on other devices without downloading it, so I changed the audio system.
 */
public class Enter implements Runnable {

    /**
     * Plays .wav file
     */
    public void run()
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("src/resources/sound/TypewriterEnter.wav")));
            clip.start();
        }
        catch (Exception e)
        {
        }
    }
}
