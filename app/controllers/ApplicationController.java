package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;

import models.Pinger;
import models.Users;
import play.libs.Akka;
import play.libs.F.Callback0;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.login;
import views.html.register;
import play.data.Form;
import static play.data.Form.*;
import static java.util.concurrent.TimeUnit.SECONDS;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import play.Logger;
import play.data.DynamicForm;
import play.db.DB;

import scala.concurrent.duration.Duration;


public class ApplicationController extends Controller {
	public static Result login(String url) {
		return ok(login.render(form(Login.class), url));
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

	public static Result logout(String url) {
		session().clear();
		return redirect(url);
	}

	public static Result facebookLogin(String url) {
		JsonNode json = request().body().asJson();
		String email = json.get("email").asText();
		String first_name = json.get("first_name").asText();
		String last_name = json.get("last_name").asText();
		Users user=null;
		try {
			user = Users.find.byId(email);
		} catch (Exception e) {
			user= new Users(email, "", first_name, last_name);
			Ebean.save(user);
		}
		if(user==null){
			user= new Users(email, "", first_name, last_name);
			Ebean.save(user);
		}
		session().put("email", email);
		return ok(url);
	}

	public static Result authenticate(String url) {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(login.render(loginForm, url));
		} else {
			session().clear();
			session("email", loginForm.get().email);
			return redirect(url);
		}
	}

	public static class Register {
		public String email;
		public String password;
		public String first_name;
		public String last_name;
	}

	public static Result register() {
		Users user = null;
		try {
			user = Users.find.byId(session().get("email"));
		} catch (Exception e) {
		}
		return ok(register.render(form(Register.class)));
	}

	public static Result registerForm() {
		Form<Register> registerForm = form(Register.class).bindFromRequest();
		String email = registerForm.get().email;
		String password = registerForm.get().password;
		String first_name = registerForm.get().first_name;
		String last_name = registerForm.get().last_name;
		if (registerForm.hasErrors()) {
			return badRequest(register.render(registerForm));
		} else {
			if (email.equals("") || password.equals("")) {
				return redirect(routes.ApplicationController.register());
			} else {
				Users user = new Users(email, password, first_name, last_name);
				Ebean.save(user);
				session().put("email", registerForm.get().email);
				return redirect(routes.MainController.main("new"));
			}
		}
	}
	public static WebSocket<String> timeWs() {
		return new WebSocket<String>() {
			public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
				final ActorRef pingActor = Akka.system().actorOf(Props.create(Pinger.class, in, out));
				final Cancellable cancellable = Akka.system().scheduler().schedule(Duration.create(1, SECONDS),
													Duration.create(1, SECONDS),
													pingActor,
													"Tick",
													Akka.system().dispatcher(),
													null
													);
				in.onClose(new Callback0() {
					@Override
					public void invoke() throws Throwable {
						cancellable.cancel();
					}
				});
			}
		};
	}
	
	public static Result timeJs() {
	    return ok(views.js.time.render());
	}
}