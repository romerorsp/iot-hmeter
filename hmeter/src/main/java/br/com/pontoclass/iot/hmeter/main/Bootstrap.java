package br.com.pontoclass.iot.hmeter.main;

import java.io.Closeable;
import java.io.IOException;

import br.com.pontoclass.iot.hmeter.sketch.Ultrassonic;

public class Bootstrap implements Closeable {

	private Ultrassonic ultrassonic;
	private static volatile Object token = new Object();

	public static void main(String[] args) throws InterruptedException, IOException {
		try (Bootstrap instance = new Bootstrap()){
			synchronized(token) {
				instance.start();
				token.wait();
			}
		}
	}

	public Bootstrap() {
		ultrassonic = new Ultrassonic();
	}
	
	private void start() {
		ultrassonic.runArduinoProcess();
	}

	@Override
	public void close() throws IOException {
		ultrassonic.stopArduinoProcess();
	}
}