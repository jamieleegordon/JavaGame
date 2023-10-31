import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/** Changes ball to a basketball which is bouncier  */
public class BasketBall implements CollisionListener {

    private final Ball ball;
    private final World world;

    private Timer timerCount;
    private int timeRemaining = 10;

    private static SoundClip basketBallSound;

    public BasketBall(Ball b, World w){
        this.ball = b;
        this.world = w;
    }

    // SOUND
    static {
        try {
            basketBallSound = new SoundClip("data/sounds/basketballSound.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Player1 || e.getOtherBody() instanceof Player2){
            basketBallSound.play();
            e.getReportingBody().destroy();

            ball.removeAllImages();
            ball.addImage(new BodyImage("data/balls/basketBall.png", 1.5f));
            ball.getBallFix().setRestitution(1.1f);

            timerMethod();
            timerCount.start();
        }
    }

    /** Reverts ball back to normal after 10 seconds */
    public void timerMethod() {
        timerCount = new Timer(1000, new ActionListener() { // timer decreases with a 1000-millisecond delay (seconds)

            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--; // decrement time left
                if (timeRemaining <= 0) {
                    timerCount.stop(); // stop the timer when the time is up

                    ball.removeAllImages();
                    ball.addImage(new BodyImage("data/balls/ball.png", 1.5f));
                    ball.getBallFix().setRestitution(1);
                }
            }
        });
    }

}
