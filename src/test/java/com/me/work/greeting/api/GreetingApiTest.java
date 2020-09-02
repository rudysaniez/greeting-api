package com.me.work.greeting.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.work.greeting.api.model.Greeting;

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class GreetingApiTest {

	@Autowired
	private TestRestTemplate client;
	
	@LocalServerPort
	private int port;
	
	@Value("${server.servlet.context-path}")
	private String path;
	
	@Value("classpath:greeting.json")
	private Resource greeting;
	
	@Autowired
	private ObjectMapper thatGoodOldJack;
	
	private static final StringBuilder GREETING_URL = new StringBuilder("http://localhost:");
	
	@Before
	public void before() throws IOException {
		
		GREETING_URL.append(port).append(path).append("/greetings");
		
		ResponseEntity<Greeting> response = client.postForEntity(GREETING_URL.toString(), 
				thatGoodOldJack.readValue(greeting.getFile(), Greeting.class), Greeting.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	public void findByNameAndFirstName() {
		
		ResponseEntity<Greeting> response = client.getForEntity(GREETING_URL.append("?firstname=").append("Jack").
				append("&name=").append("Sparrow").toString(), Greeting.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
