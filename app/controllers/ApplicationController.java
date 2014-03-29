package controllers;

import com.avaje.ebean.Ebean;

import models.Users;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import views.html.register;
import play.data.Form;
import static play.data.Form.*;

public class ApplicationController extends Controller {
	public static Result login() {
		return ok(
				login.render(form(Login.class))
		);
	}
	
	public static class Login {
		public String email;
		public String password;
		
		public String validate() {
		    if (Users.authenticate(email, password) == null) {
		      return "Invalid user or password";
		    }
		    return null;
		}
	}
	
	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
	        return badRequest(login.render(loginForm));
	    } else {
	        session().clear();
	        session("email", loginForm.get().email);
	        return redirect(routes.MainController.maineditor("main_new"));
	    }
	}
	
	public static class Register {
		public String email;
		public String password;
	}
	
	public static Result register() {
    	Users user = null;
    	try{
    		user = Users.find.byId(session().get("email"));
    	} catch(Exception e){}
        return ok(register.render( 
            form(Register.class)
        )); 
    }
	
	public static Result registerForm() {
		Form<Register> registerForm = form(Register.class).bindFromRequest();
		if (registerForm.hasErrors()) {
			return badRequest(register.render(registerForm));
		} 
		else {
			Users user = new Users(registerForm.get().email,registerForm.get().password);
			Ebean.save(user);
			session().put("email", registerForm.get().email);
			return redirect(routes.MainController.maineditor("main_new"));
		}
	}
}