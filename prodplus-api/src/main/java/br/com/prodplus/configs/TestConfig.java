package br.com.prodplus.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.prodplus.services.DBService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Configuration
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() {
		this.dbService.initDatabase();
		return true;
	}

}
