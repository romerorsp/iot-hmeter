package br.com.pontoclass.iot.websocket;

import java.util.List;
import java.util.Optional;

import javax.websocket.Session;

public interface Action {

	void handle(String[] protocol, Optional<Session> hWSession, List<Session> webSessions);

}