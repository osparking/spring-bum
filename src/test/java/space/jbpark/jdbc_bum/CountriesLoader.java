package space.jbpark.jdbc_bum;

import static space.jbpark.jdbc_bum.ConnectionManager.closeConnection;
import static space.jbpark.jdbc_bum.ConnectionManager.openConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class CountriesLoader {
  // @formatter:off
  private static final String LOAD_COUNTRIES_SQL = 
      "insert into country (name, code_name) values (?, ?)";
  public static final String[][] COUNTRY_INIT_DATA = 
    { { "호주", "AU" }, { "대한민국", "KR" }, { "일본", "JP" }, { "중국", "CN" }, 
        { "미국", "US" }, { "영국", "UK" } };
  // @formatter:on

  public void loadCountries() {
    try {
      Connection connection = openConnection();
      for (String[] countryData : COUNTRY_INIT_DATA) {
        PreparedStatement statement = connection
            .prepareStatement(LOAD_COUNTRIES_SQL);
        statement.setString(1, countryData[0]);
        statement.setString(2, countryData[1]);
        statement.executeUpdate();
        statement.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeConnection();
    }
  }
}
