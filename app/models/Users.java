package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity(name="users")
public class Users extends Model{
	@Id
	public int id;
	
	public String email;
	
	public String password;
}
