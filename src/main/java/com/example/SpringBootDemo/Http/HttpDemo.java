package com.example.SpringBootDemo.Http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.core.JsonProcessingException;

public class HttpDemo {
	
	public String httpDemo() throws JsonProcessingException {
		String getUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";
		String rtnHttps = "";
		
		try {
			URL url = new URL(getUrl);
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
			httpsURLConnection.connect();
			
			if (httpsURLConnection.getResponseCode() == 200) {
				InputStream inputStream = httpsURLConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				StringBuffer stringBuffer = new StringBuffer();
				String readLine = "";
				while ((readLine = bufferedReader.readLine()) != null) {
					stringBuffer.append(readLine);
				}
				inputStream.close();
				bufferedReader.close();
				httpsURLConnection.disconnect();
				rtnHttps = stringBuffer.toString();
			}
		} catch (Exception e) {
			// TODO
		}
		return rtnHttps;
	}
	
}
