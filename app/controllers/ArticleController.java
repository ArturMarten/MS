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
	public static Result article(String uudis_id) throws SQLException {
		List<String> uudiseandmed = Article.show(uudis_id);
		ArrayList<ArrayList<String>> kommentaar = Comment.show(uudis_id);
		return ok(article.render(uudiseandmed, kommentaar));
	}
	
	public static Result articleeditor(String uudis_id) throws SQLException {
		List<String> uudiseandmed = Article.show(uudis_id);
		ArrayList<ArrayList<String>> kommentaar = Comment.show(uudis_id);
		return ok(articleeditor.render(uudiseandmed, kommentaar));
	}

	public static Result newArticle() throws SQLException{
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection.prepareStatement("Insert into uudis(pealkiri,sissejuhatus) values('Kirjuta siia pealkiri','Sissejuhatus')");
		statement.executeUpdate();
		
		PreparedStatement statement2 = connection.prepareStatement("SELECT id from uudis order by kuupaev desc limit 1");
		ResultSet result = statement2.executeQuery();
		result.next();
		String uudis_id = result.getString("id");
		
		statement.close();
		connection.close();
		
		return redirect(routes.ArticleController.articleeditor(uudis_id));
	}

	public static Result saveArticle(String uudis_id) throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection.prepareStatement("update uudis set pealkiri=?, sissejuhatus = ?, teemaarendus = ?, kokkuvote = ?  where id =?");
		
		DynamicForm data = Form.form().bindFromRequest();
		String pealkiri = data.get("pealkiri");
		String sissejuhatus = data.get("sissejuhatus");
		String teemaarendus = data.get("teemaarendus");
		String kokkuvote= data.get("kokkuvote");
		
		statement.setString(1,pealkiri);
		statement.setString(2,sissejuhatus );
		statement.setString(3, teemaarendus );
		statement.setString(4,kokkuvote);
		statement.setInt(5, Integer.parseInt(uudis_id));
		statement.executeUpdate();
		
		connection.close();
		statement.close();
		
		return redirect(routes.MainController.maineditor("avaleht_uued"));
	}
	
	public static Result deleteArticle(String uudis_id) throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("Delete from uudis where id = ?");
		statement.setInt(1, Integer.parseInt(uudis_id));
		statement.executeUpdate();

		statement.close();
		connection.close();
		
		return redirect(routes.MainController.maineditor("avaleht_uued"));
	}
}