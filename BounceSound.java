import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.SoundClip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/** Ball will make a bounce sound whenever it collides with something */
public class BounceSound implements CollisionListener {

    private static SoundClip bounceSound;

    public BounceSound(){
        bounceSound.setVolume(2);
    }

    // COLLISION SOUND
    static {
        try {
            bounceSound = new SoundClip("data/sounds/bounce.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() != null){ // if the ball collides with anything
            bounceSound.play();
        }
    }
}
