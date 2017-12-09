package dtm;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import com.rabbitmq.jms.admin.RMQConnectionFactory;
import jms.AllVideosMDB;
import jms.NonReplyException;
import tm.RotondAndesMaster;
import vos.ListaVideos;

public class RotondAndesDistributed extends BaseDTM{
	private static RotondAndesDistributed instance;
	private AllVideosMDB allVideosMQ;

	private RotondAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		allVideosMQ = new AllVideosMDB(factory, ctx);
		addMBD(allVideosMQ);
		start();
	}
	
	public static RotondAndesDistributed getInstance(RotondAndesMaster tm){
		if(instance == null)
		{
			try {
				instance = new RotondAndesDistributed();
			} catch (NamingException|JMSException e) {
				e.printStackTrace();
			}
		}
		instance.setUpTransactionManager(tm);
		return instance;
	}
	
	public static RotondAndesDistributed getInstance(){
		if(instance == null)
		{
			RotondAndesMaster tm = new RotondAndesMaster(path);
			return getInstance(tm);
		}
		if(instance.tm != null)
		{
			return instance;
		}
		RotondAndesMaster tm = new RotondAndesMaster(path);
		return getInstance(tm);
	}
	
	public ListaVideos getLocalVideos() throws Exception
	{
		return tm.darVideosLocal();
	}
	
	public ListaVideos getRemoteVideos() throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return allVideosMQ.getRemoteVideos();
	}
}
