package br.com.pontoclass.iot.websocket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import javax.websocket.Session;

public class WebAction implements Action {

	private static final Logger LOGGER = Logger.getLogger(WebAction.class.getName());
	private static Map<String, Command> commands =  new HashMap<>();
	
	static {
		commands.put("Calibrate", new WebCalibrateCommand());
	}
	
	@Override
	public void handle(String[] protocol, Optional<Session> hWSession, List<Session> webSession) {
		Command command = commands.get(protocol[1]);
		if(command == null) {
			LOGGER.warning(String.format("Unknown command was asked by the web client: [%s]", protocol[1]));
		} else {
			command.execute(protocol, hWSession, webSession);
		}
	}
}
