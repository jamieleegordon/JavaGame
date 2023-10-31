import city.cs.engine.*;

public class Ball extends DynamicBody {

    private static final Shape ballShape = new CircleShape(0.3f);

    private static final BodyImage ballImage =
            new BodyImage("data/balls/ball.png", 1.5f);

    private final SolidFixture ballFix;

    public Ball(World world) {
        super(world);
        ballFix = new SolidFixture(this,ballShape);
        addImage(ballImage);
        ballFix.setRestitution(0.9f); // allows the ball to bounce
    }

    public SolidFixture getBallFix() {
        return ballFix;
    }

}
