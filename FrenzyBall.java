import city.cs.engine.*;

// new ball that will be used in the final level
public class FrenzyBall extends DynamicBody {

    private static final Shape ballShape = new CircleShape(0.3f);

    private static final BodyImage ballImage =
            new BodyImage("data/balls/finalBall.png", 1.5f);

    private SolidFixture ballFix;

    public FrenzyBall(World world) {
        super(world);
        ballFix = new SolidFixture(this,ballShape);
        addImage(ballImage);
        ballFix.setRestitution(1);
    }

}
