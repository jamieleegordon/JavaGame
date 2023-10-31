import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**  Jump boost - gives player the ability to jump higher for 10 seconds **/
public class PlayerOneJumpBoost implements CollisionListener {

    private final Player1 player1;
    private final UserView view;

    private Timer timerCount;
    int timeRemaining = 10;

    private static SoundClip jumpSound;

    public PlayerOneJumpBoost(Player1 p, UserView v){
        this.player1 = p;
        this.view = v;
        jumpSound.setVolume(2);
    }

    // SOUND
    static {
        try {
            jumpSound = new SoundClip("data/sounds/jump.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Player1){
            jumpSound.play();
            // set a higher jump speed
            e.getReportingBody().destroy();
            player1.setGravityScale(0.5f);

            timerMethod();
            timerCount.start();
        }
    }

    public void timerMethod() {
        timerCount = new Timer(1000, new ActionListener() { // timer decreases with a 1000-millisecond delay (seconds)

            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--; // decrement time left
                if (timeRemaining <= 0) {
                    timerCount.stop(); // stop the timer when the time is up
                    player1.setGravityScale(1);
                }
            }
        });
    }

}
