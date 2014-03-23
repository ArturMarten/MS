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
		ResultSet result = statement.executeQuery("Select * from " + sort + " limit 10;");

		List<String> uudis = new ArrayList<String>();
		while (result.next()) {
			uudis.add(result.getString("id"));
			uudis.add(result.getString("pealkiri"));
			uudis.add(result.getString("sissejuhatus"));
		}

		result.close();
		statement.close();
		connection.close();

		return ok(main.render(uudis));
	}
	
	public static Result maineditor(String sort) throws SQLException {
		Connection connection = DB.getConnection();
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("Select * from " + sort + " limit 10;");

		List<String> uudis = new ArrayList<String>();

		while (result.next()) {
			uudis.add(result.getString("id"));
			uudis.add(result.getString("pealkiri"));
			uudis.add(result.getString("sissejuhatus"));
		}

		result.close();
		statement.close();
		connection.close();

		return ok(maineditor.render(uudis));
	}
}