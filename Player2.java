import city.cs.engine.*;

public class Player2 extends Walker {
    private static final Shape messiShape = new BoxShape(2, 2.1f);

    private static final BodyImage image =
            new BodyImage("data/players/messi.png", 5);

    private static final BodyImage RightImage =
            new BodyImage("data/players/messiRight.png", 5);

    private int player2Score;

    private int walkingSpeed;
    private int jumpSpeed;

    /**
     * - Default stats (at the start of each level):
     *         - Walk speed of 5
     *         - Jump speed of 11
     *         - Score of 0
     * */
    public Player2(World world) {
        super(world, messiShape);
        addImage(image);
        player2Score = 0;
        walkingSpeed = 5;
        jumpSpeed = 11;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    // will flip the image depending on where the player is facing
    @Override
    public void startWalking(float speed) {
        super.startWalking(speed);
        if(speed < 0) {
            this.removeAllImages();
            this.addImage(image);
        } else {
            this.removeAllImages();
            this.addImage(RightImage);
        }
    }

    public int getWalkingSpeed() {
        return walkingSpeed;
    }
    public void setWalkingSpeed(int walkingSpeed) {
        this.walkingSpeed = walkingSpeed;
    }
    public int getJumpSpeed() {
        return jumpSpeed;
    }
    public void setJumpSpeed(int jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }
}

