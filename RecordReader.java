import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/** Reads record in text file **/
public class RecordReader {

    private final String fileName;

    private int draws;

    private int p1Wins;
    private int p2Wins;

    public RecordReader(String fileName) {
        this.fileName = fileName;
    }

    // reads player1 wins, player2 wins and the number of draws
    public void readRecord() throws IOException {
        FileReader fr = null;
        BufferedReader reader = null;
        try {
            fr = new FileReader(fileName);
            reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                String[] tokens = line.split(",");
                p1Wins = Integer.parseInt(tokens[0]);
                draws = Integer.parseInt(tokens[1]);
                p2Wins = Integer.parseInt(tokens[2]);
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

    public int getDraws() {
        return draws;
    }

    public int getP1Wins() {
        return p1Wins;
    }

    public int getP2Wins() {
        return p2Wins;
    }

    public int updateP1Wins(){
        p1Wins = p1Wins + 1;
        return p1Wins;
    }

    public int updateP2Wins (){
        p2Wins = p2Wins + 1;
        return p2Wins;
    }

    public int updateDraws (){
        draws = draws + 1;
        return draws;
    }

}

