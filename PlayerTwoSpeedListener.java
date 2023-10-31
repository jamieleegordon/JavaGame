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

public class PlayerTwoSpeedListener implements CollisionListener {

    private final Player2 player2;
    private final UserView view;

    private Timer timerCount;
    int timeRemaining = 10;

    private static SoundClip speedSound;

    public PlayerTwoSpeedListener(Player2 p, UserView v){
        this.player2 = p;
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
        if (e.getOtherBody() instanceof Player2){
            speedSound.play();
            e.getReportingBody().destroy();
            // increase player speed
            player2.setWalkingSpeed(player2.getWalkingSpeed() + 10);
            // create new key listener and add the new speed
            view.addKeyListener(new Player2Controller(player2, player2.getWalkingSpeed(), player2.getJumpSpeed()));

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
                    player2.setWalkingSpeed(player2.getWalkingSpeed() - 10);
                    view.addKeyListener(new Player2Controller(player2, player2.getWalkingSpeed(), player2.getJumpSpeed()));
                }
            }
        });
    }

}
