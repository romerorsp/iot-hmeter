package br.com.pontoclass.iot.websocket;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javax.websocket.Session;

public class HWLostCommand implements Command {

	@Override
	public void execute(String[] protocol, Optional<Session> hWSession, List<Session> webSessions) {
		webSessions.stream()
		   		   .forEach(session -> {
					   try {
						   session.getBasicRemote().sendText("HWLost");
					   } catch (Exception e) {
							Logger.getLogger(this.getClass().getName())
								  .warning(String.format("Something went wrong by trying to answer session [%s]: [%s]",
										  				 session.getId(), e.getMessage()));
					   }
		   		   });
	}
}