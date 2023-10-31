import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.SoundClip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class RightFrenzy implements CollisionListener {

    private final Player1 player1;

    private static SoundClip goalSound;

    public RightFrenzy(Player1 p1){
        this.player1 = p1;
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
            player1.setPlayer1Score(player1.getPlayer1Score() + 1);

            e.getOtherBody().destroy();
        }
    }
}

