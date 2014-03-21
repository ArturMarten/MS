package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import play.db.DB;

@Entity
@Table(name = "kommentaarid")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "nimi")
	private String nimi;

	@Column(name = "email")
	private String email;

	@Column(name = "meeldib")
	private int meeldib;

	@Column(name = "date")
	private Date date;

	@Column(name = "uudis_id")
	private int uudis_id;

	@Column(name = "sisu")
	private String sisu;
	
	public static ArrayList<ArrayList<String>> show(String uudis_id) throws SQLException{
		ArrayList<ArrayList<String>> kommentaar = new ArrayList<>();
		Connection connection = DB.getConnection();
		PreparedStatement statementKommentaar = connection
				.prepareStatement("Select * from kommentaarid where uudis_id = ? ORDER BY kuupaev");
		statementKommentaar.setInt(1, Integer.parseInt(uudis_id));
		ResultSet resultKommentaar = statementKommentaar.executeQuery();
		
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
		return kommentaar;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getMeeldib() {
		return meeldib;
	}

	public void setMeeldib(int meeldib) {
		this.meeldib = meeldib;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getUudis_id() {
		return uudis_id;
	}

	public void setUudis_id(int uudis_id) {
		this.uudis_id = uudis_id;
	}

	public String getSisu() {
		return sisu;
	}

	public void setSisu(String sisu) {
		this.sisu = sisu;
	}
}