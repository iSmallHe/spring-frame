package helper;

import database.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionHelper.class);

    public static void startTransaction(){
        Connection connection = DatabaseManager.getConnection();
        if(connection != null){
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("数据库连接设置非自动提交失败",e);
                throw new RuntimeException(e);
            }
        }
    }

    public static void commitTransaction(){
        Connection connection = DatabaseManager.getConnection();
        if(connection != null){
            try {
                connection.commit();
            } catch (SQLException e) {
                LOGGER.error("数据库连接提交事务失败",e);
                throw new RuntimeException(e);
            } finally{
                DatabaseManager.closeConnection();
            }
        }
    }

    public static void rollbackTransaction(){
        Connection connection = DatabaseManager.getConnection();
        if(connection != null){
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.error("数据库连接事务回滚失败",e);
                throw new RuntimeException(e);
            } finally{
                DatabaseManager.closeConnection();
            }
        }
    }
}
