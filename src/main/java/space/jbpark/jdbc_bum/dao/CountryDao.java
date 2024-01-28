package space.jbpark.jdbc_bum.dao;

import static space.jbpark.jdbc_bum.ConnectionManager.closeConnection;
import static space.jbpark.jdbc_bum.ConnectionManager.openConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import space.jbpark.jdbc_bum.model.Country;

public class CountryDao {
	// @formatter:off
	private static final String GET_ALL_COUNTRIES = "select * from country";
	private static final String GET_COUNTRIES_BY_NAME = 
			"select * from country where name like ?";
	// @formatter:on

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
		List<Country> countries = new ArrayList<>();

		try {
			Connection connection = openConnection();
			PreparedStatement statement = connection
					.prepareStatement(GET_ALL_COUNTRIES);
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
}
