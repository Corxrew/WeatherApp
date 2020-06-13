package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class API2 {
	private static final String apiKey = 
			 "54723e5517b86b251a3420d02862b36c";
	 private static final String baseUrl=
			 "https://api.openweathermap.org/data/2.5/weather";
	 
	 public static JSONObject getWeatherInfo(Double longitude, Double latitude) throws ParseException 
	    {
		 	JSONParser parser = new JSONParser(); 
	        String jsonResult=getWeatherJsonString(longitude, latitude);
	        JSONObject json = (JSONObject) parser.parse(jsonResult);
	        return json;
	        
	    }
	 
	 private static String getWeatherJsonString(Double longitude, Double latitude) throws RuntimeException
	 {  
		 String lon = Double.toString(longitude);
		 String lat = Double.toString(latitude);
		 String lonAppend = "?lon="+lon;
		 String latAppend = "&lat="+lat;
		 String apiKeyAppend = "&appid="+apiKey;
		 String Url = baseUrl+lonAppend+latAppend+apiKeyAppend;
		 StringBuilder strBuf = new StringBuilder(); 
     
		 HttpURLConnection conn=null;
		 BufferedReader reader=null;
		 try{  
			 URL url = new URL(Url);  
			 conn = (HttpURLConnection)url.openConnection();  
			 conn.setRequestMethod("GET");
         
			 if (conn.getResponseCode() != 200) {
				 throw new RuntimeException("HTTP GET Request Failed with Error code : "
						 + conn.getResponseCode());
			 } 
        
			 reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
			 String output = null;  
			 while ((output = reader.readLine()) != null)  
				 strBuf.append(output);  
		 }catch(MalformedURLException e) {  
			 e.printStackTrace();   
		 }catch(IOException e){  
			 e.printStackTrace();   
		 }
		 finally
		 {
			 if(reader!=null)
			 {
				 try {
					 reader.close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
			 if(conn!=null)
			 {
				 conn.disconnect();
			 }
		 }
		 return strBuf.toString();  
	 }

}
