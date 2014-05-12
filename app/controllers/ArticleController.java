package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import models.Comment;
import models.Article;
import models.Users;
import play.data.DynamicForm;
import play.data.Form;
import play.db.DB;
import play.mvc.Security;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.article;



public class ArticleController extends ApplicationController{
	public static Result article(String article_id) throws SQLException {
		List<String> uudiseandmed = Article.show(article_id);
		ArrayList<ArrayList<String>> kommentaar = Comment.show(article_id);
		Users user= null;
		try{
			user = Users.find.byId(session().get("email"));			
		}
		catch(NullPointerException e){}
		return ok(article.render(uudiseandmed, kommentaar, user));
	}
	
	@Security.Authenticated(Secured.class)
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
		
		return redirect(routes.ArticleController.article(article_id));
	}
	@Security.Authenticated(Secured.class)
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
		return redirect(routes.MainController.main("new"));
	}
	@Security.Authenticated(Secured.class)
	public static Result deleteArticle(String article_id) throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection.prepareStatement("DELETE FROM article WHERE id = ?");
		statement.setInt(1, Integer.parseInt(article_id));
		statement.executeUpdate();
		statement.close();
		connection.close();
		return redirect(routes.MainController.main("new"));
	}
	@Security.Authenticated(Secured.class)
	public static Result uploadImage(String article_id) throws SQLException, IOException {
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart image = body.getFile("image");
		if (image != null) {
			File file = image.getFile();
			InputStream isFile = new FileInputStream(file);
			byte[] byteFile = IOUtils.toByteArray(isFile);
			
			Connection connection = DB.getConnection();
			PreparedStatement statement = connection.prepareStatement("UPDATE article SET image = ? WHERE id = ?");
			
			statement.setBytes(1,byteFile);
			statement.setInt(2, Integer.parseInt(article_id));
			statement.executeUpdate();
			
			statement.close();
			connection.close();
		}
		return redirect(routes.ArticleController.article(article_id));
	}
	
	public static Result getImage(String article_id) throws SQLException, IOException {
		File image = new File("tmp/image"+article_id+".jpg");
		if(image.exists()){
			return ok(image);
		}
		else{
			Connection connection = DB.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT image FROM article WHERE id = ?");
			statement.setInt(1, Integer.parseInt(article_id));
			ResultSet result = statement.executeQuery();
			result.next();
			
			byte[] byteFile = result.getBytes("image");
			FileUtils.writeByteArrayToFile(image, byteFile);
			
			statement.close();
			connection.close();
			
			return ok(image);
		}
	}
}