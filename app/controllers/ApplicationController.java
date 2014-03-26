package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
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

	}

	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		return ok();
	}
}