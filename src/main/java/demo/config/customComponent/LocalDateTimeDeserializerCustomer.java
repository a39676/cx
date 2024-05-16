package demo.config.customComponent;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import toolPack.dateTimeHandle.LocalDateTimeHandler;

public class LocalDateTimeDeserializerCustomer extends JsonDeserializer<LocalDateTime> {

	@Override
	public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {

		LocalDateTimeHandler l = new LocalDateTimeHandler();

		try {
			return l.stringToLocalDateTimeUnkonwFormat(p.readValueAsTree().toString());
		} catch (Exception e) {
			return null;
		}
	}

}
