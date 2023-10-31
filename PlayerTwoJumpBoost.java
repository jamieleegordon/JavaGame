import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.SoundClip;
import city.cs.engine.UserView;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PlayerTwoJumpBoost implements CollisionListener {

    private final Player2 player2;
    private final UserView view;

    private Timer timerCount;
    int timeRemaining = 10;

    private static SoundClip jumpSound;

    public PlayerTwoJumpBoost(Player2 p, UserView v){
        this.player2 = p;
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
        if (e.getOtherBody() instanceof Player2){
            jumpSound.play();
            // set a higher jump speed
            e.getReportingBody().destroy();
            player2.setGravityScale(0.5f);

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
                    player2.setGravityScale(1);
                }
            }
        });
    }

}
