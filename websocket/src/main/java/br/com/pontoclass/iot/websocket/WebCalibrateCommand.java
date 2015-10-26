package br.com.pontoclass.iot.websocket;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.websocket.Session;

public class WebCalibrateCommand implements Command {

	@Override
	public void execute(String[] protocol, Optional<Session> hWSession, List<Session> webSession) {
		hWSession.ifPresent(session -> {
			try {
				session.getBasicRemote().sendText("Calibrate");
			} catch (Exception e) {
				Logger.getLogger(this.getClass().getName()).warning("Something went wrong by trying to send the Calibrate message to the Hardware...");
			}
		});
	}
}