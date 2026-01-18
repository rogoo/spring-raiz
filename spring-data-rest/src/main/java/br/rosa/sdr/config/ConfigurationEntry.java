package br.rosa.sdr.config;

import java.util.Calendar;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.rosa.sdr.entity.Person;
import br.rosa.sdr.repository.PerRepository;

@Configuration
public class ConfigurationEntry {

	@Bean
	public ApplicationRunner dataLoad(PerRepository perRepo) {
		Calendar c = Calendar.getInstance();

		return ret -> {
			perRepo.save(new Person("Rod", "Fess", c.getTime()));
			c.add(Calendar.YEAR, -4);
			c.add(Calendar.MONTH, -10);
			perRepo.save(new Person("Dan", "Nadi", c.getTime()));
			c.add(Calendar.YEAR, -1);
			c.add(Calendar.MONTH, -2);
			c.add(Calendar.DAY_OF_MONTH, -17);
			perRepo.save(new Person("Iza", "Evol", c.getTime()));
		};
	}
}
