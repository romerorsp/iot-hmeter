package br.com.pontoclass.iot.websocket;

import java.util.List;
import java.util.Optional;
import javax.websocket.Session;

public interface Command {

	public void execute(String[] protocol, Optional<Session> hWSession, List<Session> webSessions);
}
