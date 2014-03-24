package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.DB;
import play.db.ebean.Model;

@Entity
public class Comment extends Model {
	@Id
	public int id;
	
	public String name;
	
	public String email;
	
	public int likes;
	
	public Date date;
	
	public int article_id;
	
	public String content;
	
	public int inappropriate;
	
	public static ArrayList<ArrayList<String>> show(String article_id) throws SQLException{
		ArrayList<ArrayList<String>> comment = new ArrayList<>();
		Connection connection = DB.getConnection();
		PreparedStatement statementKommentaar = connection
				.prepareStatement("SELECT * FROM comment WHERE article_id = ? ORDER BY date");
		statementKommentaar.setInt(1, Integer.parseInt(article_id));
		ResultSet resultKommentaar = statementKommentaar.executeQuery();
		
		while (resultKommentaar.next()) {
			ArrayList<String> kommentaariAndmed = new ArrayList<String>();
			kommentaariAndmed.add(resultKommentaar.getString("id"));
			kommentaariAndmed.add(resultKommentaar.getString("name"));
			kommentaariAndmed.add(resultKommentaar.getString("email"));
			kommentaariAndmed.add(resultKommentaar.getString("likes"));
			kommentaariAndmed.add(resultKommentaar.getString("date"));
			kommentaariAndmed.add(resultKommentaar.getString("content"));
			comment.add(kommentaariAndmed);
		}
		resultKommentaar.close();
		statementKommentaar.close();
		connection.close();
		return comment;
	}
}