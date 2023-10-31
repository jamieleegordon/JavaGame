import java.io.FileWriter;
import java.io.IOException;

/** Writes the player1 vs player2 stats into a txt file
 * matches played
 * average amount of goals scored by player 1 per match
 * average amount of goals scored by player 2 per match
 * */
public class StatsWriter {

    private final String fileName;

    public StatsWriter(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @param matches - matches played
     * @param p1goals - goals scored by player 1
     * @param p2goals - goals scored by player 2
     * */
    // updates the matches played and average goals per player
    public void writeStats(int matches, int p1goals, int p2goals) throws IOException {
        boolean append = false;
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName, false);
            writer.write(matches + "," + p1goals + "," + p2goals + "\n");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}

