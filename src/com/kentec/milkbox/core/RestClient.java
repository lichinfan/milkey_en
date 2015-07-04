package com.kentec.milkbox.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class RestClient {

	public static String get(String url) throws Exception {		
        URL u = new URL(url);
        URLConnection conn = u.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = br.readLine()) != null) {
        	sb.append(line);
        }
        br.close();
        return sb.toString();        
	}
	
}
