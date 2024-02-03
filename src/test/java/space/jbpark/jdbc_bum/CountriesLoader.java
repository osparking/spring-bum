package space.jbpark.jdbc_bum;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

class CountriesLoader extends JdbcDaoSupport {
  // @formatter:off
  private static final String LOAD_COUNTRIES_SQL = 
      "insert into country (name, code_name) values ";
  public static final String[][] COUNTRY_INIT_DATA = 
    { { "호주", "AU" }, { "대한민국", "KR" }, { "일본", "JP" },
        { "중국", "CN" }, { "미국", "US" }, { "영국", "UK" } };
  // @formatter:on

  public void loadCountries() {
    for (String[] countryData : COUNTRY_INIT_DATA) {
      String sql = LOAD_COUNTRIES_SQL + "('" + countryData[0] + "', '"
          + countryData[1] + "');";
      getJdbcTemplate().execute(sql);
    }
  }
}
