package com.abc.ceop.dao;

import org.springframework.context.ApplicationContext;

public interface DataLoaderDao {

	void deleteTables();

	void loadCampaigns();

	void loadCsvFileConfigurations();

	void loadEmails();

	void loadQuestions();

	void loadThresholds(ApplicationContext context);

	void loadLocationSearchConfig();

	void deleteConfigTables();

	void loadFirstProcessConfiguration();

	void loadSecondProcessConfiguration();

	void deleteLocationTables();

	void loadCountries();

}
