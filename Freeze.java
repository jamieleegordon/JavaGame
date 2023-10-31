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

/** Freezes opponent for 7 seconds */
public class Freeze implements CollisionListener {

    private final Player1 player1;
    private final Player2 player2;
    private final UserView view;

    private Timer timerCount;
    private int timeRemaining = 7;

    private static SoundClip freeze;

    public Freeze(Player1 p1, Player2 p2, UserView v){
        this.player1 = p1;
        this.player2 = p2;
        this.view = v;
    }

    // SOUND
    static {
        try {
            freeze = new SoundClip("data/sounds/freeze.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    // opponent freezes when collide with ice image
    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Player1){
            freeze.play();
            e.getReportingBody().destroy();
            player2.setWalkingSpeed(player2.getWalkingSpeed() - 5);
            view.addKeyListener(new Player2Controller(player2, player2.getWalkingSpeed(), player2.getJumpSpeed()));
            timerMethod(1);
            timerCount.start();
        }else if (e.getOtherBody() instanceof Player2){
            freeze.play();
            e.getReportingBody().destroy();
            player1.setWalkingSpeed(player1.getWalkingSpeed() - 5);
            view.addKeyListener(new Player1Controller(player1, player1.getWalkingSpeed(), player1.getJumpSpeed()));
            timerMethod(2);
            timerCount.start();
        }
    }

    public void timerMethod(int player){
        timerCount = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining --; // decrement time left
                if (timeRemaining <= 0) {
                    if (player == 1){
                        player2.setWalkingSpeed(player2.getWalkingSpeed() + 1);
                        view.addKeyListener(new Player2Controller(player2, player2.getWalkingSpeed(), player2.getJumpSpeed()));
                    } else if (player == 2){
                        player1.setWalkingSpeed(player1.getWalkingSpeed() + 1);
                        view.addKeyListener(new Player1Controller(player1, player1.getWalkingSpeed(), player1.getJumpSpeed()));
                    }
                }
            }
        });
    }
}
