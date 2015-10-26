package br.com.pontoclass.iot.websocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

public @ServerEndpoint("/hmeter") class HMeterServer {
	private static final Logger LOGGER = Logger.getLogger(HMeterServer.class.getName());
	private static Optional<Session> hWSession = Optional.empty();
	private static List<Session> webSessions = Collections.synchronizedList(new ArrayList<>());

	public @OnClose void onClose(Session session) {
		LOGGER.log(Level.INFO, "Conexão finalizada com o cliente: {0}", session.getId());
		hWSession.ifPresent(s -> {
			if(s.getId().equals(session.getId())) {
				ActionFactory.factory("HW").handle("HW:HWLost".split("\\:"), hWSession, webSessions);
			}
		});
		webSessions.remove(session);
	}

	public @OnError void onError(Throwable exception, Session session) {
		LOGGER.log(Level.INFO, "Erro de conexão com o cliente: {0}", session.getId());
		Optional.ofNullable(session)
				.map(Session::isOpen)
				.filter(Boolean.FALSE::equals)
				.ifPresent(webSessions::remove);
	}
	
	public @OnMessage void onMessage(String message, Session session) {
		LOGGER.log(Level.INFO, "Mensagem recebida do cliente [{0}]: {1}", new Object[]{session.getId(), message});
		String[] protocol = message.split("\\:");
		if("CLIENT".equalsIgnoreCase(protocol[0])) {
			if("HW".equalsIgnoreCase(protocol[1])) {
				hWSession = Optional.of(session);
			} else if("WEB".equalsIgnoreCase(protocol[1])) {
				webSessions.add(session);
			}
		} else {
			Action action = ActionFactory.factory(protocol[0]);
			LOGGER.log(Level.INFO, "Nova mensagem recebida do cliente [{0}]: {1}", new Object[] {session.getId(), message});
			action.handle(protocol, hWSession, webSessions);
		}
	}
}