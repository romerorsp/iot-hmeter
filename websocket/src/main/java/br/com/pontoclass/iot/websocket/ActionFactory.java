package br.com.pontoclass.iot.websocket;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

	private static final Map<String, Action> actions = new HashMap<>();
	private static final Action DEFAULT_ACTION = new NoAction();
	
	static {
		actions.put("HW", new HardwareAction());
		actions.put("WEB", new WebAction());
	}
	
	public static Action factory(String actionName) {
		return actions.getOrDefault(actionName, DEFAULT_ACTION);
	}
}