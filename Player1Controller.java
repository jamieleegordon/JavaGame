import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player1Controller implements KeyListener {

    private final float WALKING_SPEED;

    private final Player1 player;

    private final int jumpSpeed;

    public Player1Controller(Player1 player, int walkSpeed, int s) {
        this.player = player;
        this.WALKING_SPEED = walkSpeed;
        this.jumpSpeed = s;
    }

    // events occurring when certain keys are being pressed
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_A) {
            player.startWalking(-WALKING_SPEED);
        } else if (code == KeyEvent.VK_D) {
            player.startWalking(WALKING_SPEED);
        } else if (code == KeyEvent.VK_W){
            player.jump(jumpSpeed);
        }
    }

    // stops moving when key is released
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_A) {
            player.stopWalking();
        } else if (code == KeyEvent.VK_D) {
            player.stopWalking();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
