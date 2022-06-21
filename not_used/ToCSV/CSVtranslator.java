// Importing required classes
import java.io.*;
import java.nio.file.*;
import org.apache.commons.io.FileUtils;
import org.json.*;
public class CSVtranslator {

    public static void main(String[] args) {
        String jsonString;
        JSONObject jsonObject;
        try {
            jsonString = new String(
                    Files.readAllBytes(Paths.get("src/main/resources/test.json")));
            jsonObject = new JSONObject(jsonString);
            JSONArray docs
                    = jsonObject.getJSONArray("test");
            File file = new File("src/main/resources/testDone2.csv");
            String csvString = CDL.toString(docs);
            FileUtils.writeStringToFile(file, csvString);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
