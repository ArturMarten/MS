package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import play.db.DB;

@Entity
@Table(name="uudis")
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="pealkiri")
	private String pealkiri;
	
	@Column(name="sissejuhatus")
	private String sissejuhatus;
	
	@Column(name="teemaarendus")
	private String teemaarendus;
	
	@Column(name="kokkuvote")
	private String kokkuvote;

	@Column(name="date")
	private Date date;
	
	@Column(name="pilt")
	private byte[] pilt;

	@Column(name="kommentaar_id")
	private int kommentaar_id;
	
	
	public static List<String> show(String uudis_id) throws SQLException{
		List<String> uudiseandmed = new ArrayList<String>();
		Connection connection = DB.getConnection();
		PreparedStatement statementUudis = connection
				.prepareStatement("Select * from uudis where id = ?");

		statementUudis.setInt(1, Integer.parseInt(uudis_id));

		ResultSet resultUudis = statementUudis.executeQuery();
		resultUudis.next();
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
		
		return uudiseandmed;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPealkiri() {
		return pealkiri;
	}

	public void setPealkiri(String pealkiri) {
		this.pealkiri = pealkiri;
	}

	public String getSissejuhatus() {
		return sissejuhatus;
	}

	public void setSissejuhatus(String sissejuhatus) {
		this.sissejuhatus = sissejuhatus;
	}

	public String getTeemaarendus() {
		return teemaarendus;
	}

	public void setTeemaarendus(String teemaarendus) {
		this.teemaarendus = teemaarendus;
	}

	public String getKokkuvote() {
		return kokkuvote;
	}

	public void setKokkuvote(String kokkuvote) {
		this.kokkuvote = kokkuvote;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public byte[] getPilt() {
		return pilt;
	}

	public void setPilt(byte[] pilt) {
		this.pilt = pilt;
	}

	public int getKommentaar_id() {
		return kommentaar_id;
	}

	public void setKommentaar_id(int kommentaar_id) {
		this.kommentaar_id = kommentaar_id;
	}
}