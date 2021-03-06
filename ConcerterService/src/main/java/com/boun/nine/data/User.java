package com.boun.nine.data;

import java.util.Date;
import java.util.List;
/**
 * 
 * @author HİLAL DÖNMEZ
 * Determine user fields
 * Contain get and set methods for these fields  
 */

public class User {
	private int id;
	private int facebook_id;
	private int google_id;
	private String name;
	private String username;
	private String email;
	private String password;
	private String photo_path;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public int getFacebook_id() {
		return facebook_id;
	}
	public void setFacebook_id(int facebook_id) {
		this.facebook_id = facebook_id;
	}
	public int getGoogle_id() {
		return google_id;
	}
	public void setGoogle_id(int google_id) {
		this.google_id = google_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhoto_path() {
		return photo_path;
	}
	public void setPhoto_path(String photo_path) {
		this.photo_path = photo_path;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
