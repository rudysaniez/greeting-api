package com.me.work.greeting.api.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.me.work.greeting.api.bo.Greeting;

public interface GreetingRepository extends MongoRepository<Greeting, String> {

	/**
	 * @param name
	 * @param firstname
	 * @return page of {@link Greeting}
	 */
	public Optional<Greeting> findByNameAndFirstname(String name, String firstname);
}
