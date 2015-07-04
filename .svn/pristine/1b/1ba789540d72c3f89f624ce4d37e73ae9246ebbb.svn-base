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

public class LoadPlaceWOEIDTask extends AsyncTask<Void, Void, Integer>
		implements AsyncTaskCode {

	// Example for "New York"
	// http://query.yahooapis.com/v1/public/yql?q=select*from geo.places where
	// text="New York"&format=xml
	final String yahooPlaceApisBase = "http://query.yahooapis.com/v1/public/yql?q=select*from%20geo.places%20where%20text=";
	final String yahooapisFormat = "&format=xml";
	final String yahooAPIKey="&appid=foOF4CzV34EFIIW4gz1lx0Ze1em._w1An3QyivRalpXCK9sIXT5de810JWold3ApkdMdCrc-#sthash.JIsGsnM3.dpuf";
	String yahooPlaceAPIsQuery;
	
	String place;
	private String msg = "";
	ArrayList<String> l;
	

	private OnLoadPlaceWOEIDListener listener;
	public interface OnLoadPlaceWOEIDListener {
		public void OK(ArrayList<String> l);

		public void Error(String msg);
	}

	public LoadPlaceWOEIDTask(String place, OnLoadPlaceWOEIDListener listener) {
		
		this.place = place;
		this.listener = listener;
		this.execute();

	}

	@Override
	protected Integer doInBackground(Void... params) {
		l = QueryYahooPlaceAPIs();
		if(l.size()>0) {
			
			return OK;
		}else {
			return ERROR;
		}

	}

	@Override
	protected void onPostExecute(Integer result) {
		System.out.println("onPostExecute result: "+result);
//		if (listener != null) {
//			if (result == OK) {
//				listener.OK(weatherResult);
//			} else if (result == ERROR) {
//				listener.Error("WOEID Error");
//			}
//		}
		if (result == OK) {
			listener.OK(l);
		} else if (result == ERROR) {
			listener.Error("WOEID Error");
		}
		
	}
	
	
	private ArrayList<String> QueryYahooPlaceAPIs() {
		String uriPlace = Uri.encode(place);

		yahooPlaceAPIsQuery = yahooPlaceApisBase + "%22" + uriPlace + "%22"
				+ yahooapisFormat + "%22" + yahooAPIKey ;

		String woeidString = QueryYahooWeather(yahooPlaceAPIsQuery);
		Document woeidDoc = convertStringToDocument(woeidString);
		return parseWOEID(woeidDoc);
	}

	private ArrayList<String> parseWOEID(Document srcDoc) {
		ArrayList<String> listWOEID = new ArrayList<String>();

		NodeList nodeListDescription = srcDoc.getElementsByTagName("woeid");
		if (nodeListDescription.getLength() >= 0) {
			for (int i = 0; i < nodeListDescription.getLength(); i++) {
				listWOEID.add(nodeListDescription.item(i).getTextContent());
			}
		} else {
			listWOEID.clear();
		}

		return listWOEID;
	}

	private Document convertStringToDocument(String src) {
		Document dest = null;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
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

	private String QueryYahooWeather(String queryString) {
		String qResult = "";

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
			;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return qResult;
	}
	

}
