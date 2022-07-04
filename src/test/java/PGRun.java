import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;


public class PGRun {
    public static void main(String[] args) throws IOException, SQLException {
        Path queryString = Path.of("resources/queries/q4_sf1.txt");
        String query = Files.readString(queryString);
        Postgres PGRun = new Postgres();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File("resources/IR-JSON-outputs/Q4_PG.json"), PGRun.addExplainToQuery(query));
    }
}
