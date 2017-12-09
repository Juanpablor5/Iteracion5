package dtm;

import java.util.LinkedList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.rabbitmq.jms.admin.RMQConnectionFactory;
import jms.MBD;
import tm.RotondAndesMaster;

public class BaseDTM {
	protected final static String MQ_CONNECTION_NAME = "java:global/RMQClient";
	
	protected RotondAndesMaster tm;
	
	protected TopicConnectionFactory factory;
	
	protected static String path;
	
	private List<MBD> mbds=new LinkedList<>();


	protected BaseDTM() throws NamingException, JMSException{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
	}
	
	public void stop() throws JMSException{
		for(MBD mbd:mbds) {
			mbd.close();
		}
	}
	
	public void start() throws JMSException{
		for(MBD mbd:mbds) {
			mbd.start();
		}
	}
	
	public void addMBD(MBD mbd) {
		mbds.add(mbd);
	}
	
	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	public static void setPath(String p) {
		path = p;
	}
	
	public void setUpTransactionManager(RotondAndesMaster tm)
	{
	   this.tm = tm;
	}
}
