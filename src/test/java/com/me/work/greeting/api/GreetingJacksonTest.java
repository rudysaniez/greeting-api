package com.me.work.greeting.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.work.greeting.api.bo.Greeting;
import com.me.work.greeting.api.mapper.GreetingMapper;
import com.me.work.greeting.api.repository.GreetingRepository;

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest
public class GreetingJacksonTest {

	@Autowired
	private GreetingRepository greetingRepository;
	
	@Autowired
	private GreetingMapper greetingMapper;
	
	@Autowired
	private ObjectMapper thatGoodOldJack;
	
	@Value("classpath:greeting.json")
	private Resource greeting;
	
	@Before
	public void before() throws IOException {
		
		com.me.work.greeting.api.model.Greeting greetingModel = thatGoodOldJack.readValue(this.greeting.getFile(), 
				com.me.work.greeting.api.model.Greeting.class);
		assertThat(greetingModel).isNotNull();
		
		Greeting greeting = greetingRepository.save(greetingMapper.toBusiness(greetingModel));
		assertThat(greeting.getId()).isNotEmpty();
		assertThat(greeting.getName()).isEqualTo("Sparrow");
		assertThat(greeting.getFirstname()).isEqualTo("Jack");
	}
	
	@Test
	public void save() {
		
		Optional<Greeting> greetingOpt = greetingRepository.findByNameAndFirstname("Sparrow", "Jack");
		assertThat(greetingOpt.get()).isNotNull();
	}
}
