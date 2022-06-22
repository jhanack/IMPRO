import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;

public class ToCSV {

    public static void main(String[] args) {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(new File("src/main/resources/data.json"));
            CsvSchema.Builder builder = CsvSchema.builder()
                    .addColumn("name")
                    .addColumn("code")
                    .addColumn("date");


            CsvSchema csvSchema = builder.build().withHeader();

            CsvMapper csvMapper = new CsvMapper();
            csvMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
            csvMapper.writerFor(JsonNode.class)
                    .with(csvSchema)
                    .writeValue(new File("src/main/resources/output/DataDone.csv"), jsonNode);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
