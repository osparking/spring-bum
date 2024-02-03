package space.jbpark.jdbc_bum.dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import space.jbpark.jdbc_bum.model.Country;

public class CountryDao extends JdbcDaoSupport {
  private static final String GET_ALL_COUNTRIES = "select * from country";
  private static final String GET_COUNTRIES_BY_NAME = "select * from country where name like :name";
  private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

  public List<Country> getCountryByName(String name) {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
        getDataSource());
    SqlParameterSource source = new MapSqlParameterSource("name",
        "%" + name + "%");
    return namedParameterJdbcTemplate.query(GET_COUNTRIES_BY_NAME, source,
        COUNTRY_ROW_MAPPER);
  }

  public List<Country> getCountries() {
    List<Country> countries = getJdbcTemplate().query(GET_ALL_COUNTRIES,
        COUNTRY_ROW_MAPPER);
    return countries;
  }
}
