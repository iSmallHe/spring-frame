package database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.MyThreadLocal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseManager.class);

    private static final String DRIVER="";
    private static final String URL="";
    private static final String USERNAME="";
    private static final String PASSWORD="";

    public final static MyThreadLocal<Connection> container = new MyThreadLocal<Connection>();

    static{
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("数据库驱动启动失败",e);
        }
    }

    public static Connection getConnection(){
        Connection connection = container.get();
        if(connection == null){
            try {
                connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
                container.set(connection);
            } catch (SQLException e) {
                LOGGER.error("数据库获取连接失败",e);
            }
        }
        return connection;
    }

    public static void closeConnection(){
        Connection connection = container.get();
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("数据库连接关闭失败",e);
            } finally {
                container.remove();
            }
        }
    }
}
