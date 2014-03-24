package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import play.data.DynamicForm;
import play.data.Form;
import play.db.DB;
import play.mvc.Result;
import views.html.newsletter;
import views.html.newslettereditor;

public class NewsletterController extends ApplicationController{
	
	public static Result newsletter() {
		return ok(newsletter.render());
	}
	
	public static Result newslettereditor() throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM newsletter");
		ResultSet result = statement.executeQuery();
		
		ArrayList<ArrayList<String>> uudistevooAndmed = new ArrayList<>();
		while (result.next()) {
			ArrayList<String> rida = new ArrayList<String>();
			rida.add(result.getString("id"));
			rida.add(result.getString("name"));
			rida.add(result.getString("email"));
			rida.add(result.getString("topic1"));
			rida.add(result.getString("topic2"));
			rida.add(result.getString("topic3"));
			rida.add(result.getString("topic4"));
			rida.add(result.getString("topic5"));
			uudistevooAndmed.add(rida);
		}

		statement.close();
		result.close();
		connection.close();
		
		return ok(newslettereditor.render(uudistevooAndmed));
	}
	
	public static Result addToNewsletter() throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection.prepareStatement("INSERT INTO newsletter(name,email,topic1,topic2,topic3,topic4,topic5) VALUES(?,?,?,?,?,?,?)");

		DynamicForm data = Form.form().bindFromRequest();
		String nimi = data.get("username");
		String email = data.get("email");
		String teema1 = data.get("options1");
		String teema2 = data.get("options2");
		String teema3 = data.get("options3");
		String teema4 = data.get("options4");
		String teema5 = data.get("options5");
		
		if (teema1 == null) {
			statement.setInt(3, 0);
		} else {
			statement.setInt(3, 1);
		}
		if (teema2 == null) {
			statement.setInt(4, 0);
		} else {
			statement.setInt(4, 1);
		}
		if (teema3 == null) {
			statement.setInt(5, 0);
		} else {
			statement.setInt(5, 1);
		}
		if (teema4 == null) {
			statement.setInt(6, 0);
		} else {
			statement.setInt(6, 1);
		}
		if (teema5 == null) {
			statement.setInt(7, 0);
		} else {
			statement.setInt(7, 1);
		}
		statement.setString(1, nimi);
		statement.setString(2, email);

		statement.executeUpdate();
		statement.close();
		connection.close();

		return redirect(routes.NewsletterController.newsletter());
	}

	public static Result removeFromNewsletter(String newsletter_id)
			throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection.prepareStatement("DELETE FROM newsletter WHERE id = ?");
		statement.setInt(1, Integer.parseInt(newsletter_id));
		statement.executeUpdate();

		statement.close();
		connection.close();

		return redirect(routes.NewsletterController.newslettereditor());
	}

}