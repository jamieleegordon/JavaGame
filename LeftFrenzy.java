import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.SoundClip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class LeftFrenzy implements CollisionListener {

    private final Player2 player2;

    private static SoundClip goalSound;

    public LeftFrenzy(Player2 p2){
        this.player2 = p2;
        goalSound.setVolume(2);
    }

    // SOUND
    static {
        try {
            goalSound = new SoundClip("data/sounds/goalCeleb.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof FrenzyBall) {
            goalSound.play();
            // updates score
            player2.setPlayer2Score(player2.getPlayer2Score() + 1);

            e.getOtherBody().destroy();
        }
    }

}