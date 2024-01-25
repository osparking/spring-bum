package space.jbpark.jdbc_bum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static space.jbpark.jdbc_bum.ConnectionManager.openConnection;
import static space.jbpark.jdbc_bum.CountriesLoader.COUNTRY_INIT_DATA;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import space.jbpark.jdbc_bum.dao.CountryDao;

class CountriesLoaderTest {
  private static Connection connection;
  private CountryDao dao;

  @BeforeEach
  void openDbConnection() {
    dao = new CountryDao();
    connection = openConnection();
  }

  @Test
  void testLoadingCountries() throws SQLException {
    // 국가 테이블 내용 비우기
    deleteCountries(connection);
    
    // 국가 테이블 채우기
    CountriesLoader loader = new CountriesLoader();
    loader.loadCountries();
    
    // 국가 테이블 행 수 읽기
    int countryCount = dao.getCountries().size();
    
    // 국가 테이블 행수 단언; COUNTRY_INIT_DATA 배열 길이와 동일
    assertEquals(COUNTRY_INIT_DATA.length, countryCount);

    // 국가 테이블 내용 비우기
    deleteCountries(connection);
  }
  
  private void deleteCountries(Connection connection) throws SQLException {
    String deleteSql = "delete from country";
    Statement statement = connection.createStatement();
    statement.execute(deleteSql);
  }
}
