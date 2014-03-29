package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Users extends Model{
	@Id	
	public String email;
	
	public String password;
	
    public Users(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public static Finder<String,Users> find = new Finder<String,Users>( String.class, Users.class); 
	
    public static Users authenticate(String email, String password) {
        return find.where().eq("email", email).eq("password", password).findUnique();
    }
}
