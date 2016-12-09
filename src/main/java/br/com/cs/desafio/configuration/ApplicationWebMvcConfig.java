package br.com.cs.desafio.configuration;

import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


public class ApplicationWebMvcConfig extends WebMvcConfigurerAdapter {
	
	  @Override
	    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	        configurer.enable();
	    }
	
}
