import city.cs.engine.*;

public class Player1 extends Walker {
    private static final Shape ronaldoShape = new BoxShape(2f, 2.1f);

    private static final BodyImage image =
            new BodyImage("data/players/ronaldo.png", 5);

    private static final BodyImage leftImage =
            new BodyImage("data/players/ronaldoLeft.png", 5);

    private int player1Score;

    private int walkingSpeed;
    private int jumpSpeed;

    /**
     * - Default stats (at the start of each level):
     *         - Walk speed of 5
     *         - Jump speed of 11
     *         - Score of 0
     * */
    public Player1(World world) {
        super(world, ronaldoShape);
        walkingSpeed = 5;
        jumpSpeed = 11;
        addImage(image);
        player1Score = 0;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    // will flip the image depending on where the player is facing
    @Override
    public void startWalking(float speed) {
        super.startWalking(speed);
        if(speed < 0) {
            this.removeAllImages();
            this.addImage(leftImage);
        } else {
            this.removeAllImages();
            this.addImage(image);
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
