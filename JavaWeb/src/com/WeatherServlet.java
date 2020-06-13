package com;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class WeatherServlet
 */
public class WeatherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeatherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		PrintWriter out = response.getWriter();
		String location = request.getParameter("LocationString");
		JSONObject ans = null;
		if(!location.matches("^[a-zA-Z0-9 ]*$")) {
			rd = request.getRequestDispatcher("weather.html");
			rd.include(request, response);
			out.println("<p style=\"color:red;text-align:center;\">"+"Please Enter a Valid City"+"</p>");
		}
		else {
			try {
				ans = API1.getLocInfo(location);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			JSONArray check = (JSONArray) ans.get("features");
			if(check.isEmpty()) {
				rd = request.getRequestDispatcher("weather.html");
				rd.include(request, response);
				out.println("<p style=\"color:red;text-align:center;\">"+"Please Enter a Valid City"+"</p>");
			}
			else {
				String city = (String) (((JSONObject) (check.get(0))).get("place_name"));
				String lon = (((JSONArray) (((JSONObject) (check.get(0))).get("center"))).get(0)).toString(); 
				String lat = (((JSONArray) (((JSONObject) (check.get(0))).get("center"))).get(1)).toString();
				Double longitude = Double.parseDouble(lon);
				Double latitude = Double.parseDouble(lat);
				try {
					ans = API2.getWeatherInfo(longitude, latitude);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String summary = (String) (((JSONObject) (((JSONArray) (ans.get("weather"))).get(0))).get("description"));
				String temp = (((JSONObject) (ans.get("main"))).get("temp")).toString();
				String tempMin = (((JSONObject) (ans.get("main"))).get("temp_min")).toString();
				String tempMax = (((JSONObject) (ans.get("main"))).get("temp_max")).toString();
				String pressure = (((JSONObject) (ans.get("main"))).get("pressure")).toString();
				String humidity = (((JSONObject) (ans.get("main"))).get("humidity")).toString();
				String windSpeed = (((JSONObject) (ans.get("wind"))).get("speed")).toString();
				
				out.println("<body style=\"background-color:coral\">");
				out.println("<header style=\"text-align:center;background-color:white;\">\r\n" + 
						"		<a href=\"index.html\">Home</a>\r\n" + 
						"		<a href=\"about.html\">About</a>\r\n" + 
						"		<a href=\"weather.html\">Weather</a>\r\n" + 
						"	</header>");
				out.println("<p style=\"text-align:center;background-color:orange;\">"+city+"</p>");
				out.println("<p style=\"text-align:center;background-color:orange;\">"+"Summary : "+summary+"</p>");
				out.println("<p style=\"text-align:center;background-color:orange;\">"+"Temperature : "+temp+" (Min:"+tempMin+", "+"Max:"+tempMax+")"+"</p>");
				out.println("<p style=\"text-align:center;background-color:orange;\">"+"Pressure : "+pressure+"</p>");
				out.println("<p style=\"text-align:center;background-color:orange;\">"+"Humidity : "+humidity+"</p>");
				out.println("<p style=\"text-align:center;background-color:orange;\">"+"Wind Speed : "+windSpeed+"</p>");
				out.println("</body");
			}
		}
	}

}
