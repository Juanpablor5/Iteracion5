package dtm;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;

import em.Check;
import jms.AllVideosMDB;
import jms.NonReplyException;
import tm.RotondAndesTM;
import vos.Filtro;
import vos.ListaProductosI;
import vos.Producto;

public class VideoAndesDistributed 
{
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";
	
	private static VideoAndesDistributed instance;
	
	private RotondAndesTM tm;
	
	private TopicConnectionFactory factory;
	
	private AllVideosMDB allVideosMQ;
	
	private static String path;


	private VideoAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		allVideosMQ = new AllVideosMDB(factory, ctx);
		
		allVideosMQ.start();
		
	}
	
	public void stop() throws JMSException
	{
		allVideosMQ.close();
	}
	
	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	public static void setPath(String p) {
		path = p;
	}
	
	public void setUpTransactionManager(RotondAndesTM tm)
	{
	   this.tm = tm;
	}
	
	private static VideoAndesDistributed getInst()
	{
		return instance;
	}
	
	public static VideoAndesDistributed getInstance(RotondAndesTM tm)
	{
		if(instance == null)
		{
			try {
				instance = new VideoAndesDistributed();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		instance.setUpTransactionManager(tm);
		return instance;
	}
	
	public static VideoAndesDistributed getInstance()
	{
		if(instance == null)
		{
			RotondAndesTM tm = new RotondAndesTM(path);
			return getInstance(tm);
		}
		if(instance.tm != null)
		{
			return instance;
		}
		RotondAndesTM tm = new RotondAndesTM(path);
		return getInstance(tm);
	}
	
	public ListaProductosI getLocalProductos(Filtro[] filtros, Check[] checks) throws Exception
	{
		return tm.darProductosLocal(filtros, checks);
	}
	
	public ListaProductosI getRemoteProductos() throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return allVideosMQ.getRemoteVideos();
	}
}
