package com.kentec.milkbox.weather.yahoo;

public class Forecast {
	
	// <yweather:forecast day="Tue" date="8 Apr 2014" low="68" high="78" text="Isolated Thunderstorms" code="37"/>
	String day;
	String date;
	String low;
	String high;
	String text;
	String code;
	
	public Forecast(String day, String date, String low, String high, String text, String code) {
		this.day = day;
		this.date = date;
		this.low = low;
		this.high = high;
		this.text = text;
		this.code = code;
	}
	

}
