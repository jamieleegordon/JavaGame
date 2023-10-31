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
 * - Optical circles moving added that spawn randomly
 * - Ball frenzy added (spawns 5 new balls)
 * */
public class Level4 extends Game {

    private final World world;
    private final JFrame frame;

    private int player1Level1Score;
    private int player2Level1Score;

    private int player1Level2Score;
    private int player2Level2Score;

    private int player1Level3Score;
    private int player2Level3Score;

    private Timer circleTimer;
    private int timeRemaining = 60;

    private static SoundClip song;

    /**
     * @param stadiumBackground - path to image of background
     * @param l1p1Score - player 1's score from level 1
     * @param l1p2Score - player 2's score from level 2
     * */
    public Level4(String stadiumBackground, int l1p1Score, int l1p2Score, int l2p1Score,
                  int l2p2Score, int l3p1Score, int l3p2Score) {

        super(stadiumBackground);
        this.player1Level1Score = l1p1Score;
        this.player2Level1Score = l1p2Score;
        this.player1Level2Score = l2p1Score;
        this.player2Level2Score = l2p2Score;
        this.player1Level3Score = l3p1Score;
        this.player2Level3Score = l3p2Score;

        world = getWorld();
        frame = getFrame();

        System.out.println("LEVEL 3:  " + player1Level3Score + "-" + player2Level3Score);

        getBall().removeAllImages();
        getBall().addImage(new BodyImage("data/balls/finalBall.png",1.5f));


        circleMethod();
        circleTimer.start();

    }

    // SOUND (champions league song)
    static {
        try {
            song = new SoundClip("data/sounds/song.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    // plays song instead of the crowd noise
    @Override
    public void playBackgroundSound(){
        getCrowdSound().stop();
        song.play();
        song.setVolume(2);
        song.loop();
    }

    // prints the results to the console and moves onto a results window
    @Override
    public void nextLevel() {
        System.out.println("LEVEL 4:  " + getPlayer1().getPlayer1Score() + "-" + getPlayer2().getPlayer2Score());

        String level4 = (getPlayer1().getPlayer1Score() + "-" + getPlayer2().getPlayer2Score());

        int p1Score = player1Level1Score + player1Level2Score + player1Level3Score + getPlayer1().getPlayer1Score();
        int p2Score = player2Level1Score + player2Level2Score + player2Level3Score + getPlayer2().getPlayer2Score();

        System.out.println("AGGREGATE SCORE:  " + p1Score + "-" + p2Score);

        String level1 = (player1Level1Score + "-" + player2Level1Score);
        String level2 = (player1Level2Score + "-" + player2Level2Score);
        String level3 = (player1Level3Score + "-" + player2Level3Score);
        String finalR = (p1Score + "-" + p2Score);

        if (p1Score > p2Score) {
            System.out.println("PLAYER 1 WINS");
        } else if (p2Score > p1Score){
            System.out.println("PLAYER 2 WINS");
        } else {
            System.out.println("DRAW");
        }

        frame.dispose();

        try {
            new Results(level1,level2,level3,level4,p1Score,p2Score,finalR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        song.stop();
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

    // spawns circle obstacles every 5 seconds at random positions
    public void circleMethod(){
        circleTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining --;
                if (timeRemaining % 5 == 0) {
                    int rX = randomXcircle();
                    int rY = randomYcircle();
                    OpticalCircle circle = new OpticalCircle(world, rY, new Vec2(rX, rY));
                    circle.setPosition(new Vec2(rX,rY));
                } else if (timeRemaining <= 0){
                    circleTimer.stop();
                }
            }
        });
    }

    @Override
    public void buttonListeners() {
        ActionListener settingsButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Settings(getView(),song, world, frame, getPlayer1(), getPlayer2());
            }
        };

        getSettings().addActionListener(settingsButton);
    }

    @Override
    public void powerUp(){
        String[] powerUps = {"BIG NET", "JUMP BOOST", "BASKETBALL", "SPEED", "PORTAL", "FRENZY", "FRENZY"};
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
            case "FRENZY" -> {
                // spawns 5 new balls
                Shape frenzyShape = new CircleShape(0.3f);
                StaticBody frenzy = new StaticBody(world, frenzyShape);
                frenzy.setPosition(new Vec2(randomX(), randomY()));
                frenzy.addImage(new BodyImage("data/balls/finalBall.png", 1.5f));
                BallFrenzy bF = new BallFrenzy(world);
                frenzy.addCollisionListener(bF);
            }
            default -> {
            }
        }
    }

}
