
package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Users;
import play.db.DB;
import play.mvc.Result;
import views.html.main;

public class MainController extends ApplicationController {
	public static Result main(String sort) throws SQLException {
		Connection connection = DB.getConnection();
		Statement statement = connection.createStatement();
		ResultSet result = sortArticles(sort,statement);
		List<String> articles = new ArrayList<String>();
		while (result.next()) {
			articles.add(result.getString("id"));
			articles.add(result.getString("title"));
			articles.add(result.getString("intro"));
		}
		result.close();
		statement.close();
		connection.close();
		
		Users user = null;
		try{
			user = Users.find.byId(session().get("email"));			
		}
		catch(NullPointerException e){}
		
		return ok(main.render(articles, user));
	}

	public static ResultSet sortArticles(String sort, Statement statement) throws SQLException {
		ResultSet result = null;
		if (sort == "new") {
			result = statement
					.executeQuery("SELECT article.id, article.title, article.intro FROM article ORDER BY article.date DESC LIMIT 10");
		} else if (sort == "most_viewed") {
			result = statement
					.executeQuery("SELECT article.id, article.title, article.intro FROM article ORDER BY article.views DESC LIMIT 10");
		} else if (sort == "most_commentated") {
			result = statement
					.executeQuery("SELECT article.id, article.title, article.intro, count(*) AS count FROM article INNER JOIN comment ON (article.id = comment.article_id) GROUP BY article.id ORDER BY count(*) DESC LIMIT 10");
		}
		return result;
	}
}