package br.com.pontoclass.iot.hmeter.sketch;

import java.util.logging.Logger;
import java.util.stream.IntStream;
import org.sintef.jarduino.DigitalPin;
import org.sintef.jarduino.PinMode;
import br.com.pontoclass.iot.hmeter.strategy.Strategy;
import br.com.pontoclass.iot.hmeter.websocket.WebSocket;

public class Ultrassonic extends AbstractArduinoSketch {
	
	private static final int AVERAGE_PRECISION = 10;
	private static final Logger LOGGER = Logger.getLogger(Ultrassonic.class.getName());
	private Strategy strategy = Strategy.Available;
	private WebSocket socket = new WebSocket(this, "ws://localhost:8080/websocket/hmeter");
	private float lastResult = -1;
	
	@Override protected void loop() {
		synchronized(strategy) {
			float result = strategy.execute(this);
			if(lastResult != result) {
				LOGGER.info(String.format("Último resultado (diferente) obtido do medidor: [%f].", result));
				lastResult = result;
			}
		}
	}

	@Override protected void setup() {
		LOGGER.info("Iniciando o medidor Ultrassônico...");
		socket.connect();
		pinMode(DigitalPin.PIN_4, PinMode.OUTPUT);
		pinMode(DigitalPin.PIN_5, PinMode.INPUT);
	}

	public float average() {
		return IntStream.range(1, AVERAGE_PRECISION)
						.mapToObj(i -> ultrassonic(DigitalPin.PIN_4, DigitalPin.PIN_5)/29f/2f/100f)
						.reduce((a, b) -> a+b)
						.orElse(0f)/AVERAGE_PRECISION;
	}
	
	public void setStrategy(Strategy strategy) {
		synchronized(strategy) {
			this.strategy = strategy;
		}
	}

	public WebSocket getWebSocket() {
		return this.socket;
	}
}