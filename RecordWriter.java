import java.io.FileWriter;
import java.io.IOException;

/** Writes the player1 vs player2 record into a txt file
 * player 1 wins
 * player 2 wins
 * draws
 * */
public class RecordWriter {

    private final String fileName;

    public RecordWriter(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @param p1Wins - player 1 wins
     * @param p2Wins - player 2 wins
     * @param draws - draws
     * */
    public void writeRecord(int p1Wins, int draws, int p2Wins) throws IOException {
        boolean append = false;
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName, false);
            writer.write(p1Wins + "," + draws + "," + p2Wins + "\n");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
