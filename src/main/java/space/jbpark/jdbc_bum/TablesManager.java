package space.jbpark.jdbc_bum;

import static space.jbpark.jdbc_bum.ConnectionManager.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TablesManager {
  public static void createTable() {
    String sql = "CREATE TABLE COUNTRY"
        + "(ID IDENTITY, NAME VARCHAR(255), CODE_NAME VARCHAR(255));";
    executeStatement(sql);
  }

  public static void dropTable() {
    String sql = "DROP TABLE IF EXISTS COUNTRY;";
    executeStatement(sql);
  }

  private static void executeStatement(String sql) {
    PreparedStatement pStatement;
    
    try {
      Connection connection = openConnection();
      pStatement = connection.prepareStatement(sql);
      pStatement.executeUpdate();
      pStatement.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeConnection();
    }
  }
}
