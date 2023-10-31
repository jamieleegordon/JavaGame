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

/** Speed boost - boosts the player walking speed for 10 seconds **/
public class PlayerOneSpeedListener implements CollisionListener {

    private final Player1 player1;
    private final UserView view;

    private Timer timerCount;
    private int timeRemaining = 10;

    private static SoundClip speedSound;

    public PlayerOneSpeedListener(Player1 p, UserView v){
        this.player1 = p;
        this.view = v;
        speedSound.setVolume(2);
    }

    // SOUND
    static {
        try {
            speedSound = new SoundClip("data/sounds/speed.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Player1){
            speedSound.play();
            e.getReportingBody().destroy();
            // increase player speed
            player1.setWalkingSpeed(player1.getWalkingSpeed() + 10);
            // create new key listener and add the new speed
            view.addKeyListener(new Player1Controller(player1, player1.getWalkingSpeed(), player1.getJumpSpeed()));
            timerMethod();
            timerCount.start();
        }
    }

    // speed ability will only last 10 seconds
    public void timerMethod(){
        timerCount = new Timer(1000, new ActionListener() { // timer decreases with a 1000-millisecond delay (seconds)

            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining --; // decrement time left
                if (timeRemaining <= 0) {
                    timerCount.stop(); // stop the timer when the time is up
                    player1.setWalkingSpeed(player1.getWalkingSpeed() - 10);
                    view.addKeyListener(new Player1Controller(player1, player1.getWalkingSpeed(), player1.getJumpSpeed()));
                }
            }
        });
    }

}
