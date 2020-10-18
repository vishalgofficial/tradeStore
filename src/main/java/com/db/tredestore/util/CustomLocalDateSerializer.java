package com.db.tredestore.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateSerializer extends StdSerializer<LocalDate> {
    private static final String PATTERN = "yyyy-MM-dd";
    private static DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern(PATTERN);

    public CustomLocalDateSerializer() {
        this(null);
    }

    public CustomLocalDateSerializer(Class<LocalDate> t) {
        super(t);
    }

    @Override
    public void serialize(
            LocalDate value,
            JsonGenerator gen,
            SerializerProvider arg2)
            throws IOException {

        gen.writeString(formatter.format(value));
    }
}
