package com.boun.nine.service.concrete;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.core.Response;

import com.boun.nine.data.Concert;
import com.boun.nine.service.interfaces.ConnectedService;
import com.boun.nine.service.interfaces.IConcertResource;
import com.google.gson.Gson;

public class ConcertResource extends ConnectedService implements IConcertResource{

	@Override
	public void updateConcert(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteConcert(int id) {
		// TODO Auto-generated method stub
		
	}
	/**
	* <h1>Get all Concerts</h1>
	* The Get all concerts function returns all the existing
	* concerts on the database with a JSON code. Simply requesting
	* with a SELECT query with giving required parameters of
	* concert entity.
	*
	* @author  Efehan Atıcı
	* @version 1.0
	* @since   2017-04-21 
	*/
	@Override
	public String getAllConcerts() {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM concert";
		ResultSet rs;
		Concert concert = new Concert();
		Gson gson = new Gson();
		String json = new String();
		json += "[";
		try{
			rs = this.executeQuery(query);
			while(rs.next()){
				concert.setId(rs.getInt("id"));
				concert.setName(rs.getString("name"));
				concert.setOwnerId(rs.getInt("owner_id"));
				concert.setArtistName(rs.getString("artist_name"));
				concert.setLocationX(rs.getDouble("location_x"));
				concert.setLocationY(rs.getDouble("location_y"));
				concert.setMinPrice(rs.getDouble("min_price"));
				concert.setMaxPrice(rs.getDouble("max_price"));
				concert.setRate(rs.getDouble("rate"));
				json += gson.toJson(concert)+",";
			}
			json = json.substring(0, json.length()-1);
			json += "]";
			return json;
		}catch(SQLException ex){
			ex.printStackTrace();
			return null;
		}
		
	}
	@Override
	public String getConcert(int id) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM concert WHERE id="+id+";";
		ResultSet rs;
		Concert concert = new Concert();
		Gson gson = new Gson();
		try{
			rs = this.executeQuery(query);
			while(rs.next()){
				concert.setId(rs.getInt("id"));
				concert.setName(rs.getString("name"));
				concert.setOwnerId(rs.getInt("owner_id"));
				concert.setArtistName(rs.getString("artist_name"));
				concert.setLocationX(rs.getDouble("location_x"));
				concert.setLocationY(rs.getDouble("location_y"));
				concert.setMinPrice(rs.getDouble("min_price"));
				concert.setMaxPrice(rs.getDouble("max_price"));
				concert.setRate(rs.getDouble("rate"));
			}
			return gson.toJson(concert);
		}catch(SQLException ex){
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Response createConcert(String body) {
		// TODO Auto-generated method stub
		Concert concert = new Concert();
		Gson g = new Gson();
		String query = "INSERT INTO concert (";
		String values = " VALUES (";
		concert = g.fromJson(body,Concert.class);
		if(concert.getId() != 0){
			query+="id,";
			values += concert.getId()+",";
		}
		if(concert.getName() != null || concert.getName() != ""){
			query+="name,";
			values += "'"+concert.getName()+"',";
		}
		if(concert.getOwnerId() != 0){
			query+="owner_id,";
			values += concert.getOwnerId()+",";
		}
		if(concert.getArtistName() != null || concert.getArtistName() != ""){
			query += "artist_name,";
			values += "'"+concert.getArtistName()+"',";
		}
		if(concert.getLocationX() != 0){
			query +="location_x,";
			values += concert.getLocationX()+",";
		}
		if(concert.getLocationY() != 0){
			query += "location_y,";
			values += concert.getLocationY()+",";
		}
		if(concert.getMinPrice() != 0){
			query += "min_price,";
			values += concert.getMinPrice()+",";
		}
		if(concert.getMaxPrice() != 0){
			query += "max_price,";
			values += concert.getMaxPrice()+",";
		}
		if(concert.getRate() != 0){
			query += "rate,";
			values += concert.getRate();
		}
		if(query.endsWith(",")){
			query = query.substring(0, query.length()-1);
		}
		query += ")";
		if(values.endsWith(",")){
			values = values.substring(0, values.length()-1);
		}
		query += values+");";
		try{
			this.executeUpdate(query);
			return Response.status(200).entity("Concert is created").build();
		}catch(SQLException ex){
			ex.printStackTrace();
			return Response.status(500).entity("Oops! There was a database error. Try again.").build();
		}catch(Exception ex){
			ex.printStackTrace();
			return Response.status(500).entity("Oops! There was an internal error. Try again.").build();
		}
		
	}
	
}
