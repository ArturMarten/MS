package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import play.db.*;
import play.mvc.*;
import views.html.*;
import play.data.Form;
import play.data.DynamicForm;

public class Application extends Controller {

	public static Result main(String sort) throws SQLException {
		Connection connection = DB.getConnection();
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("Select * from " + sort
				+ " limit 4;");

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

	public static Result kustutaArtikkel(String uudis_id) throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("Delete from uudis where id = ?");
		statement.setInt(1, Integer.parseInt(uudis_id));
		statement.executeUpdate();

		statement.close();
		connection.close();
		return redirect(routes.Application.maineditor("avaleht_uued"));
	}

	public static Result article(String uudis_id) throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statementUudis = connection
				.prepareStatement("Select * from uudis where id = ?");

		statementUudis.setInt(1, Integer.parseInt(uudis_id));

		ResultSet resultUudis = statementUudis.executeQuery();
		resultUudis.next();

		List<String> uudiseandmed = new ArrayList<String>();
		uudiseandmed.add(resultUudis.getString("id"));
		uudiseandmed.add(resultUudis.getString("pealkiri"));
		uudiseandmed.add(resultUudis.getString("sissejuhatus"));
		uudiseandmed.add(resultUudis.getString("teemaarendus"));
		uudiseandmed.add(resultUudis.getString("kokkuvote"));

		int vaatamisi = resultUudis.getInt("vaatamistearv") + 1;

		statementUudis = connection
				.prepareStatement("Update uudis set vaatamistearv = ? where id = ?");
		statementUudis.setInt(1, vaatamisi);
		statementUudis.setInt(2, Integer.parseInt(uudis_id));

		statementUudis.executeUpdate();

		resultUudis.close();
		statementUudis.close();

		PreparedStatement statementKommentaar = connection
				.prepareStatement("Select * from kommentaarid where uudis_id = ? ORDER BY kuupaev");
		statementKommentaar.setInt(1, Integer.parseInt(uudis_id));
		ResultSet resultKommentaar = statementKommentaar.executeQuery();

		ArrayList<ArrayList<String>> kommentaar = new ArrayList<>();

		while (resultKommentaar.next()) {

			ArrayList<String> kommentaariAndmed = new ArrayList<String>();
			kommentaariAndmed.add(resultKommentaar.getString("id"));
			kommentaariAndmed.add(resultKommentaar.getString("nimi"));
			kommentaariAndmed.add(resultKommentaar.getString("email"));
			kommentaariAndmed.add(resultKommentaar.getString("meeldib"));
			kommentaariAndmed.add(resultKommentaar.getString("kuupaev"));
			kommentaariAndmed.add(resultKommentaar.getString("sisu"));
			kommentaar.add(kommentaariAndmed);
		}
		resultKommentaar.close();
		statementKommentaar.close();
		connection.close();

		return ok(article.render(uudiseandmed, kommentaar));
	}

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

		return redirect(routes.Application.article(uudis_id));
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

		return redirect(routes.Application.article(uudis_id));
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

		return redirect(routes.Application.article(uudis_id));
	}

	public static Result articleeditor(String uudis_id) throws SQLException {

		Connection connection = DB.getConnection();
		PreparedStatement statementUudis = connection
				.prepareStatement("Select * from uudis where id = ?");

		statementUudis.setInt(1, Integer.parseInt(uudis_id));

		ResultSet resultUudis = statementUudis.executeQuery();
		resultUudis.next();

		List<String> uudiseandmed = new ArrayList<String>();
		uudiseandmed.add(resultUudis.getString("id"));
		uudiseandmed.add(resultUudis.getString("pealkiri"));
		uudiseandmed.add(resultUudis.getString("sissejuhatus"));
		uudiseandmed.add(resultUudis.getString("teemaarendus"));
		uudiseandmed.add(resultUudis.getString("kokkuvote"));

		statementUudis.close();
		resultUudis.close();

		PreparedStatement statementKommentaar = connection
				.prepareStatement("Select * from kommentaarid where uudis_id = ? ORDER BY kuupaev");
		statementKommentaar.setInt(1, Integer.parseInt(uudis_id));
		ResultSet resultKommentaar = statementKommentaar.executeQuery();

		ArrayList<ArrayList<String>> kommentaar = new ArrayList<>();

		while (resultKommentaar.next()) {

			ArrayList<String> kommentaariAndmed = new ArrayList<String>();
			kommentaariAndmed.add(resultKommentaar.getString("id"));
			kommentaariAndmed.add(resultKommentaar.getString("nimi"));
			kommentaariAndmed.add(resultKommentaar.getString("email"));
			kommentaariAndmed.add(resultKommentaar.getString("meeldib"));
			kommentaariAndmed.add(resultKommentaar.getString("kuupaev"));
			kommentaariAndmed.add(resultKommentaar.getString("sisu"));
			kommentaar.add(kommentaariAndmed);
		}
		resultKommentaar.close();
		statementKommentaar.close();
		connection.close();

		return ok(articleeditor.render(uudiseandmed, kommentaar));
	}
	
	public static Result uusUudis() throws SQLException{
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection.prepareStatement("Insert into uudis(pealkiri,sissejuhatus) values('Kirjuta siia pealkiri','Sissejuhatus')");
		statement.executeUpdate();
		
		PreparedStatement statement2 = connection.prepareStatement("SELECT id from uudis order by kuupaev desc limit 1");
		
		ResultSet result = statement2.executeQuery();
		result.next();
		String uudis_id = result.getString("id");
		
		statement.close();
		connection.close();
		
		return redirect(routes.Application.articleeditor(uudis_id));
	
	}

	public static Result salvestaUudis(String uudis_id) throws SQLException {
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
		
		return redirect(routes.Application.maineditor("avaleht_uued"));
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

		return redirect(routes.Application.articleeditor(uudis_id));
	}

	public static Result login() {
		return ok(login.render());
	}

	public static Result maineditor(String sort) throws SQLException {
		Connection connection = DB.getConnection();
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("Select * from " + sort
				+ " limit 4;");

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

	public static Result uudistevoogLisaKirje() throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("Insert Into uudistevoog(nimi,email,teema1,teema2,teema3,teema4,teema5) Values(?,?,?,?,?,?,?)");

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

		return redirect(routes.Application.newsletter());
	}

	public static Result newslettereditor() throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("Select * from uudistevoog");

		ResultSet result = statement.executeQuery();
		ArrayList<ArrayList<String>> uudistevooAndmed = new ArrayList<>();
		while (result.next()) {
			ArrayList<String> rida = new ArrayList<String>();
			rida.add(result.getString("id"));
			rida.add(result.getString("nimi"));
			rida.add(result.getString("email"));
			rida.add(result.getString("teema1"));
			rida.add(result.getString("teema2"));
			rida.add(result.getString("teema3"));
			rida.add(result.getString("teema4"));
			rida.add(result.getString("teema5"));
			uudistevooAndmed.add(rida);
		}

		statement.close();
		result.close();
		connection.close();
		return ok(newslettereditor.render(uudistevooAndmed));
	}

	public static Result kustutaInimeneUudistevoog(String uudistevoog_id)
			throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("Delete from uudistevoog where id = ?");
		statement.setInt(1, Integer.parseInt(uudistevoog_id));
		
		statement.executeUpdate();

		statement.close();
		connection.close();

		return redirect(routes.Application.newslettereditor());
	}

	public static Result newsletter() {
		return ok(newsletter.render());
	}
}
