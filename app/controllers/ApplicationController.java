package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import views.html.facebooklogin;

public class ApplicationController extends Controller {
	public static Result login() {
		return ok(login.render());
	}
	
	public static Result facebooklogin() {
		return ok(facebooklogin.render());
	}
}