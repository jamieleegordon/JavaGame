import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/** Reads stats **/
public class StatsReader {

    private final String fileName;

    private int matches;

    private int p1Goals;
    private int p2Goals;

    public StatsReader(String fileName) {
        this.fileName = fileName;
    }

    // reads the matches played and average goals per player
    public void readStats() throws IOException {
        FileReader fr = null;
        BufferedReader reader = null;
        try {
            fr = new FileReader(fileName);
            reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                String[] tokens = line.split(",");
                matches = Integer.parseInt(tokens[0]);
                p1Goals = Integer.parseInt(tokens[1]);
                p2Goals = Integer.parseInt(tokens[2]);
                line = reader.readLine();
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
    }

    public int getMatches() {
        return matches;
    }

    public int getP1Goals() {
        return p1Goals;
    }

    public int getP2Goals() {
        return p2Goals;
    }

    public int updateMatches() {
        return matches + 1;
    }

}
