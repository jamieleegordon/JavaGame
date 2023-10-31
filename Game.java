import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

/** BASE GAME CLASS (also used as level 1)
 * - Levels 2,3 and 4 will all inherit the same bodies and functionality as this world
 * */
public class Game  {

    // objects of each player
    private final Player1 player1;
    private final Player2 player2;
    private final Ball ball;

    private final World world;
    private final GameView view;

    private final JFrame frame;

    private Timer timer;
    private int timeRemaining = 60;

    private final StaticBody leftCol;
    private final StaticBody rightCol;
    private final Net rightNet;
    private final Net leftNet;

    private static SoundClip crowdSound;

    String leftNetImg = "data/nets/leftNet.png";
    String rightNetImg = "data/nets/rightNet.png";

    private JButton settings;

    // CONSTRUCTOR
    public Game(String stadiumBackground) {

        playBackgroundSound();

        // world for the game
        world = new World();

        // instantiates new player objects;
        player1 = new Player1(world);
        player2 = new Player2(world);

        // world view
        view = new GameView(world,1024, 500, stadiumBackground, player1, player2);

        // allows focus into game window to detect mouse or keyboard inputs
        view.addMouseListener(new GiveFocus(view));

        // frame
        frame = new JFrame("Soccer Head");
        frame.add(view);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        view.setLayout(new FlowLayout(FlowLayout.LEADING));

        // Settings button (opens up new window with settings buttons and options)
        settings = new JButton("SETTINGS");
        settings.setSize(2,2);
        view.add(settings);
        buttonListeners();

        // RONALDO (PLAYER 1)
        player1.setPosition(new Vec2(-10, -7));
        view.addKeyListener(new Player1Controller(player1, player1.getWalkingSpeed(),
                player1.getJumpSpeed())); // keyboard interaction for player1

        // MESSI (PLAYER 2)
        player2.setPosition(new Vec2(10, -7));
        view.addKeyListener(new Player2Controller(player2, player2.getWalkingSpeed(),
                player2.getJumpSpeed())); // keyboard interaction for player2

        // BALL
        ball = new Ball(world);
        ball.setPosition(new Vec2(0.1f, 0));

        BounceSound bounceSound = new BounceSound();
        ball.addCollisionListener(bounceSound);

        // left goal collision wall
        // if the ball collides with this shape, player 2 scores a goal
        Shape leftColShape = new BoxShape(0, 3.2f);
        leftCol = new StaticBody(world, leftColShape);
        leftCol.setPosition(new Vec2(-23, -9));

        // allows for collisions of ball in level 4
        LeftFrenzy lF = new LeftFrenzy(player2);
        leftCol.addCollisionListener(lF);

        // collision event (Player 2 scores a goal)
        LeftGoalCollision player2Goal = new LeftGoalCollision(player2, world, ball);
        leftCol.addCollisionListener(player2Goal);

        // right goal collision wall
        // if the ball collides with this shape, player 1 scores a goal
        Shape rightColShape = new BoxShape(0, 3.2f);
        rightCol = new StaticBody(world, rightColShape);
        rightCol.setPosition(new Vec2(23, -9));

        // allows for collisions of ball in level 4
        RightFrenzy rF = new RightFrenzy(player1);
        rightCol.addCollisionListener(rF);

        // collision event (Player 1 scores a goal)
        RightGoalCollision player1Goal = new RightGoalCollision(player1, world, ball);
        rightCol.addCollisionListener(player1Goal);

        // NETS
        rightNet = new Net(world, rightNetImg);
        rightNet.setPosition(new Vec2(24, -8));

        leftNet = new Net(world, leftNetImg);
        leftNet.setPosition(new Vec2(-24, -8));

        // floor platform
        Shape floorPlatformShape = new BoxShape(250, 0);
        StaticBody floorPlatform = new StaticBody(world, floorPlatformShape);
        floorPlatform.setPosition(new Vec2(0, -11.78f));

        // ceiling platform
        Shape ceilingPlatformShape = new BoxShape(250, 0);
        StaticBody ceilingPlatform = new StaticBody(world, ceilingPlatformShape);
        ceilingPlatform.setPosition(new Vec2(0, 11.7f));

        // left platform
        Shape leftPlatformShape = new BoxShape(0, 500);
        StaticBody leftPlatform = new StaticBody(world, leftPlatformShape);
        leftPlatform.setPosition(new Vec2(-25, 0));

        // right platform
        Shape rightPlatformShape = new BoxShape(0, 500);
        StaticBody rightPlatform = new StaticBody(world, rightPlatformShape);
        rightPlatform.setPosition(new Vec2(25, 0));

        // left blockage
        Shape leftBlockShape = new BoxShape(0.3f, 20);
        StaticBody leftBlock = new StaticBody(world, leftBlockShape);
        leftBlock.setPosition(new Vec2(31, 0));
        leftBlock.setAngleDegrees(50);

        // right blockage
        Shape rightBlockShape = new BoxShape(0.3f, 20);
        StaticBody rightBlock = new StaticBody(world, rightBlockShape);
        rightBlock.setPosition(new Vec2(-31, 0));
        rightBlock.setAngleDegrees(-50);

        timerMethod();
        timer.start();

        // DEBUG VIEW
        //JFrame debugView = new DebugViewer(world, 1024, 500);

        world.start();
    }

    // TRANSITIONS TO NEXT LEVEL
    public void nextLevel() {
        frame.dispose();
        new Level2("data/stadiums/CampNou.png", player1.getPlayer1Score(), player2.getPlayer2Score());
        crowdSound.stop();
    }

    // CROWD SOUND
    static {
        try {
            crowdSound = new SoundClip("data/sounds/crowdBackground.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    // plays the background sound
    public void playBackgroundSound(){
        crowdSound.play();
        crowdSound.setVolume(0.6f);
        crowdSound.loop();
    }

    public void timerMethod(){
        timer = new Timer(1000, new ActionListener() { // timer decreases with a 1000-millisecond delay (seconds)

            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining --;
                if (timeRemaining % 8 == 0) { // every 8 seconds
                    powerUp();
                } else if (timeRemaining <= 0){
                    timer.stop();
                    nextLevel();
                }
            }
        });
    }

    // generates random x and y coordinates for the pick-ups to spawn
    public int randomX (){
        int min = -19;
        int max = 19;

        return (int) (Math.random() * (max - min)) + min;
    }
    public int randomY (){
        int min = -10;
        int max = -1;

        return (int) (Math.random() * (max - min)) + min;
    }

    /**
     * this method is triggered every 8 seconds
     * - using the random method, it will select a random string from the powerups list
     * - depending on what string is chosen, the switch case is used and will spawn that specific power up*/
    // will spawn a random ability every 8 seconds
    public void powerUp(){
        String[] powerUps = {"SPEED", "BIG NET", "JUMP BOOST", "BASKETBALL"};
        String chosenPowerUp = powerUps[new Random().nextInt(powerUps.length)]; // selects random ability within powerUps list

        // switch case runs appropriate code depending on what ability is chosen
        switch (chosenPowerUp) {
            case "SPEED" -> {
                // makes player go faster
                Shape speedShape = new BoxShape(0f, 0f);
                StaticBody speed = new StaticBody(world, speedShape);
                speed.setPosition(new Vec2(randomX(), randomY())); // 0,-10
                speed.addImage(new BodyImage("data/powerups/lightningBolt.png", 3));
                PlayerOneSpeedListener p1Speed = new PlayerOneSpeedListener(player1, view);
                speed.addCollisionListener(p1Speed);
                PlayerTwoSpeedListener p2Speed = new PlayerTwoSpeedListener(player2, view);
                speed.addCollisionListener(p2Speed);
            }
            case "BIG NET" -> {
                // makes net bigger
                Shape enlargeShape = new BoxShape(0f, 0);
                StaticBody enlarge = new StaticBody(world, enlargeShape);
                enlarge.setPosition(new Vec2(randomX(), randomY()));
                enlarge.addImage(new BodyImage("data/powerups/goalEnlargement.png", 4));
                RightGoalEnlargement rightEnlarge = new RightGoalEnlargement(rightCol, rightNet, world, player1, ball);
                enlarge.addCollisionListener(rightEnlarge);
                LeftGoalEnlargement leftEnlarge = new LeftGoalEnlargement(leftCol, leftNet, world, player2, ball);
                enlarge.addCollisionListener(leftEnlarge);
            }
            case "JUMP BOOST" -> {
                // gives a jump boost
                Shape jumpShape = new BoxShape(0f, 0f);
                StaticBody jump = new StaticBody(world, jumpShape);
                jump.setPosition(new Vec2(randomX(), randomY()));
                jump.addImage(new BodyImage("data/powerups/jump.gif", 3));
                PlayerOneJumpBoost p1Jump = new PlayerOneJumpBoost(player1, view);
                jump.addCollisionListener(p1Jump);
                PlayerTwoJumpBoost p2Jump = new PlayerTwoJumpBoost(player2, view);
                jump.addCollisionListener(p2Jump);
            }
            case "BASKETBALL" -> {
                // makes ball bouncier
                Shape basketBallShape = new BoxShape(0f, 0f);
                StaticBody basketBall = new StaticBody(world, basketBallShape);
                basketBall.setPosition(new Vec2(randomX(), randomY()));
                basketBall.addImage(new BodyImage("data/balls/basketBall.png", 2));
                BasketBall b= new BasketBall(ball,world);
                basketBall.addCollisionListener(b);
            }
            default -> {
            }
        }
    }

    public void buttonListeners() {
        ActionListener settingsButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Settings(view,crowdSound, world, frame, player1,player2);
            }
        };

        settings.addActionListener(settingsButton);
    }

    public Player1 getPlayer1() {
        return player1;
    }
    public Player2 getPlayer2() {
        return player2;
    }
    public World getWorld() {
        return world;
    }
    public GameView getView() {
        return view;
    }
    public JFrame getFrame() {
        return frame;
    }
    public Ball getBall() {
        return ball;
    }
    public static SoundClip getCrowdSound() {
        return crowdSound;
    }
    public StaticBody getLeftCol() {
        return leftCol;
    }
    public StaticBody getRightCol() {
        return rightCol;
    }
    public Net getRightNet() {
        return rightNet;
    }
    public Net getLeftNet() {
        return leftNet;
    }
    public String getLeftNetImg() {
        return leftNetImg;
    }
    public void setLeftNetImg(String leftNetImg) {
        this.leftNetImg = leftNetImg;
    }
    public String getRightNetImg() {
        return rightNetImg;
    }
    public void setRightNetImg(String rightNetImg) {
        this.rightNetImg = rightNetImg;
    }
    public JButton getSettings() {
        return settings;
    }
    public int getTimeRemaining() {
        return timeRemaining;
    }
}


