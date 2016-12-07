package br.com.cs.desafio.configuration;

import javax.servlet.Filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration
public class RestInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	 @Override
	    protected Class<?>[] getRootConfigClasses() {
	        return new Class[] { RestConfiguration.class };
	    }
	   
	    @Override
	    protected Class<?>[] getServletConfigClasses() {
	        return null;
	    }
	   
	    @Override
	    protected String[] getServletMappings() {
	        return new String[] { "/" };
	    }
	    
	    @Override
	    protected Filter[] getServletFilters() {
	        Filter [] singleton = { new CORSFilter()};
	        return singleton;
	    }

}
