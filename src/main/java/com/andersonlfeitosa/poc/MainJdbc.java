package com.andersonlfeitosa.poc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

import org.apache.log4j.Logger;

public class MainJdbc {
  
  private static final Logger logger = Logger.getLogger(MainJdbc.class);
  
  private static final String URL = "jdbc:h2:tcp://localhost//tmp/poc-jdbc";

  public static void main(String[] args) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    Properties connectionProperties = new Properties();
    connectionProperties.put("user", "sa");
    connectionProperties.put("password", "sa");
    
    try {
      connection = DriverManager.getConnection(URL, connectionProperties);
      
      connection.setAutoCommit(false);
      
      preparedStatement = connection.prepareStatement(createSqlUserInsert());
      preparedStatement.setString(1, UUID.randomUUID().toString());
      preparedStatement.setString(2, Long.toString(UUID.randomUUID().getMostSignificantBits()));
      preparedStatement.execute();
      
      resultSet = connection.prepareStatement(creatSqlUserSelect()).executeQuery();
      while (resultSet.next()) {
        logger.info(resultSet.getLong(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
      }
      
    } catch (SQLException e) {
      logger.error("Error to close resources.", e);
    } finally {
      try {
        closeResources(connection, preparedStatement, resultSet);
      } catch (SQLException e) {
        logger.error("Error to close resources.", e);
      }
    }
  }

  private static void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
    if (resultSet != null && !resultSet.isClosed()) {
      resultSet.close();
    }
    
    if (preparedStatement != null && !preparedStatement.isClosed()) {
      preparedStatement.close();
    }
    
    if (connection != null && !connection.isClosed()) {
      connection.close();
    }
  }
  
  private static String creatSqlUserSelect() {
    return "SELECT * FROM USR.USER";
  }

  private static String createSqlUserInsert() {
    return "INSERT INTO USR.USER (USER_ID, NAME, EMAIL) VALUES (DEFAULT, ?, ?)";
  }

}
