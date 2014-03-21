package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

public class ApplicationController extends Controller {
	public static Result login() {
		return ok(login.render());
	}
}