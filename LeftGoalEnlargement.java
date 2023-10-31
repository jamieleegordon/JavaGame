import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/** Goal gets bigger for 7 seconds */
public class LeftGoalEnlargement implements CollisionListener {

    private final StaticBody collisionWall;
    private final World world;
    private final Net leftNet;
    private final Player2 player2;

    private final Ball ball;

    private Shape netShape;
    private StaticBody net;
    private Shape leftColShape;
    private StaticBody leftCol;

    private Timer timerCount;
    private int timeRemaining = 7;

    private static SoundClip enlargementSound;

    public LeftGoalEnlargement(StaticBody b, Net n, World w, Player2 p, Ball ba) {
        this.collisionWall = b;
        this.world = w;
        this.leftNet = n;
        this.player2 = p;
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
        if (e.getOtherBody() instanceof Player2) {
            enlargementSound.play();
            e.getReportingBody().destroy();

            leftNet.destroy();
            collisionWall.destroy();

            netShape = new BoxShape(1, 5);
            net = new StaticBody(world, netShape);
            net.addImage(new BodyImage("data/nets/leftNet.png", 12));
            net.setPosition(new Vec2(-24, -6.5f));

            leftColShape = new BoxShape(0, 6);
            leftCol = new StaticBody(world, leftColShape);
            leftCol.setPosition(new Vec2(-23, -9));

            LeftGoalCollision player2Goal = new LeftGoalCollision(player2, world, ball);
            leftCol.addCollisionListener(player2Goal);

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
                    leftCol.destroy();

                    Net newLeftNet = new Net(world, "data/nets/leftNet.png");
                    newLeftNet.setPosition(new Vec2(-24, -8));

                    Shape leftColShape = new BoxShape(0, 3.2f);
                    StaticBody leftCol = new StaticBody(world, leftColShape);
                    leftCol.setPosition(new Vec2(-23, -9));

                    LeftGoalCollision player2Goal = new LeftGoalCollision(player2, world, ball);
                    leftCol.addCollisionListener(player2Goal);
                }
            }
        });

    }
}
