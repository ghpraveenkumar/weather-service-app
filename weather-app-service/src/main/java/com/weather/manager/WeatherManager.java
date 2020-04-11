package com.weather.manager;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.weather.model.Weather;

@Component
public class WeatherManager {

 private static ObjectMapper mapper = new ObjectMapper();	
 private static Gson gson;	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${endpoint}")
	String endpoint;
	
	static {
		mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
		gson = gsonBuilder.create();
		
	}
	
	public List<Weather> getWeather(String latitude, String longitude) {
		List<Weather> weather = new ArrayList<Weather>();
		String latitudeLongitude = latitude + "," + longitude;
		String url = String.format(endpoint, latitudeLongitude);
		ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
		Object resBody = responseEntity.getBody();
		
		if(null!=resBody) {
			String jsonStr = gson.toJson(resBody);
			if(null!= jsonStr) {
				JSONObject root = new JSONObject(jsonStr);
				JSONObject daily = root.getJSONObject("daily");
				JSONArray data = daily.getJSONArray("data");
				JSONObject first = (JSONObject) data.get(0);
			
				Weather weatherObj = new Weather();
				String epochTimedate = first.getString("time");
				long unix_seconds = Long.parseLong(epochTimedate);
				Date date = new Date(unix_seconds * 1000 );
				SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String java_date = jdf.format(date);
				
				weatherObj.setDate(java_date);
				weatherObj.setSunriseTime(first.getString("sunriseTime"));
				weatherObj.setSunsetTime(first.getString("sunsetTime"));
				weatherObj.setTemperatureHigh(first.getString("temperatureHigh"));
				weatherObj.setTemperatureHighTime(first.getString("temperatureHighTime"));
				weatherObj.setTemperatureLow(first.getString("temperatureLow"));
				weatherObj.setTemperatureLowTime(first.getString("temperatureLowTime"));
				weather.add(weatherObj);
				
				LocalDateTime previosYear = LocalDateTime.ofInstant(Instant.ofEpochMilli(unix_seconds), ZoneId.of("UTC")).minusYears(1);
			}
			
		}
		
		
		return weather;
	}

}
