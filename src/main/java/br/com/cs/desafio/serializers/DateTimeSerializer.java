package br.com.cs.desafio.serializers;

import java.io.IOException;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateTimeSerializer extends JsonSerializer<DateTime> {

//    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public void serialize(DateTime value, JsonGenerator gen, SerializerProvider arg2)
        throws IOException, JsonProcessingException {
    	
    	if ( value != null ){
    		gen.writeNumber( value.toDate().getTime() );
    	}else {
    		gen.writeNumber( 0L );	
    	}
    }
}