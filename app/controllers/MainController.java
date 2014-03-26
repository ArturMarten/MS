package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import play.db.DB;
import play.mvc.Result;
import views.html.main;
import views.html.maineditor;

public class MainController extends ApplicationController{
	public static Result main(String sort) throws SQLException {
		Connection connection = DB.getConnection();
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT * FROM " + sort + " LIMIT 10;");
		
		List<String> article = new ArrayList<String>();
		while (result.next()) {
			article.add(result.getString("id"));
			article.add(result.getString("title"));
			article.add(result.getString("intro"));
			article.add(result.getString("image"));
		}

		result.close();
		statement.close();
		connection.close();

		return ok(main.render(article));
	}
	
	public static Result maineditor(String sort) throws SQLException {
		Connection connection = DB.getConnection();
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT * FROM " + sort + " LIMIT 10;");

		List<String> article = new ArrayList<String>();

		while (result.next()) {
			article.add(result.getString("id"));
			article.add(result.getString("title"));
			article.add(result.getString("intro"));
			article.add(result.getString("image"));
		}

		result.close();
		statement.close();
		connection.close();

		return ok(maineditor.render(article));
	}
}