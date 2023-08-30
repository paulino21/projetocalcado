package br.com.projetocalcado.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ConverterInt implements Converter {

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        int value = (int) source;
        writer.setValue(String.valueOf(value));
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        double doubQde = Double.parseDouble(reader.getValue());
        int qde = (int) doubQde;
        return qde ;
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(int.class) || type.equals(Integer.class);
    }
}
