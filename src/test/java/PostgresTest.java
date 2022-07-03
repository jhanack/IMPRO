import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;


public class PostgresTest {
    public static void main(String[] args) throws IOException, SQLException {
        Path q3 = Path.of("resources/queries/q3_sf1.txt");
        String query = Files.readString(q3);
        Postgres Q3_Test = new Postgres();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File("Test_Output.json"), Q3_Test.addExplainToQuery(query));
    }
}
