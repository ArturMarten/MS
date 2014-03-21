package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "kommentaarid")
public class Kommentaar {

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