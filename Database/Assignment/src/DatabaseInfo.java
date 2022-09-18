import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseInfo {
    //Thông tin về database, có thể thay đổi các String theo config của bản thân để test chương trình
    private static String dbPrefix = "jdbc:sqlserver://LAPTOP-EEDKQTHM";
    private static String dbPort = "1433";
    private static String user = "Temp";
    private static String pass = "123";
    private static String databaseName = "Assignment";

    //hàm getConnection để kết nối với database
    public Connection getConnection(){
        Connection conn = null;
        String dbURL = dbPrefix + ":" + dbPort + ";" + "databaseName=" + databaseName + ";trustServerCertificate=true;" ;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
            conn = DriverManager.getConnection(dbURL, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
