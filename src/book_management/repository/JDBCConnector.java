package book_management.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnector {
    private static final String DRIVER_PATH = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522/xe";
    private static final String USER_NAME = "C##aisoft";
    private static final String PASSWORD = "1234";
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER_PATH);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Oracle JDBC 드라이버를 찾을 수 없습니다.", e);
        }

        return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    }

    public static void main(String[] args) throws SQLException {
        try (Connection con = getConnection()) {
            System.out.println("Connection success");
        }
    }
}
