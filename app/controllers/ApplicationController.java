package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import play.data.Form;
import static play.data.Form.*;

public class ApplicationController extends Controller {
		public static Result login() {
			return ok(login.render());
		}
}