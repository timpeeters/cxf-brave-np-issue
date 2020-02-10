package com.github.timpeeters;

import brave.Tracing;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.tracing.brave.BraveClientFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    JaxWsPortProxyFactoryBean calculatorService(Tracing tracing) {
        JaxWsPortProxyFactoryBean bean = new JaxWsPortProxyFactoryBean();
        bean.setServiceInterface(CalculatorSoap.class);
        bean.setServiceName("Calculator");
        bean.setEndpointAddress("http://localhost:9999");
        bean.setPortFeatures(new LoggingFeature(), new BraveClientFeature(tracing));

        return bean;
    }
}
