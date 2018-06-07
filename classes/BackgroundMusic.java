package classes;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * @author Nathan Henry
 * @since 2018-05-23
 * @version 2 (current version)
 *
 * Version 1:
 * Author: Nathan Henry
 * Date: 2018-05-23
 * Description: Used java sun audio to make music loop and stop looping
 * Time spent: 20 minutes
 *
 * Version 2:
 * Author: Nathan Henry
 * Date: 2018-05-23
 * Description: Used the clip class for the music
 * Time spent: 10 minutes
 *
 */
public class BackgroundMusic
{
    private static Clip clip;

    /**
     * Default constructor, reads from a file, opens and creates a clip
     */
    public BackgroundMusic()
    {
        try
        {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(
                    "src/resources/sound/Classical_Detective_Music_-_Scary_Violin_Instrumental-EuoJ1SAgOnA.wav")));
        }
        catch (Exception e)
        {
        }
    }

    /**
     * Start method loops the clip
     */
    public static void start()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stop method stops the looping of the clip
     */
    public static void stop()
    {
        clip.stop();
    }

    /**
     * @return Returns whether the clip is active
     */
    public static boolean isPlaying()
    {
        return clip.isActive();
    }
}
