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
		PreparedStatement statement = connection.prepareStatement("Insert into article(title,intro) values('Kirjuta siia pealkiri','Sissejuhatus')");
		statement.executeUpdate();
		
		PreparedStatement statement2 = connection.prepareStatement("SELECT id from article order by date desc limit 1");
		ResultSet result = statement2.executeQuery();
		result.next();
		String article_id = result.getString("id");
		
		statement.close();
		connection.close();
		
		return redirect(routes.ArticleController.articleeditor(article_id));
	}

	public static Result saveArticle(String article_id) throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection.prepareStatement("update article set title=?, intro = ?, body = ?, summary = ?  where id =?");
		
		DynamicForm data = Form.form().bindFromRequest();
		String pealkiri = data.get("title");
		String sissejuhatus = data.get("intro");
		String teemaarendus = data.get("body");
		String kokkuvote= data.get("summary");
		
		statement.setString(1,pealkiri);
		statement.setString(2,sissejuhatus );
		statement.setString(3, teemaarendus );
		statement.setString(4,kokkuvote);
		statement.setInt(5, Integer.parseInt(article_id)+1);
		statement.executeUpdate();
		
		connection.close();
		statement.close();
		
		return redirect(routes.MainController.maineditor("main_new"));
	}
	
	public static Result deleteArticle(String article_id) throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("Delete from article where id = ?");
		statement.setInt(1, Integer.parseInt(article_id));
		statement.executeUpdate();

		statement.close();
		connection.close();
		
		return redirect(routes.MainController.maineditor("main_new"));
	}
}