package com.kentec.milkbox.weather.yahoo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.kentec.milkbox.comm.AsyncTaskCode;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;

public class LoadWeatherConditionTask extends AsyncTask<Void, Void, Integer>
		implements AsyncTaskCode {

	String woeid;
	String weatherResult;
	String weatherString;
	YahooWeather yahooWeatherResult;
	

	private OnLoadWeatherConditionListener listener;
	public interface OnLoadWeatherConditionListener {
		public void OK(YahooWeather yahooWeatherResult);

		public void Error(String msg);
	}

	public LoadWeatherConditionTask(String woeid, OnLoadWeatherConditionListener listener) {
		
		this.woeid = woeid;
		this.listener = listener;
		this.execute();

	}

	@Override
	protected Integer doInBackground(Void... params) {
		weatherString = QueryYahooWeather();
		Document weatherDoc = convertStringToDocument(weatherString);

		if (weatherDoc != null) {
			yahooWeatherResult = parseWeather(weatherDoc);
//			weatherResult = parseWeather(weatherDoc).toString();
			return OK;
		} else {
			weatherResult = "Cannot convertStringToDocument!";
			return ERROR;
		}

	}

	@Override
	protected void onPostExecute(Integer result) {
		
		if (listener != null) {
			if (result == OK) {
				listener.OK(yahooWeatherResult);
			} else if (result == ERROR) {
				listener.Error("WOEID Error");
			}
		}
		
	}
	
	
	
	
	
	private String QueryYahooWeather() {
		String qResult = "";
		String queryString = "http://weather.yahooapis.com/forecastrss?u=c&w="
				+ woeid;

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(queryString);

		try {
			HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();

			if (httpEntity != null) {
				InputStream inputStream = httpEntity.getContent();
				Reader in = new InputStreamReader(inputStream);
				BufferedReader bufferedreader = new BufferedReader(in);
				StringBuilder stringBuilder = new StringBuilder();

				String stringReadLine = null;

				while ((stringReadLine = bufferedreader.readLine()) != null) {
					stringBuilder.append(stringReadLine + "\n");
				}

				qResult = stringBuilder.toString();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return qResult;
	}

	private Document convertStringToDocument(String src) {
		Document dest = null;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder parser;

		try {
			parser = dbFactory.newDocumentBuilder();
			dest = parser.parse(new ByteArrayInputStream(src.getBytes()));
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dest;
	}

	private YahooWeather parseWeather(Document srcDoc) {

		YahooWeather yahooWeather = new YahooWeather();

		// <description>Yahoo! Weather for New York, NY</description>
		NodeList descNodelist = srcDoc.getElementsByTagName("description");
		if (descNodelist != null && descNodelist.getLength() > 0) {
			yahooWeather.description = descNodelist.item(0).getTextContent();
		} else {
			yahooWeather.description = "EMPTY";
		}

		// <yweather:location city="New York" region="NY"
		// country="United States"/>
		NodeList locationNodeList = srcDoc
				.getElementsByTagName("yweather:location");
		if (locationNodeList != null && locationNodeList.getLength() > 0) {
			Node locationNode = locationNodeList.item(0);
			NamedNodeMap locNamedNodeMap = locationNode.getAttributes();

			yahooWeather.city = locNamedNodeMap.getNamedItem("city")
					.getNodeValue().toString();
			yahooWeather.region = locNamedNodeMap.getNamedItem("region")
					.getNodeValue().toString();
			yahooWeather.country = locNamedNodeMap.getNamedItem("country")
					.getNodeValue().toString();
		} else {
			yahooWeather.city = "EMPTY";
			yahooWeather.region = "EMPTY";
			yahooWeather.country = "EMPTY";
		}

		// <yweather:wind chill="60" direction="0" speed="0"/>
		NodeList windNodeList = srcDoc
				.getElementsByTagName("yweather:wind");
		if (windNodeList != null && windNodeList.getLength() > 0) {
			Node windNode = windNodeList.item(0);
			NamedNodeMap windNamedNodeMap = windNode.getAttributes();

			yahooWeather.windChill = windNamedNodeMap.getNamedItem("chill")
					.getNodeValue().toString();
			yahooWeather.windDirection = windNamedNodeMap
					.getNamedItem("direction").getNodeValue().toString();
			yahooWeather.windSpeed = windNamedNodeMap.getNamedItem("speed")
					.getNodeValue().toString();
		} else {
			yahooWeather.windChill = "EMPTY";
			yahooWeather.windDirection = "EMPTY";
			yahooWeather.windSpeed = "EMPTY";
		}

		// <yweather:astronomy sunrise="6:52 am" sunset="7:10 pm"/>
		NodeList astNodeList = srcDoc
				.getElementsByTagName("yweather:astronomy");
		if (astNodeList != null && astNodeList.getLength() > 0) {
			Node astNode = astNodeList.item(0);
			NamedNodeMap astNamedNodeMap = astNode.getAttributes();

			yahooWeather.sunrise = astNamedNodeMap.getNamedItem("sunrise")
					.getNodeValue().toString();
			yahooWeather.sunset = astNamedNodeMap.getNamedItem("sunset")
					.getNodeValue().toString();
		} else {
			yahooWeather.sunrise = "EMPTY";
			yahooWeather.sunset = "EMPTY";
		}

		// <yweather:condition text="Fair" code="33" temp="60"
		// date="Fri, 23 Mar 2012 8:49 pm EDT"/>
		NodeList conditionNodeList = srcDoc
				.getElementsByTagName("yweather:condition");
		if (conditionNodeList != null && conditionNodeList.getLength() > 0) {
			Node conditionNode = conditionNodeList.item(0);
			NamedNodeMap conditionNamedNodeMap = conditionNode
					.getAttributes();

			yahooWeather.conditiontext = conditionNamedNodeMap
					.getNamedItem("text").getNodeValue().toString();
			yahooWeather.temperature = conditionNamedNodeMap
					.getNamedItem("temp").getNodeValue().toString(); 
			yahooWeather.code = conditionNamedNodeMap
					.getNamedItem("code").getNodeValue().toString();
			yahooWeather.conditiondate = conditionNamedNodeMap
					.getNamedItem("date").getNodeValue().toString();
		} else {
			yahooWeather.conditiontext = "EMPTY";
			yahooWeather.conditiondate = "EMPTY";
		}
		
		// <yweather:forecast day="Tue" date="8 Apr 2014" low="68" high="78" text="Isolated Thunderstorms" code="37"/>
		// <yweather:forecast day="Wed" date="9 Apr 2014" low="67" high="78" text="Isolated Thunderstorms" code="37"/>
		// .....
		NodeList forecastNodeList = srcDoc
				.getElementsByTagName("yweather:forecast");
		if (forecastNodeList != null && forecastNodeList.getLength() > 0) {
			for(int i=0; i<forecastNodeList.getLength(); i++) {
				Node forecastNode = forecastNodeList.item(i);
				NamedNodeMap forecastNamedNodeMap = forecastNode
						.getAttributes();
				Forecast forecast = new Forecast(
						forecastNamedNodeMap.getNamedItem("day").getNodeValue().toString(),
						forecastNamedNodeMap.getNamedItem("date").getNodeValue().toString(), 
						forecastNamedNodeMap.getNamedItem("low").getNodeValue().toString(),
						forecastNamedNodeMap.getNamedItem("high").getNodeValue().toString(),
						forecastNamedNodeMap.getNamedItem("text").getNodeValue().toString(),
						forecastNamedNodeMap.getNamedItem("code").getNodeValue().toString()
						);
				
				yahooWeather.forecast.add(forecast);
				
			}
		} else {
			yahooWeather.forecast = null;
		}
		

		return yahooWeather;
	}

}
