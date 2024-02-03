package space.jbpark.jdbc_bum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import space.jbpark.jdbc_bum.model.Country;

public class CountryDao extends JdbcDaoSupport {
  private static final String GET_ALL_COUNTRIES = "select * from country";
  private static final String GET_COUNTRIES_BY_NAME = "select * from country where name like :name";
  private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

  public List<Country> getCountryByName(String name) {
    List<Country> countries = new ArrayList<>();

    try {
        Connection connection = openConnection();
        PreparedStatement statement = connection
                .prepareStatement(GET_COUNTRIES_BY_NAME);
        StringBuffer searchName = new StringBuffer("%");
        searchName.append(name);
        searchName.append("%");
        statement.setString(1, searchName.toString());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            countries.add(new Country(result.getString("name"),
                    result.getString("code_name")));
        }
        statement.close();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    } finally {
        closeConnection();
    }
    return countries;
}

  public List<Country> getCountries() {
    List<Country> countries = getJdbcTemplate().query(GET_ALL_COUNTRIES,
        COUNTRY_ROW_MAPPER);
    return countries;
  }
}
