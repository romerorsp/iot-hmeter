package br.com.pontoclass.iot.websocket;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.websocket.Session;

public class NoAction implements Action {

	private static final Logger LOGGER = Logger.getLogger(NoAction.class.getName());
	
	@Override
	public void handle(String[] protocol, Optional<Session> hWSession, List<Session> webSession) {
		LOGGER.warning(String.format("There should be an action for [%s]", protocol[0]));
	}
}
