package com.weather.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.weather.controllers.WeatherServiceController;
import com.weather.manager.WeatherManager;
import com.weather.model.Weather;


public class WeatherControllerTest {

	@InjectMocks
	public WeatherServiceController controller;
	
	@Mock
	public WeatherManager manager;
	
	@Test
	public void testPositive() throws ParseException {
		
		Mockito.when(manager.getWeather("latitude", "longitude")).thenReturn(new ArrayList<Weather>());
		ResponseEntity<List<Weather>> response = controller.getWeather("latitude", "longitude");
		
		assertEquals(response.getStatusCode(),HttpStatus.ACCEPTED);
		
	}
	
	@Test
	public void testValidation() throws ParseException {
		
		Mockito.when(manager.getWeather("latitude", null)).thenReturn(new ArrayList<Weather>());
		ResponseEntity<List<Weather>> response = controller.getWeather("latitude", "longitude");
		
		assertEquals(response.getStatusCode(),HttpStatus.BAD_REQUEST);
		
	}
}
