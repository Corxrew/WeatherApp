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

public class API1 {
	private static final String apiKey = 
			 "pk.eyJ1IjoiY29yeHJldyIsImEiOiJja2I2b25iODYwMG5nMnJvNjZnNDF2N2ZmIn0.xDFPYsQWOl5dq9RsZ7PKiA";
	 private static final String baseUrl=
			 "https://api.mapbox.com/geocoding/v5/mapbox.places/";
	 
	 public static JSONObject getLocInfo(String city) throws ParseException 
	    {
		 	JSONParser parser = new JSONParser();
	        String jsonResult=getLocJsonString(city);
	        JSONObject json = (JSONObject) parser.parse(jsonResult);
	        return json;
	        
	    }
	 
	 private static String getLocJsonString(String city) throws RuntimeException
	 {  
		 String cityAppend = city+".json";
		 cityAppend = cityAppend.replace(' ', '+');
		 String tokenAppend = "?access_token="+apiKey;
		 String limitAppend = "&limit=1";
		 String Url = baseUrl+cityAppend+tokenAppend+limitAppend;
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
