import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.SoundClip;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class RightGoalCollision implements CollisionListener {

    private final Player1 player1;
    private final World world;
    private final Ball ball;

    private static SoundClip goalSound;

    public RightGoalCollision(Player1 p1, World w, Ball b){
        this.player1 = p1;
        this.world = w;
        this.ball = b;
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
        if (e.getOtherBody() instanceof Ball) {
            goalSound.play();
            // updates score
            player1.setPlayer1Score(player1.getPlayer1Score() + 1);

            // creates a new ball
            // creates a new ball
            ball.setLinearVelocity(new Vec2(0,0));
            ball.setAngularVelocity(0);
            ball.setPosition(new Vec2(0.1f, 0));
        }
    }
}
