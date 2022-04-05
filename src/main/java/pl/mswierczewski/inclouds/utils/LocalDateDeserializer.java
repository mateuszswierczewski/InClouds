package pl.mswierczewski.inclouds.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import pl.mswierczewski.inclouds.exceptions.api.DeserializationException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LocalDateDeserializer() {
        this(null);
    }

    protected LocalDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        LocalDate date;

        try {
            date = LocalDate.parse(node.asText(), dateFormatter);
        } catch (DateTimeParseException e) {
            throw DeserializationException.WRONG_DATE_FORMAT;
        }

        return date;
    }
}
