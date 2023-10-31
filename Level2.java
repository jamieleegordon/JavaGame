import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

/**
 * - Lava platforms added (ball will set on fire and become more bouncy when collided with)
 * - Circles randomly spawn, making game harder
 * - Freeze ability added
 * */
public class Level2 extends Game {

    private final World world;
    private final JFrame frame;

    private final int player1Level1Score;
    private final int player2Level1Score;

    private Timer circleTimer;
    private int timeRemaining = 60;

    private static SoundClip barca;

    public Level2(String stadiumBackground, int l1p1Score, int l1p2Score) {
        super(stadiumBackground);
        this.player1Level1Score = l1p1Score;
        this.player2Level1Score = l1p2Score;

        world = getWorld();
        frame = getFrame();

        System.out.println("LEVEL 1:  " + player1Level1Score + "-" + player2Level1Score);

        // NEW BODIES/SHAPES
        // Lava platforms (ball turns on fire and more bouncy)
        Shape leftLavaShape = new PolygonShape(6.15f,-0.217f, -6.052f,-0.242f, -6.027f,0.25f, 6.101f,0.25f);
        StaticBody leftLava = new StaticBody(world, leftLavaShape);
        leftLava.addImage(new BodyImage("data/stadiums/lava.gif", 0.5f));
        leftLava.setPosition(new Vec2(-11, 4.5f));
        leftLava.setAngleDegrees(-40);
        FireBall fire1 = new FireBall(getBall());
        leftLava.addCollisionListener(fire1);

        Shape rightLavaShape = new PolygonShape(6.15f,-0.217f, -6.052f,-0.242f, -6.027f,0.25f, 6.101f,0.25f);
        StaticBody rightLava = new StaticBody(world, rightLavaShape);
        rightLava.addImage(new BodyImage("data/stadiums/lava.gif", 0.5f));
        rightLava.setPosition(new Vec2(11, 4.5f));
        rightLava.setAngleDegrees(40);
        FireBall fire2 = new FireBall(getBall());
        rightLava.addCollisionListener(fire2);

        circleMethod();
        circleTimer.start();

    }

    static {
        try {
            barca = new SoundClip("data/sounds/barca.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    @Override
    public void nextLevel() {
        frame.dispose();
        new Level3("data/stadiums/SantiagoBernabeu.png", player1Level1Score, player2Level1Score,
                getPlayer1().getPlayer1Score(), getPlayer2().getPlayer2Score());
        barca.stop();
    }

    public int randomXcircle (){
        int min = -19;
        int max = 19;

        return (int) (Math.random() * (max - min)) + min;
    }
    public int randomYcircle (){
        int min = -4;
        int max = 11;

        return (int) (Math.random() * (max - min)) + min;
    }

    // spawns circle obstacles every 9 seconds at random positions
    public void circleMethod(){
        circleTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining --;
                if (timeRemaining % 9 == 0) {
                    Shape circle = new CircleShape(1.8f);
                    StaticBody circleBody = new StaticBody(world, circle);
                    circleBody.setLineColor(null);
                    circleBody.setFillColor(Color.ORANGE);
                    circleBody.setPosition(new Vec2(randomXcircle(),randomYcircle()));
                } else if (timeRemaining <= 0){
                    circleTimer.stop();
                }
            }
        });
    }

    @Override
    public void playBackgroundSound(){
        barca.play();
        barca.setVolume(0.6f);
        barca.loop();
    }

    @Override
    public void buttonListeners() {
        ActionListener settingsButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Settings(getView(),barca, world, frame, getPlayer1(), getPlayer2());
            }
        };

        getSettings().addActionListener(settingsButton);
    }

    /**
     * power up is overridden to add level specific power ups (in this case "FREEZE")
     * - also removes certain power ups from previous levels (level 2 doesn't have speed, instead has been replaced by freeze*/
    // override powerUp
    @Override
    public void powerUp(){
        String[] powerUps = {"FREEZE", "BIG NET", "JUMP BOOST", "BASKETBALL"};
        String chosenPowerUp = powerUps[new Random().nextInt(powerUps.length)]; // selects random ability within powerUps list

        // switch case runs appropriate code depending on what ability is chosen
        switch (chosenPowerUp) {
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
            case "FREEZE" -> {
                // opposition can't move
                Shape freezeShape = new BoxShape(0f, 0f);
                StaticBody freeze = new StaticBody(world, freezeShape);
                freeze.setPosition(new Vec2(randomX(), randomY()));
                freeze.addImage(new BodyImage("data/powerups/ice.png", 4));
                Freeze f = new Freeze(getPlayer1(),getPlayer2(),getView());
                freeze.addCollisionListener(f);
            }
            default -> {
            }
        }
    }
}
