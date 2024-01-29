package space.jbpark.jdbc_bum;

import static space.jbpark.jdbc_bum.CountriesLoader.COUNTRY_INIT_DATA;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import space.jbpark.jdbc_bum.dao.CountryDao;
import space.jbpark.jdbc_bum.model.Country;

class CountriesDatabaseTest {

  private CountryDao dao = new CountryDao();
  private CountriesLoader loader = new CountriesLoader();

  private List<Country> expectedCountryList = new ArrayList<>();
  private List<Country> expectedCountryByName국 = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    TablesManager.createTable();
    initExpectedCountryLists();
    loader.loadCountries();
  }

  private void initExpectedCountryLists() {
    for (int i = 0; i < COUNTRY_INIT_DATA.length; i++) {
      String[] countryInitData = COUNTRY_INIT_DATA[i];
      Country country = new Country(countryInitData[0], countryInitData[1]);
      expectedCountryList.add(country);
      if (country.getName().contains("국")) {
        expectedCountryByName국.add(country);
      }
    }
  }

  @Test
  void testCountryList() {
    List<Country> countries = dao.getCountries();
    assertNotNull(countries);
    assertEquals(expectedCountryList.size(), countries.size());
    expectedCountryList.forEach(exp -> assertTrue(countries.contains(exp)));
  }
  
  @Test
  void testCountryByName국() {
    List<Country> countries = dao.getCountryByName("국");
    assertNotNull(countries);
    assertEquals(expectedCountryByName국.size(), countries.size());
    expectedCountryByName국.forEach(exp -> assertTrue(countries.contains(exp)));
  }
  
  @AfterEach
  public void dropDown() {
    TablesManager.dropTable();
  }
}
