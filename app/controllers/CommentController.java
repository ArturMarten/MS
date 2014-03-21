package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import play.data.DynamicForm;
import play.data.Form;
import play.db.DB;
import play.mvc.Result;

public class CommentController extends ApplicationController{
	public static Result saveComment(String uudis_id) throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("Insert Into kommentaarid(nimi,email,meeldib,uudis_id,sisu) Values(?,?,?,?,?)");

		DynamicForm data = Form.form().bindFromRequest();
		String nimiKommentaar = data.get("nimi");
		String emailKommentaar = data.get("e-mail");
		String kommentaarikast = data.get("kommentaarikast");
		
		statement.setString(1, nimiKommentaar);
		statement.setString(2, emailKommentaar);
		statement.setInt(3, 0);
		statement.setInt(4, Integer.parseInt(uudis_id));
		statement.setString(5, kommentaarikast);
		statement.executeUpdate();
		
		statement.close();
		connection.close();

		return redirect(routes.ArticleController.article(uudis_id));
	}
	
	public static Result deleteComment(String comment_id) throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM kommentaarid WHERE id = ?");

		statement.setInt(1, Integer.parseInt(comment_id));
		ResultSet result = statement.executeQuery();
		result.next();

		String uudis_id = Integer.toString(result.getInt("uudis_id"));

		statement = connection
				.prepareStatement("Delete from kommentaarid where id = ?");
		statement.setInt(1, Integer.parseInt(comment_id));
		statement.executeUpdate();

		statement.close();
		connection.close();

		return redirect(routes.ArticleController.articleeditor(uudis_id));
	}

	public static Result addLike(String comment_id) throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM kommentaarid WHERE id = ?");

		statement.setInt(1, Integer.parseInt(comment_id));
		ResultSet result = statement.executeQuery();
		result.next();

		int like = result.getInt("meeldib") + 1;
		String uudis_id = Integer.toString(result.getInt("uudis_id"));

		statement = connection
				.prepareStatement("UPDATE kommentaarid SET meeldib = ? WHERE id = ?");
		statement.setInt(1, like);
		statement.setInt(2, Integer.parseInt(comment_id));
		statement.executeUpdate();

		result.close();
		statement.close();
		connection.close();

		return redirect(routes.ArticleController.article(uudis_id));
	}

	public static Result inappropriateComment(String comment_id)
			throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM kommentaarid WHERE id = ?");

		statement.setInt(1, Integer.parseInt(comment_id));
		ResultSet result = statement.executeQuery();
		result.next();

		int ebasobiv = result.getInt("ebasobiv") + 1;
		String uudis_id = Integer.toString(result.getInt("uudis_id"));

		statement = connection
				.prepareStatement("UPDATE kommentaarid SET ebasobiv = ? WHERE id = ?");
		statement.setInt(1, ebasobiv);
		statement.setInt(2, Integer.parseInt(comment_id));
		statement.executeUpdate();

		result.close();
		statement.close();
		connection.close();

		return redirect(routes.ArticleController.article(uudis_id));
	}
}