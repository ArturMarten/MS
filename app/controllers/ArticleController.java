package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Comment;
import models.Article;

import play.data.DynamicForm;
import play.data.Form;
import play.db.DB;
import play.mvc.Result;
import views.html.article;
import views.html.articleeditor;

public class ArticleController extends ApplicationController{
	public static Result article(String article_id) throws SQLException {
		List<String> uudiseandmed = Article.show(article_id);
		ArrayList<ArrayList<String>> kommentaar = Comment.show(article_id);
		return ok(article.render(uudiseandmed, kommentaar));
	}
	
	public static Result articleeditor(String article_id) throws SQLException {
		List<String> uudiseandmed = Article.show(article_id);
		ArrayList<ArrayList<String>> kommentaar = Comment.show(article_id);
		return ok(articleeditor.render(uudiseandmed, kommentaar));
	}

	public static Result newArticle() throws SQLException{
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection.prepareStatement("INSERT INTO article(title,intro,body,summary) VALUES ('Pealkiri','Sissejuhatus','Teemaarendus','Kokkuvõte')");
		statement.executeUpdate();
		
		PreparedStatement statement2 = connection.prepareStatement("SELECT id FROM article ORDER BY date DESC LIMIT 1");
		ResultSet result = statement2.executeQuery();
		result.next();
		String article_id = result.getString("id");
		
		statement.close();
		connection.close();
		
		return redirect(routes.ArticleController.articleeditor(article_id));
	}

	public static Result saveArticle(String article_id) throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection.prepareStatement("UPDATE article SET title=?, intro = ?, body = ?, summary = ?  WHERE id =?");

		DynamicForm data = Form.form().bindFromRequest();
		String title = data.get("title");
		String intro = data.get("intro");
		String body = data.get("body");
		String summary= data.get("summary");

		statement.setString(1,title);
		statement.setString(2,intro );
		statement.setString(3, body );
		statement.setString(4,summary);
		statement.setInt(5, Integer.parseInt(article_id));
		statement.executeUpdate();

		connection.close();
		statement.close();

		return redirect(routes.MainController.maineditor("main_new"));
	}
	
	public static Result deleteArticle(String article_id) throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection.prepareStatement("DELETE FROM article WHERE id = ?");
		statement.setInt(1, Integer.parseInt(article_id));
		statement.executeUpdate();

		statement.close();
		connection.close();
		
		return redirect(routes.MainController.maineditor("main_new"));
	}
}