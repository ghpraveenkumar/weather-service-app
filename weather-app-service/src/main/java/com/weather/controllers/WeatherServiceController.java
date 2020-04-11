package com.weather.controllers;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.web.bind.annotation.RequestParam;
import com.weather.manager.WeatherManager;
import com.weather.model.Weather;

@RestController
@RequestMapping("/api")
public class WeatherServiceController {

	@Autowired
	private WeatherManager manager;
	
	@RequestMapping("weather/{latitude}/{longitude}")
	public ResponseEntity<List<Weather>> getWeather(
			@PathVariable String latitude, @PathVariable String longitude) {
		List<Weather> weatherResponse;
		if (StringUtils.isBlank(latitude) || StringUtils.isBlank(longitude)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Input Request Validation Failed: Required parameters latitide or longitude is missing");
		}
		try {
			weatherResponse = manager.getWeather(latitude, longitude);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error while calling Backend Application:DarkSky", e);
		}
		return new ResponseEntity<>(weatherResponse, HttpStatus.CREATED);
	}	
}
