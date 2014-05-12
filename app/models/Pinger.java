package models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import play.mvc.WebSocket;
import akka.actor.UntypedActor;

public class Pinger extends UntypedActor {
	WebSocket.In<String> in;
	WebSocket.Out<String> out;
	
	public Pinger(WebSocket.In<String> in, WebSocket.Out<String> out) {
		this.in = in;
		this.out = out;
	}
	
	@Override
	public void onReceive(Object message) {
		if (message.equals("Tick")) {
			Locale locale = new Locale("et", "ET");
			SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd.MMMM yyyy HH:mm:ss",locale);
			Calendar cal = Calendar.getInstance();
			out.write(sdf.format(cal.getTime()));
		} else {
			unhandled(message);
		}
	}
}