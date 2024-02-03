package space.jbpark.jdbc_bum;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static space.jbpark.jdbc_bum.CountriesLoader.COUNTRY_INIT_DATA;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import space.jbpark.jdbc_bum.dao.CountryDao;
import space.jbpark.jdbc_bum.model.Country;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:application-context.xml")
class CountriesDatabaseTest {

  @Autowired
  private CountryDao dao;
  
  @Autowired
  private CountriesLoader loader;

  private List<Country> expectedCountryList = new ArrayList<>();
  private List<Country> expectedCountryByName국 = new ArrayList<>();

  @BeforeEach
  public void setUp() {
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

  @DirtiesContext
  @Test
  void testCountryList() {
    List<Country> countries = dao.getCountries();
    assertNotNull(countries);
    assertEquals(expectedCountryList.size(), countries.size());
    expectedCountryList.forEach(exp -> assertTrue(countries.contains(exp)));
  }
  
  @DirtiesContext
  @Test
  void testCountryByName국() {
    List<Country> countries = dao.getCountryByName("국");
    assertNotNull(countries);
    assertEquals(expectedCountryByName국.size(), countries.size());
    expectedCountryByName국.forEach(exp -> assertTrue(countries.contains(exp)));
  }
}
