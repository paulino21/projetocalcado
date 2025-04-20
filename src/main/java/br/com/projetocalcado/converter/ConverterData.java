package br.com.projetocalcado.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class ConverterData implements Converter {

    private static final DateTimeFormatter formatterComHora = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final DateTimeFormatter formatterSimples = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        LocalDate data = (LocalDate) source;
        writer.setValue(data.toString());
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String text = reader.getValue();

        try {
            OffsetDateTime data = OffsetDateTime.parse(text, formatterComHora);
            return data.toLocalDate();
        } catch (Exception e) {
            return LocalDate.parse(text, formatterSimples);
        }
    }

    @Override
    public boolean canConvert(Class type) {

        return type.equals(LocalDate.class);
    }
}

