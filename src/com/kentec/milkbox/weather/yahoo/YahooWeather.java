package com.kentec.milkbox.weather.yahoo;

import java.util.ArrayList;

public class YahooWeather {

	String description;
	String city;
	String region;
	String country;

	String windChill;
	String windDirection;
	String windSpeed;

	String sunrise;
	String sunset;

	String conditiontext;
	String conditiondate;
	String temperature;
	String code;
	ArrayList<Forecast> forecast = new ArrayList<Forecast>();
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
//		this.temperature =String.valueOf( ((Integer.parseInt(temperature)-32) *5 ) /9 );
		this.temperature = temperature;
	}

	public String getConditiontext() {
		return conditiontext;
	}

	public void setConditiontext(String conditiontext) {
		this.conditiontext = conditiontext;
	}


	public String toString() {

		String s;

		s = description + " -\n\n" + "city: " + city + "\n" + "region: "
				+ region + "\n" + "country: " + country + "\n\n" + "Wind\n"
				+ "chill: " + windChill + "\n" + "direction: " + windDirection
				+ "\n" + "speed: " + windSpeed + "\n\n" + "Sunrise: " + sunrise
				+ "\n" + "Sunset: " + sunset + "\n\n" + "Condition: "
				+ conditiontext + "\n" + conditiondate + "\n" + "temperature: "
				+ temperature + "\n";

		for (int i = 0; i < forecast.size(); i++) {
			s += "forecast> day: " + forecast.get(i).day + " date: "
					+ forecast.get(i).date + " low: " + forecast.get(i).low
					+ " high: " + forecast.get(i).high + " text: "
					+ forecast.get(i).text + " code: " + forecast.get(i).code
					+ "\n";
		}

		return s;
	}

}