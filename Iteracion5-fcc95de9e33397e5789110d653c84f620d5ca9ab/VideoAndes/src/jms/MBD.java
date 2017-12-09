package jms;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageListener;

public interface MBD extends MessageListener, ExceptionListener {
	final static int TIME_OUT = 10;
	final static String APP = "app2";
	
	final static String REQUEST = "REQUEST";
	final static String REQUEST_ANSWER = "REQUEST_ANSWER";
	
	public void start() throws JMSException;
	
	public void close() throws JMSException;

}
