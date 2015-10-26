package br.com.pontoclass.iot.hmeter.websocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import br.com.pontoclass.iot.hmeter.sketch.Ultrassonic;
import br.com.pontoclass.iot.hmeter.strategy.Strategy;

@ClientEndpoint
public class WebSocket {

	private URI uri;
	private Session session;
	private Ultrassonic ultrassonic;
	
	public WebSocket(Ultrassonic ultrassonic, String uri) {
		this.ultrassonic = ultrassonic;
		try {
			this.uri = new URI(uri);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public void connect() {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		try {
			this.session = container.connectToServer(this, getURI());
			this.session.getBasicRemote().sendText("CLIENT:HW");
		} catch (DeploymentException | IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public URI getURI() {
		return uri;
	}

	public void setUrl(URI uri) {
		this.uri = uri;
	}
	 
    public @OnOpen void onOpen(Session session) {
        this.session = session;
    }
    
    public @OnMessage void onMessage(String message) {
    	ultrassonic.setStrategy(Strategy.byName(message));
    }
    
    public void sendMessage(String value) throws IOException {
    	session.getBasicRemote().sendText(value);
    }
}
