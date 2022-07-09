import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class OnlyWriteExplainJSONs {

    private static Connection dbConn1;
    private static Connection dbConn2;

    public static void main(String[] args) throws SQLException, IOException {

        Path queryString = Path.of("resources/queries/q10_sf1.txt");
        String query = Files.readString(queryString);

        String queryPG = "EXPLAIN (FORMAT JSON) " + query;
        dbConn1 = DriverManager.getConnection("jdbc:postgresql://localhost:8081/impro_database", "impro_user", "impro_password");
        Statement stmt = dbConn1.createStatement();
//    ScriptRunner runner = new ScriptRunner(con, [booleanAutoCommit], [booleanStopOnerror]);
//    runner.runScript(new BufferedReader(new FileReader("test.sql")));
        stmt.execute(queryPG);
        ResultSet resultSet = stmt.getResultSet();
        resultSet.next();
        String retrievedName = resultSet.getString(1);
        System.out.println(retrievedName);

        File file=new File("resources/explain_outputs/Q10_explain_PG.json");
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(retrievedName);
        fileWriter.flush();
        fileWriter.close();

        stmt.close();

        String queryMDB = "EXPLAIN FORMAT=JSON " + query;
        dbConn2 = DriverManager.getConnection("jdbc:mariadb://localhost:8083/impro_database", "impro_user", "impro_password");
        Statement stmt2 = dbConn2.createStatement();
        stmt2.execute(queryMDB);
        ResultSet resultSet2 = stmt2.getResultSet();
        resultSet2.next();
        String retrievedName2 = resultSet2.getString(1);
        System.out.println(retrievedName2);

        File file2=new File("resources/explain_outputs/Q10_explain_MDB.json");
        file2.createNewFile();
        FileWriter fileWriter2 = new FileWriter(file2);
        fileWriter2.write(retrievedName2);
        fileWriter2.flush();
        fileWriter2.close();

        stmt2.close();
    }
}
