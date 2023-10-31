import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RightGoalEnlargement implements CollisionListener {

    private final StaticBody collisionWall;
    private final World world;
    private final Net rightNet;
    private final Player1 player1;

    private final Ball ball;

    private StaticBody net;
    private StaticBody rightCol;

    private Timer timerCount;
    private int timeRemaining = 7;

    private static SoundClip enlargementSound;

    public RightGoalEnlargement(StaticBody b, Net n, World w, Player1 p, Ball ba){
        this.collisionWall = b;
        this.world = w;
        this.rightNet = n;
        this.player1 = p;
        this.ball = ba;
        enlargementSound.setVolume(2);
    }

    // SOUND
    static {
        try {
            enlargementSound = new SoundClip("data/sounds/bigGoal.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Player1){
            enlargementSound.play();
            e.getReportingBody().destroy();

            rightNet.destroy();
            collisionWall.destroy();

            Shape netShape = new BoxShape(1,5);
            net = new StaticBody(world, netShape);
            net.addImage(new BodyImage("data/nets/rightNet.png",12));
            net.setPosition(new Vec2(24, -6.5f));

            Shape rightColShape = new BoxShape(0, 6);
            rightCol = new StaticBody(world, rightColShape);
            rightCol.setPosition(new Vec2(23, -9));

            RightGoalCollision player1Goal = new RightGoalCollision(player1, world, ball);
            rightCol.addCollisionListener(player1Goal);

            timerMethod();
            timerCount.start();
        }
    }

    // sets net back to original size after 7 seconds
    public void timerMethod() {
        timerCount = new Timer(1000, new ActionListener() { // timer decreases with a 1000-millisecond delay (seconds)

            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--; // decrement time left
                if (timeRemaining <= 0) {
                    timerCount.stop(); // stop the timer when the time is up

                    net.destroy();
                    rightCol.destroy();

                    Net rightNet = new Net(world, "data/nets/rightNet.png");
                    rightNet.setPosition(new Vec2(24, -8));

                    Shape rightColShape = new BoxShape(0, 3.2f);
                    StaticBody rightCol = new StaticBody(world, rightColShape);
                    rightCol.setPosition(new Vec2(23, -9));

                    RightGoalCollision player1Goal = new RightGoalCollision(player1, world, ball);
                    rightCol.addCollisionListener(player1Goal);
                }
            }
        });

    }

}
