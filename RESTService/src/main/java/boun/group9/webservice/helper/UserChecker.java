package boun.group9.webservice.helper;

import java.sql.ResultSet;

import boun.group9.webservice.app.Application;
import boun.group9.webservice.app.data.Users;

public class UserChecker {
	public static Users getUser(int userID){
		String query = "SELECT * FROM Users WHERE id="+userID+";";
		Users user = new Users();
		ResultSet rs;
		try {
			rs = Database.connect(query, Application.MODE_GET);
			if(rs.next()) {
				user.setId(rs.getInt("id"));
				user.setSpotify_id(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setFollowers(rs.getInt("followers"));
				user.setFollowings(rs.getInt("followings"));
				user.setPhoto_path(rs.getString("photo_path"));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return user;
	}
	// bu userID  birini follow ederse
	public static String follow(int userID){
		String query = "Update users SET followings = followings + 1 WHERE users.id = "+ userID +  ";";
		return query;
	}

	// bu userID  birini unfollow ederse
	public static String unFollow(int userID){
		String query = "Update users SET followings = followings - 1 WHERE users.id = "+ userID +  ";";
		return query;
	}

	// bu userID'yi  biri follow ederse
	public static String followed(int userID){
		String query = "Update users SET followers = followers + 1 WHERE users.id = "+ userID +  ";";
		return query;
	}

	// bu userID'yi  biri unfollow ederse
	public static String unFollowed(int userID){
		String query = "Update users SET followers = followers - 1 WHERE users.id = "+ userID +  ";";
		return query;
	}


	public static String insertUserQuery(Users user) {
		java.util.Date date = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(date);
		String query = "INSERT INTO Users ";
		String fieldQuery = "(";
		String valuesQuery = "VALUES(";
		if(user.getId() != 0) {
			fieldQuery+="id,";
			valuesQuery+=user.getId()+",";
		}
		if(user.getName() != null) {
			fieldQuery+="name,";
			valuesQuery+="'"+user.getName()+"',";
		}
		if(user.getUsername() != null) {
			fieldQuery+="username,";
			valuesQuery+="'"+user.getUsername()+"',";
		}
		if(user.getEmail() != null) {
			fieldQuery += "email,";
			valuesQuery+="'"+user.getEmail()+"',";
		}
		if(user.getPassword() != null) {
			fieldQuery += "password,";
			valuesQuery+="'"+user.getPassword()+"',";
		}
			fieldQuery += "followers,";
			valuesQuery += user.getFollowers()+",";
			fieldQuery += "followings,";
			valuesQuery += user.getFollowings()+",";
		if(user.getPhoto_path() != null) {
			fieldQuery += "photo_path,";
			valuesQuery += "'"+user.getPhoto_path()+"',";
		}
		fieldQuery += "created_at,";
		valuesQuery += "'"+currentTime+"',";
		fieldQuery += "updated_at,";
		valuesQuery += "'"+currentTime+"',";
		fieldQuery += "last_login";
		valuesQuery += "'"+currentTime+"'";
		if(fieldQuery.endsWith(",")) {
			fieldQuery = fieldQuery.substring(0, fieldQuery.length()-1);
		}
		if(valuesQuery.endsWith(",")) {
			valuesQuery = valuesQuery.substring(0, valuesQuery.length()-1);
		}
		query+=fieldQuery+") "+valuesQuery+");";
		System.out.println(query);
		return query;
	}
}
