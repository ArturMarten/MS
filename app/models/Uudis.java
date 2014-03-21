package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;


@Entity
@Table(name="uudis")
public class Uudis {
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