package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Users extends Model{
	@Id	
	public int id;
	
	public String email;
	
	public String password;
	
	public String first_name;
	
	public String last_name;
	
	public Users(String email, String password, String first_name, String last_name) {
		this.email = email;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
	}

	public static Finder<String,Users> find = new Finder<String,Users>( String.class, Users.class); 
	
    public static Users authenticate(String email, String password) {
        return find.where().eq("email", email).eq("password", password).findUnique();
    }
}
