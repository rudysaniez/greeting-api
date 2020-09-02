package com.me.work.greeting.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.me.work.greeting.api.bo.Greeting;
import com.me.work.greeting.api.repository.GreetingRepository;

@DirtiesContext
@RunWith(SpringRunner.class)
@DataMongoTest
public class GreetingRepositoryTest {

	@Autowired
	private GreetingRepository greetingRepository;
	
	@Before
	public void before() {
		
		Greeting greeting = new Greeting(null, "How are you Luke ?", "This message is for you Luke, you're my only hope!", 
				"Leia", "Organa", new Date(), "1");
		
		greeting = greetingRepository.save(greeting);
		assertThat(greeting).isNotNull();
	}
	
	@Test
	public void test() {
		
		Optional<Greeting> greetingOpt = greetingRepository.findByNameAndFirstname("Organa", "Leia");
		assertThat(greetingOpt.isPresent()).isEqualTo(true);
	}
}
