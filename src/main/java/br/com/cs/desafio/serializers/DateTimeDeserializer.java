package br.com.cs.desafio.serializers;

import java.io.IOException;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateTimeDeserializer extends JsonDeserializer<DateTime> {

	@Override
	public DateTime deserialize(JsonParser json, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		long time = json.getDecimalValue().longValue();
				
		if ( time > 0 ){
			return new DateTime( time );
		}
		
		return DateTime.now();
	}


}