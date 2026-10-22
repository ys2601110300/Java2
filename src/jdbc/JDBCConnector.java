package jdbc;

import java.sql.*;

public class JDBCConnector
{
    private static final String DRIVER_PATH = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1522/xe";
    private static final String USER_NAME = "c##jys";
    private static final String PASSWORD = "1234";

    private static Connection con;

    public static Connection getConnection()
    {
        try
        {
            //  1. JDBC Driver Memory Load
            Class.forName(DRIVER_PATH);
            System.out.println("JDBC Driver Loaded");
            //  2. Connection
            con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println("JDBC Connection Successful");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("JDBC Driver cannot found");
        }
        catch (SQLException e)
        {
            System.out.println("Connection failed");
        }
        return con;
    }

    public static void resultSetTest()
    {
        try
        {
            //  3. SQL문을 실행할 수 있는 문장객체 생성 (미리 준비된 SQL문을 실행할 때 사용)
            String sql = "SELECT * FROM 고객";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                System.out.print(rs.getString("고객아이디") + "\t");
                System.out.print(rs.getString("고객이름") + "\t");
                System.out.print(rs.getString("직업") + "\t");
                System.out.println();
            }
        }
        catch (SQLException e)
        {
            System.out.println("연결된 DB로 문장객체가 생성되지 않았습니다.");
        }
    }

    public static void main(String[] args)
    {
        getConnection();
        resultSetTest();
    }
}
