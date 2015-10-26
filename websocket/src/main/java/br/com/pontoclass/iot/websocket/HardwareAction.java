package br.com.pontoclass.iot.websocket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import javax.websocket.Session;

public class HardwareAction implements Action {

	private static final Logger LOGGER = Logger.getLogger(HardwareAction.class.getName());
	private static Map<String, Command> commands =  new HashMap<>();
	
	static {
		commands.put("Calibrate", new HWCalibrateCommand());
		commands.put("BeginCalibrate", new HWBeginCalibrateCommand());
		commands.put("KeepAlive", new HWKeepAliveCommand());
		commands.put("HWLost", new HWLostCommand());
		commands.put("HWAvailable", new HWAvailableCommand());
	}
	
	@Override
	public void handle(String[] protocol, Optional<Session> hWSession, List<Session> webSession) {
		Command command = commands.get(protocol[1]);
		if(command == null) {
			LOGGER.warning(String.format("Um comando desconhecido foi solicitado pelo Hardware: [%s]", protocol[1]));
		} else {
			command.execute(protocol, hWSession, webSession);
		}
	}
}