package com.ry.demo.elevatormgr;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ry.demo.elevatormgr.dto.BasicElevatorDto;
import com.ry.demo.elevatormgr.model.Elevator;
 
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.ry.demo.elevatormgr")
public class AppConfig implements WebMvcConfigurer {
	
	@Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
        return messageSource;
    }
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		// UpdateElevatorDto -> Elevator
		modelMapper.createTypeMap(BasicElevatorDto.class, Elevator.class).addMappings(
		        mapper -> mapper.skip(Elevator::setAvailableFloors));
	    return modelMapper;
	}
}
