package boun.group9.webservice.app.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;  
import org.json.simple.JSONValue;  
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import boun.group9.webservice.app.Application;
import boun.group9.webservice.app.data.Artists;
import boun.group9.webservice.app.data.ConcertTags;
import boun.group9.webservice.app.data.Concerts;
import boun.group9.webservice.app.data.Locations;
import boun.group9.webservice.app.data.SemanticTags;
import boun.group9.webservice.app.data.Users;
import boun.group9.webservice.exception.IJsonSyntaxException;
import boun.group9.webservice.exception.ISQLException;
import boun.group9.webservice.exception.InternalServerException;
import boun.group9.webservice.exception.NotSavedException;
import boun.group9.webservice.helper.Database;
import boun.group9.webservice.helper.SemanticTagsChecker;
import boun.group9.webservice.helper.UserChecker;


@RestController
public class SemanticTagsController {
	@RequestMapping(value="new-semantictag",method=RequestMethod.POST)
	public String newsemanticTag(@RequestBody String body) {
		SemanticTags tag;
		String query;
		ResultSet rs;
		try {
			tag = Application.gson.fromJson(body, SemanticTags.class);
			query = SemanticTagsChecker.insertSemanticTagsQuery(tag);
			System.out.println(query);
			rs = Database.connect(query,Application.MODE_UPDATE);
			return "OK.";
		}catch(JsonSyntaxException ex) {
			ex.printStackTrace();
			throw new IJsonSyntaxException();
		}catch(SQLException ex) {
			ex.printStackTrace();
			throw new ISQLException();
		}catch(NotSavedException ex) {
			ex.printStackTrace();
			throw new InternalServerException();
		}
	}
	@RequestMapping(value="new-concerttag",method=RequestMethod.POST)
	public String newConcertTag(@RequestBody String body) {
		ConcertTags ctag;
		String query;
		ResultSet rs;
		try {
			ctag = Application.gson.fromJson(body, ConcertTags.class);
			query = SemanticTagsChecker.insertConcertTagsQuery(ctag);
			System.out.println(query);
			rs = Database.connect(query,Application.MODE_UPDATE);
			return "OK.";
		}catch(JsonSyntaxException ex) {
			ex.printStackTrace();
			throw new IJsonSyntaxException();
		}catch(SQLException ex) {
			ex.printStackTrace();
			throw new ISQLException();
		}catch(NotSavedException ex) {
			ex.printStackTrace();
			throw new InternalServerException();
		}
	}
	@RequestMapping(value="semantictags",method=RequestMethod.GET)
	public String getsemanticTags() {
		String jsonString="";
		String query="SELECT * FROM semanticTags;";
		System.out.println(query);
		ResultSet rs;
		SemanticTags tag;
		ArrayList<SemanticTags> tagList = new ArrayList<SemanticTags>();
		try {
			rs = Database.connect(query, Application.MODE_GET);
			while(rs.next()) {
				tag = new SemanticTags();
				tag.setId(rs.getString("id"));
				tag.setLabel(rs.getString("label"));
				tag.setSearch(rs.getString("search"));
				tag.setDescription(rs.getString("description"));
				tagList.add(tag);
			}
			jsonString = Application.gson.toJson(tagList);
		}catch(SQLException ex) {
			System.out.println("SQL Exception occured");
			ex.printStackTrace();
			return "SQL Error occured.";
		}catch(NotSavedException ex) {
			ex.printStackTrace();
			return "Not saved.";
		}
		return jsonString;
	}
	@RequestMapping(value="semantictags/{tagID}",method=RequestMethod.GET)
	public String getsemanticTag(@PathVariable(value="tagID") String tagID) {
		String jsonString="";
		String query="SELECT * FROM semanticTags WHERE id=\""+tagID+"\";";
		System.out.println(query);
		ResultSet rs;
		SemanticTags tag;
		try {
			rs = Database.connect(query, Application.MODE_GET);
			if(rs.next()) {
				tag = new SemanticTags();
				tag.setId(rs.getString("id"));
				tag.setLabel(rs.getString("label"));
				tag.setSearch(rs.getString("search"));
				tag.setDescription(rs.getString("description"));
				jsonString = Application.gson.toJson(tag);
			}
		}catch(SQLException ex) {
			System.out.println("SQL Exception occured");
			ex.printStackTrace();
			return "SQL Error occured.";
		}catch(NotSavedException ex) {
			ex.printStackTrace();
			return "Not saved.";
		}
		return jsonString;
	}
	@RequestMapping(value="concerttags/{concertID}",method=RequestMethod.GET)
	public String getConcertTags(@PathVariable(value="concertID") int concertID) {
		String jsonString="";
		String query="SELECT * FROM ConcertTags WHERE concert_id="+concertID+";";
		System.out.println(query);
		ResultSet rs;
		ConcertTags ctag;
		ArrayList<ConcertTags> ctagList = new ArrayList<ConcertTags>();
		try {
			rs = Database.connect(query, Application.MODE_GET);
			while(rs.next()) {
				ctag = new ConcertTags();
				ctag.setId(rs.getInt("id"));
				ctag.setTag_id(rs.getString("tag_id"));
				ctag.setConcert_id(rs.getInt("concert_id"));
				ctag.setCreated_at(rs.getDate("created_at"));
				ctagList.add(ctag);
			}
			jsonString = Application.gson.toJson(ctagList);
		}catch(SQLException ex) {
			System.out.println("SQL Exception occured");
			ex.printStackTrace();
			return "SQL Error occured.";
		}catch(NotSavedException ex) {
			ex.printStackTrace();
			return "Not saved.";
		}
		return jsonString;
	}
	@RequestMapping(value="searchWikidata/{search}",method=RequestMethod.GET)
	public String getSementicTagsFromWikidata(@PathVariable(value="search") String search) {
		String jsonString="";
		ArrayList<SemanticTags> tagList = new ArrayList<SemanticTags>();
		SemanticTags tag;
		try {

			URL url = new URL("https://www.wikidata.org/w/api.php?action=wbsearchentities&search="+search+"&language=en&format=json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output="";
			String line;
			while ((line = br.readLine()) != null) {
				output += line;
			}
			Object obj=JSONValue.parse(output);  
		    JSONObject jsonObject = (JSONObject) obj;  
		    
		    jsonString =  jsonObject.get("search").toString(); 
		    JSONArray result= (JSONArray) jsonObject.get("search");
		    Iterator i = result.iterator();
		    while (i.hasNext()) {
		    		JSONObject innerObj = (JSONObject) i.next();
		    		tag=new SemanticTags();
		    		tag.setId(innerObj.get("id").toString());
		    		tag.setLabel(innerObj.get("label").toString());
		    		tag.setSearch(search);
		    		tag.setDescription(innerObj.get("description").toString());
		    		tagList.add(tag);
		    	    
		    	    }

		    jsonString = Application.gson.toJson(tagList);
			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		return jsonString;
	}
	@RequestMapping(value="searchbytags/{tagID}",method=RequestMethod.GET)
	public String searchSemanticTag(@PathVariable(value="tagID") String tagID) {
		String jsonString="";
		String query;
		query = "SELECT Concerts.id AS Concerts_id, Concerts.name AS Concerts_name, Concerts.date_time AS Concerts_date_time, Concerts.min_price, Concerts.max_price, Concerts.rate AS Concerts_rate, Concerts.voter_amount as Concerts_voter_amount, Concerts.image_path AS Concerts_image_path, Users.id AS Users_id, Users.name AS Users_name, Users.email AS Users_email, Users.followers AS Users_followers, Users.followings AS Users_followings, Users.photo_path AS Users_photo_path, Users.created_at AS Users_created_at, Users.updated_at AS Users_updated_at, Users.last_login AS Users_last_login, Artists.id AS Artists_id, Artists.name AS Artists_name, Locations.id AS Locations_id, Locations.longitude AS Locations_longitude,Locations.latitude AS Locations_latitude, Locations.city AS Locations_city, Locations.address as Locations_address FROM Concerts INNER JOIN Users ON Concerts.created_by = Users.id INNER JOIN Artists ON Concerts.artist = Artists.id INNER JOIN Locations ON Concerts.location = Locations.id INNER JOIN ConcertTags ON Concerts.id=ConcertTags.concert_id WHERE ConcertTags.tag_id='"+tagID+"';";
		System.out.println(query);
		ResultSet rs;
		Concerts concert;
		ArrayList<Concerts> concertList = new ArrayList<Concerts>();
		Users user;
		Artists artist;
		Locations location;
		try {
			rs = Database.connect(query, Application.MODE_GET);
			while(rs.next()) {//System.out.println(rs.getInt("Concerts_id"));
				concert = new Concerts();
				user = new Users();
				artist = new Artists();
				location = new Locations();
				concert.setId(rs.getInt("Concerts_id"));
				concert.setName(rs.getString("Concerts_name"));
				concert.setMin_price(rs.getInt("min_price"));
				concert.setMax_price(rs.getInt("max_price"));
				concert.setRate(rs.getFloat("Concerts_rate"));
				concert.setVoter_amount(rs.getInt("Concerts_voter_amount"));
				concert.setImage_path(rs.getString("Concerts_image_path"));
				concert.setDate_time(rs.getTimestamp("Concerts_date_time"));
				user.setId(rs.getInt("Users_id"));
				user.setName(rs.getString("Users_name"));
				user.setEmail(rs.getString("Users_email"));
				user.setFollowers(rs.getInt("Users_followers"));
				user.setFollowings(rs.getInt("Users_followings"));
				user.setPhoto_path(rs.getString("Users_photo_path"));
				user.setCreated_at(rs.getTimestamp("Users_created_at"));
				user.setUpdated_at(rs.getTimestamp("Users_updated_at"));
				user.setLast_login(rs.getTimestamp("Users_last_login"));
				concert.setCreated_by(user);
				artist.setId(rs.getInt("Artists_id"));
				artist.setName(rs.getString("Artists_name"));
				concert.setArtist(artist);
				location.setId(rs.getInt("Locations_id"));
				location.setLatitude(rs.getDouble("Locations_latitude"));
				location.setLongitude(rs.getDouble("Locations_longitude"));
				location.setCity(rs.getString("Locations_city"));
				location.setAddress(rs.getString("Locations_address"));
				concert.setLocation(location);
				concertList.add(concert);
			}
			jsonString = Application.gson.toJson(concertList);
		}catch(SQLException ex) {
			System.out.println("SQL Exception occured");
			ex.printStackTrace();
			return "SQL Error occured.";
		}catch(NotSavedException ex) {
			ex.printStackTrace();
			return "Not saved.";
		}
		return jsonString;
	}
	
}