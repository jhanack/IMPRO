import java.sql.*;

public class Test {

    private static Connection dbConn1;

    public static void main(String[] args) throws SQLException{

        dbConn1 = DriverManager.getConnection("jdbc:postgresql://localhost:8080/impro_database", "impro_user", "impro_password");

        Statement stmt = dbConn1.createStatement();
        stmt.execute("EXPLAIN SELECT * FROM \"sf1_orders\" WHERE o_totalprice > 20");
        ResultSet resultSet = stmt.getResultSet();
        resultSet.next();
        String retrievedName = resultSet.getString(1);
        System.out.println(retrievedName);
        resultSet.next();
        String retrievedName2 = resultSet.getString(1);
        System.out.println(retrievedName2);
        resultSet.next();
        String retrievedName3 = resultSet.getString(1);
        System.out.println(retrievedName3);
        stmt.close();
    }
}
