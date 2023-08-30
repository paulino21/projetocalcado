package br.com.projetocalcado.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConverterData implements Converter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        LocalDateTime dateTime = (LocalDateTime) source;
        writer.setValue(formatter.format(dateTime));
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String text = reader.getValue().replace("-03:00", "");
        return LocalDateTime.parse(text, formatter);
    }

    @Override
    public boolean canConvert(Class type) {

        return type.equals(LocalDateTime.class);
    }
}
