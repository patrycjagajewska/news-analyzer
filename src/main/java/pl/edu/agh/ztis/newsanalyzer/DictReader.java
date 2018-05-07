package pl.edu.agh.ztis.newsanalyzer;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DictReader {

    private String filepath;
    private String delimiter = ",";

    public DictReader(String filepath) {
        this.filepath = filepath;
    }

    public Map<String, String> read() {
        Map<String, String> result = new HashMap<>();
        BufferedReader br = null;
        String line;

        try {

            br = new BufferedReader(new FileReader(this.filepath));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] pair = line.split(this.delimiter);
                result.put(pair[0], pair[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
