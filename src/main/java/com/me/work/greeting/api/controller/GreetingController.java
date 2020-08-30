package com.me.work.greeting.api.controller;

import java.time.OffsetDateTime;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.me.work.greeting.api.GreetingsApi;
import com.me.work.greeting.api.Path;
import com.me.work.greeting.api.exception.InvalidInputException;
import com.me.work.greeting.api.exception.NotFoundException;
import com.me.work.greeting.api.mapper.GreetingMapper;
import com.me.work.greeting.api.model.Greeting;
import com.me.work.greeting.api.repository.GreetingRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class GreetingController implements GreetingsApi {

	private final GreetingMapper greetingMapper;
	
	private final GreetingRepository greetingRepository;
	
	@Autowired
	public GreetingController(GreetingMapper greetingMapper, GreetingRepository greetingRepository) {
		
		this.greetingMapper = greetingMapper;
		this.greetingRepository = greetingRepository;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Greeting> findGreetings(@NotNull @Valid String name, @NotNull @Valid String firstname) {
		
		Optional<com.me.work.greeting.api.bo.Greeting> greeting = greetingRepository.findByNameAndFirstname(name, firstname);
		
		if(greeting.isPresent())
			return ResponseEntity.ok(greetingMapper.toModel(greeting.get()));
		
		throw new NotFoundException(String.format("The greeting with name %s and firstname %s doesn't not exist.", name, firstname));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Greeting> getGreeting(String id) {
		
		Optional<com.me.work.greeting.api.bo.Greeting> greeting = greetingRepository.findById(id);
		
		if(greeting.isPresent())
			return ResponseEntity.ok(greetingMapper.toModel(greeting.get()));
		
		throw new NotFoundException(String.format("The greeting with identifier %s doesn't not exist.", id));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Greeting> saveGreeting(@Valid Greeting greeting) {
		
		final String id = greeting.getId();
		
		final String name = greeting.getName();
		final String firstname = greeting.getFirstname();
		final String subject = greeting.getSubject();
		final String message = greeting.getMessage();
		
		if(StringUtils.isEmpty(name) || StringUtils.isEmpty(firstname) || 
				StringUtils.isEmpty(subject) || StringUtils.isEmpty(message)) 
			throw new InvalidInputException("The greeting is incomplete.");
		
		if(StringUtils.isEmpty(id)) {
			
			 Optional<com.me.work.greeting.api.bo.Greeting> greetingOption = greetingRepository.findByNameAndFirstname(name, firstname);
			 
			 if(greetingOption.isPresent()) {
				 
				 com.me.work.greeting.api.bo.Greeting greetingToUpdate = greetingOption.get();
				 
				 //Update.
				 greetingToUpdate.setSubject(greeting.getSubject());
				 greetingToUpdate.setMessage(greeting.getMessage());
				 greetingToUpdate.setVersion(Path.VERSION);
				 
				 com.me.work.greeting.api.bo.Greeting greetingUpdated = greetingRepository.save(greetingToUpdate);
				 
				 if(log.isDebugEnabled())
					 log.debug(" > This greeting has been updated : {1}", greetingUpdated.toString());
				 
				 return ResponseEntity.ok(greetingMapper.toModel(greetingUpdated));
			 }
			 else {
				 
				 //Creation.
				 greeting.setCreationDate(OffsetDateTime.now());
				 greeting.setVersion(Path.VERSION);
				 
				 com.me.work.greeting.api.bo.Greeting greetingCreated = greetingRepository.save(greetingMapper.toBusiness(greeting));
				 
				 if(log.isDebugEnabled())
					 log.debug(" > This greeting has been created : {1}", greetingCreated.toString());
				 
				 return ResponseEntity.status(HttpStatus.CREATED).body(greetingMapper.toModel(greetingCreated));
			 }
		}
		else {
			
			Optional<com.me.work.greeting.api.bo.Greeting> greetingOption = greetingRepository.findById(id);
			
			if(greetingOption.isPresent()) {
				
				com.me.work.greeting.api.bo.Greeting greetingToUpdate = greetingOption.get();
				
				greetingToUpdate.setFirstname(greeting.getFirstname());
				greetingToUpdate.setName(greeting.getName());
				greetingToUpdate.setMessage(greeting.getMessage());
				greetingToUpdate.setSubject(greeting.getSubject());
				greetingToUpdate.setVersion(Path.VERSION);
				
				com.me.work.greeting.api.bo.Greeting greetingUpdated = greetingRepository.save(greetingToUpdate);
				
				if(log.isDebugEnabled())
					log.debug(" > This greeting has been updated : {1}", greetingUpdated.toString());
				
				return ResponseEntity.ok(greetingMapper.toModel(greetingUpdated));
			}
			else {
				
				greeting.setId(null);
				greeting.setCreationDate(OffsetDateTime.now());
				greeting.setVersion(Path.VERSION);
				
				com.me.work.greeting.api.bo.Greeting greetingCreated = greetingRepository.save(greetingMapper.toBusiness(greeting));
				
				if(log.isDebugEnabled())
					log.debug(" > This greeting has been created : {1}", greetingCreated.toString());
				
				return ResponseEntity.status(HttpStatus.CREATED).body(greetingMapper.toModel(greetingCreated));
			}
		}
	}
}
