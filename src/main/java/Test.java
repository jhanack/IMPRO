import java.sql.*;

public class Test {

    private static Connection dbConn1;
    private static Connection dbConn2;

    public static void main(String[] args) throws SQLException{

        dbConn1 = DriverManager.getConnection("jdbc:postgresql://localhost:8080/impro_database", "impro_user", "impro_password");

        Statement stmt = dbConn1.createStatement();
        stmt.execute("EXPLAIN (FORMAT JSON) SELECT * FROM \"sf1_orders\" WHERE o_totalprice > 20");
        ResultSet resultSet = stmt.getResultSet();
        resultSet.next();
        String retrievedName = resultSet.getString(1);
        System.out.println(retrievedName);
        stmt.close();

        dbConn2 = DriverManager.getConnection("jdbc:mariadb://localhost:8083/impro_database", "impro_user", "impro_password");
        Statement stmt2 = dbConn2.createStatement();
        stmt2.execute("EXPLAIN FORMAT=JSON SELECT * FROM sf1_orders WHERE o_totalprice > 20");
        ResultSet resultSet2 = stmt2.getResultSet();
        resultSet2.next();
        String retrievedName2 = resultSet2.getString(1);
        System.out.println(retrievedName2);
        stmt.close();
    }
}
