import city.cs.engine.*;

public class Net extends StaticBody {
    private static final Shape leftNetShape = new BoxShape(1, 5);

    public Net(World world, String netImg) {
        super(world, leftNetShape);
        BodyImage image = new BodyImage(netImg, 9);
        addImage(image);
    }

}


