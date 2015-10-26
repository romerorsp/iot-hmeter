package br.com.pontoclass.iot.hmeter.strategy;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import br.com.pontoclass.iot.hmeter.sketch.Ultrassonic;

public enum Strategy {
	Calibrate() {

		private static final int PRECISION = 3;
		
		@Override
		public float execute(Ultrassonic ultrassonic) {
			try {
				ultrassonic.getWebSocket().sendMessage("HW:BeginCalibrate");
				Float result = IntStream.range(1, PRECISION)
										.mapToObj(i -> ultrassonic.average())
										.reduce((a, b) -> a+b+0f)
										.map(f -> f/PRECISION)
										.orElse(-1f);
				ultrassonic.getWebSocket().sendMessage(String.format("HW:%s:%.2f", getName(), result));
				return result;
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			} finally {
				ultrassonic.setStrategy(KeepAlive);
			}
		}
		
	},
	KeepAlive() {

		@Override
		public float execute(Ultrassonic ultrassonic) {
			try {
				Float result = ultrassonic.average();
				ultrassonic.getWebSocket().sendMessage(String.format("HW:%s:%.2f", getName(), result));
				return result;
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	},
	Available() {

		private Logger LOGGER = Logger.getLogger(this.getClass().getName());
		
		@Override
		public float execute(Ultrassonic ultrassonic) {
			
			LOGGER.info("No action set up...");
			try {
				ultrassonic.getWebSocket().sendMessage("HW:HWAvailable");
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				LOGGER.warning(e.getMessage());
			}
			return -1;
		}
	};
	
	public String getName() {
		return this.toString();
	}
	
	public abstract float execute(Ultrassonic ultrassonic);

	public static Strategy byName(String message) {
		return Optional.ofNullable(Strategy.valueOf(message))
					   .orElse(KeepAlive);
	}
}
