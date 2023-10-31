import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

/**
 * - Horizontal Moving platforms added which patrol an area
 * - Portals added
 * */
public class Level3 extends Game {

    private final World world;
    private final JFrame frame;

    private final int player1Level1Score;
    private final int player2Level1Score;

    private final int player1Level2Score;
    private final int player2Level2Score;

    private static SoundClip madrid;

    public Level3(String stadiumBackground, int l1p1Score, int l1p2Score, int l2p1Score, int l2p2Score) {
        super(stadiumBackground);
        this.player1Level1Score = l1p1Score;
        this.player2Level1Score = l1p2Score;
        this.player1Level2Score = l2p1Score;
        this.player2Level2Score = l2p2Score;

        world = getWorld();
        frame = getFrame();

        // moving platforms
        MovingPlatform p2 = new MovingPlatform(world, 7, new Vec2(-15,7));
        p2.setPosition(new Vec2(-15,7));

        MovingPlatform p3 = new MovingPlatform(world, 3, new Vec2(-5,3));
        p3.setPosition(new Vec2(-15,3));

        MovingPlatform p4 = new MovingPlatform(world, 0, new Vec2(-3,0));
        p4.setPosition(new Vec2(0,0));

        MovingPlatform p5 = new MovingPlatform(world, 2, new Vec2(-20,3));
        p5.setPosition(new Vec2(-20,3));

        MovingPlatform p6 = new MovingPlatform(world, 2, new Vec2(13,9));
        p6.setPosition(new Vec2(10,9));

        MovingPlatform p7 = new MovingPlatform(world, 9, new Vec2(0,9));
        p7.setPosition(new Vec2(0,9));

        System.out.println("LEVEL 2:  " + player1Level2Score + "-" + player2Level2Score);

    }

    @Override
    public void nextLevel() {
        frame.dispose();
        new Level4("data/stadiums/final.png", player1Level1Score, player2Level1Score,
                player1Level2Score, player2Level2Score, getPlayer1().getPlayer1Score(),
                getPlayer2().getPlayer2Score());
        madrid.stop();
    }

    @Override
    public void buttonListeners() {
        ActionListener settingsButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Settings(getView(),madrid, world, frame, getPlayer1(), getPlayer2());
            }
        };

        getSettings().addActionListener(settingsButton);
    }

    static {
        try {
            madrid = new SoundClip("data/sounds/madrid.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    @Override
    public void playBackgroundSound(){
        madrid.play();
        madrid.setVolume(0.6f);
        madrid.loop();
    }

    // override powerUp by adding portal ability
    @Override
    public void powerUp(){
        String[] powerUps = {"BIG NET", "JUMP BOOST", "BASKETBALL", "SPEED", "PORTAL", "PORTAL"}; // added portal twice, so it's the most common power up
        String chosenPowerUp = powerUps[new Random().nextInt(powerUps.length)];

        // switch case runs appropriate code depending on what ability is chosen
        switch (chosenPowerUp) {
            case "SPEED" -> {
                // makes player go faster
                Shape speedShape = new BoxShape(0f, 0f);
                StaticBody speed = new StaticBody(world, speedShape);
                speed.setPosition(new Vec2(randomX(), randomY())); // 0,-10
                speed.addImage(new BodyImage("data/powerups/lightningBolt.png", 3));
                PlayerOneSpeedListener p1Speed = new PlayerOneSpeedListener(getPlayer1(), getView());
                speed.addCollisionListener(p1Speed);
                PlayerTwoSpeedListener p2Speed = new PlayerTwoSpeedListener(getPlayer2(), getView());
                speed.addCollisionListener(p2Speed);
            }
            case "BIG NET" -> {
                // makes net bigger
                Shape enlargeShape = new BoxShape(0f, 0);
                StaticBody enlarge = new StaticBody(world, enlargeShape);
                enlarge.setPosition(new Vec2(randomX(), randomY()));
                enlarge.addImage(new BodyImage("data/powerups/goalEnlargement.png", 4));
                RightGoalEnlargement rightEnlarge = new RightGoalEnlargement(getRightCol(), getRightNet(), world, getPlayer1(), getBall());
                enlarge.addCollisionListener(rightEnlarge);
                LeftGoalEnlargement leftEnlarge = new LeftGoalEnlargement(getLeftCol(), getLeftNet(), world, getPlayer2(), getBall());
                enlarge.addCollisionListener(leftEnlarge);
            }
            case "JUMP BOOST" -> {
                // gives a jump boost
                Shape jumpShape = new BoxShape(0f, 0f);
                StaticBody jump = new StaticBody(world, jumpShape);
                jump.setPosition(new Vec2(randomX(), randomY()));
                jump.addImage(new BodyImage("data/powerups/jump.gif", 3));
                PlayerOneJumpBoost p1Jump = new PlayerOneJumpBoost(getPlayer1(), getView());
                jump.addCollisionListener(p1Jump);
                PlayerTwoJumpBoost p2Jump = new PlayerTwoJumpBoost(getPlayer2(), getView());
                jump.addCollisionListener(p2Jump);
            }
            case "BASKETBALL" -> {
                // makes ball bouncier
                Shape basketBallShape = new BoxShape(0f, 0f);
                StaticBody basketBall = new StaticBody(world, basketBallShape);
                basketBall.setPosition(new Vec2(randomX(), randomY()));
                basketBall.addImage(new BodyImage("data/balls/basketBall.png", 2));
                BasketBall b= new BasketBall(getBall(),world);
                basketBall.addCollisionListener(b);
            }
            case "PORTAL" -> {
                // teleports to high position in the world
                Shape portalShape = new BoxShape(0f, 0f);
                StaticBody portal = new StaticBody(world, portalShape);
                portal.setPosition(new Vec2(randomX(), randomY()));
                portal.addImage(new BodyImage("data/powerups/portal.png", 4));
                Portal p= new Portal(getPlayer1(),getPlayer2());
                portal.addCollisionListener(p);
            }
            default -> {
            }
        }
    }

}
