package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Newsletter extends Model {
	@Id
	public int id;
	
	public String name;
	
	public String email;
	
	public int topic1;
	
	public int topic2;
	
	public int topic3;

	public int topic4;
	
	public int topic5;
}