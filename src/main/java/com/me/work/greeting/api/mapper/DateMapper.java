package com.me.work.greeting.api.mapper;

import java.time.OffsetDateTime;
import java.util.Date;

import com.me.work.greeting.api.converter.date.OffsetDateConverter;

public class DateMapper {

	/**
	 * @param source
	 * @return {@link OffsetDateTime}
	 */
	public OffsetDateTime map(Date source) {
		
		OffsetDateTime out = null;
		
		if(source != null)
			out = OffsetDateConverter.convert(source);
		
		return out;
	}
	
	/**
	 * @param source
	 * @return {@link Date}
	 */
	public Date map(OffsetDateTime source) {
		
		Date out = null;
		
		if(source != null)
			out = OffsetDateConverter.convert(source);
		
		return out;
	}
}