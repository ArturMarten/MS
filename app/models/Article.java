package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.db.DB;
import play.db.ebean.Model;

@Entity
public class Article extends Model{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	
	public String title;
	
	public String intro;
	
	public String body;
	
	public String summary;
	
	public Date date;
	
	public byte[] image;
	
	public int views;
	
	public static List<String> show(String article_id) throws SQLException{
		List<String> uudiseandmed = new ArrayList<String>();
		Connection connection = DB.getConnection();
		PreparedStatement statementUudis = connection
				.prepareStatement("SELECT * FROM article WHERE id = ?");

		statementUudis.setInt(1, Integer.parseInt(article_id));

		ResultSet resultUudis = statementUudis.executeQuery();
		resultUudis.next();
		uudiseandmed.add(resultUudis.getString("id"));
		uudiseandmed.add(resultUudis.getString("title"));
		uudiseandmed.add(resultUudis.getString("intro"));
		uudiseandmed.add(resultUudis.getString("body"));
		uudiseandmed.add(resultUudis.getString("summary"));

		int vaatamisi = resultUudis.getInt("views") + 1;

		statementUudis = connection
				.prepareStatement("UPDATE article SET views = ? WHERE id = ?");
		statementUudis.setInt(1, vaatamisi);
		statementUudis.setInt(2, Integer.parseInt(article_id));

		statementUudis.executeUpdate();

		resultUudis.close();
		statementUudis.close();
		
		return uudiseandmed;
	}
}