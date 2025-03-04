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
 * Description: Used Sun Audio to make an typing sound on a typewriter as a runnable.
 *
 * Version 2:
 * 2018-06-01
 * Hours spent on TextScreen-related classes: 1
 * Description: Sun Audio wouldn't work on other devices without downloading it, so I changed the audio system.
 */

public class Type implements Runnable {
    private File file[] = new File[4];

    public Type()
    {
        file[0] = new File("Typewriter1.wav");
        file[1] = new File("Typewriter2.wav");
        file[2] = new File("Typewriter3.wav");
    }

    public void run()
    {
        letter();
    }

    public void letter()
    {
        int random = (int)(Math.random()*3);
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file[random]));
            clip.start();
        }
        catch (Exception e)
        {
        }
    }

}
