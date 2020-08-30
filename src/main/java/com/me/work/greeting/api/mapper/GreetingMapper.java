package com.me.work.greeting.api.mapper;

import org.mapstruct.Mapper;

import com.me.work.greeting.api.model.Greeting;

@Mapper(uses={DateMapper.class})
public interface GreetingMapper {

	/**
	 * @param greeting
	 * @return {@link Greeting}
	 */
	public Greeting toModel(com.me.work.greeting.api.bo.Greeting greeting);
	
	/**
	 * @param greeting
	 * @return {@link com.me.work.greeting.api.bo.Greeting}
	 */
	public com.me.work.greeting.api.bo.Greeting toBusiness(Greeting greeting);
}
