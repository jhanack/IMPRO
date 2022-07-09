import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class Write_JSON {

    private static Connection dbConn1;
    private static Connection dbConn2;

    public static void main(String[] args) throws SQLException, IOException {

        dbConn1 = DriverManager.getConnection("jdbc:postgresql://localhost:8080/impro_database", "impro_user", "impro_password");
        Statement stmt = dbConn1.createStatement();
//    ScriptRunner runner = new ScriptRunner(con, [booleanAutoCommit], [booleanStopOnerror]);
//    runner.runScript(new BufferedReader(new FileReader("test.sql")));
        stmt.execute("EXPLAIN (FORMAT JSON) select l.l_orderkey,\n" +
                "    sum( l.l_extendedprice * (1 - l.l_discount )) as revenue,\n" +
                "    o.o_orderdate,\n" +
                "    o.o_shippriority\n" +
                "from sf1_customer as c,\n" +
                "    sf1_orders as o,\n" +
                "    sf1_lineitem as l\n" +
                "where c_custkey = o_custkey\n" +
                "    and l.l_orderkey = o.o_orderkey\n" +
                "    and c.c_mktsegment = 'BUILDING'\n" +
                "    and o.o_orderdate < date '1995-03-15'\n" +
                "    and l.l_shipdate > date '1995-03-15'\n" +
                "group by l_orderkey,\n" +
                "    o_orderdate,\n" +
                "    o_shippriority\n" +
                "order by revenue desc,\n" +
                "    o_orderdate\n" +
                "limit 10;");
        ResultSet resultSet = stmt.getResultSet();
        resultSet.next();
        String retrievedName = resultSet.getString(1);
        System.out.println(retrievedName);

        File file=new File("Q3_explain_PG.json");
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(retrievedName);
        fileWriter.flush();
        fileWriter.close();

        stmt.close();


        dbConn2 = DriverManager.getConnection("jdbc:mariadb://localhost:8083/impro_database", "impro_user", "impro_password");
        Statement stmt2 = dbConn2.createStatement();
        stmt2.execute("EXPLAIN FORMAT=JSON select l.l_orderkey,\n" +
                "    sum( l.l_extendedprice * (1 - l.l_discount )) as revenue,\n" +
                "    o.o_orderdate,\n" +
                "    o.o_shippriority\n" +
                "from sf1_customer as c,\n" +
                "    sf1_orders as o,\n" +
                "    sf1_lineitem as l\n" +
                "where c_custkey = o_custkey\n" +
                "    and l.l_orderkey = o.o_orderkey\n" +
                "    and c.c_mktsegment = 'BUILDING'\n" +
                "    and o.o_orderdate < date '1995-03-15'\n" +
                "    and l.l_shipdate > date '1995-03-15'\n" +
                "group by l_orderkey,\n" +
                "    o_orderdate,\n" +
                "    o_shippriority\n" +
                "order by revenue desc,\n" +
                "    o_orderdate\n" +
                "limit 10;");
        ResultSet resultSet2 = stmt2.getResultSet();
        resultSet2.next();
        String retrievedName2 = resultSet2.getString(1);
        System.out.println(retrievedName2);

        File file2=new File("Q3_explain_MDB.json");
        file2.createNewFile();
        FileWriter fileWriter2 = new FileWriter(file2);
        fileWriter2.write(retrievedName2);
        fileWriter2.flush();
        fileWriter2.close();

        stmt2.close();
    }
}
