package com.me.work.greeting.api.bo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data
@Document(value="greetings")
public class Greeting {

	@Id
	private String id;
	
	@Exclude private String subject;
	@Exclude private String message;
	private String firstname;
	private String name;
	@Exclude private Date creationDate;
	@Exclude private String version;
}
