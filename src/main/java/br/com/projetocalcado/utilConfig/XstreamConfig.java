package br.com.projetocalcado.utilConfig;

import com.thoughtworks.xstream.XStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XstreamConfig {
    @Bean
    public XStream xStream(){

        return new XStream();
    }
}
