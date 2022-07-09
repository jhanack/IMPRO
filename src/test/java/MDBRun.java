import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;


public class MDBRun {
    public static void main(String[] args) throws IOException, SQLException {
        Path queryString = Path.of("resources/queries/q3_sf1.txt");
        String query = Files.readString(queryString);
        MariaDB MDBRun = new MariaDB();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File("resources/IR-JSON-outputs/Q3_MDB.json"), MDBRun.addExplainToQuery(query));
    }
}
