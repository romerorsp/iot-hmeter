package br.com.pontoclass.iot.hmeter.sketch;

import gnu.io.CommPortIdentifier;
import java.util.Enumeration;
import org.sintef.jarduino.JArduino;

public abstract class AbstractArduinoSketch extends JArduino {

    private static final String[] PORT_NAMES;
	private static String	TRIED_PORT_NAME;

    static {
    	final String OS = System.getProperty("os.name").toLowerCase();
    	if(OS.contains("win")) {
    		PORT_NAMES = new String[] {"COM"};
    	} else if(OS.contains("mac")) {
    		PORT_NAMES = new String[] {"/dev/tty.usbmodem"};
    	} else if(OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
    		PORT_NAMES = new String[] {"/dev/usbdev", "/dev/tty", "/dev/serial"};
    	} else if(OS.contains("sunos")) {
    		PORT_NAMES = new String[] {"/dev/tty"};
    	} else {
    		PORT_NAMES = new String[] {"/dev/tty"};
    	}
        @SuppressWarnings("unchecked") Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        System.out.println("Trying:");
        boolean set = false;
		outer: while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            System.out.println(String.format("\tPort [%s]", currPortId.getName()));
            for (String portName: PORT_NAMES) {
                if (currPortId.getName().startsWith(portName)) {
                	TRIED_PORT_NAME = currPortId.getName();
                	set = true;
                    break outer;
                }
            }
        }
        if(!set) {
        	TRIED_PORT_NAME = "/dev/tty";
        }
    }
	
	public AbstractArduinoSketch() {
        super(TRIED_PORT_NAME);
	}
}