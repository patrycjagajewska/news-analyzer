package pl.edu.agh.ztis.newsanalyzer;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DictReader {

    private String delimiter = ",";

    public Map<String, String> read(String filepath) {
        Map<String, String> result = new HashMap<>();
        BufferedReader br = null;
        String line;

        try {

            br = new BufferedReader(new FileReader(filepath));
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
