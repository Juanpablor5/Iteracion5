package dtm;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import com.rabbitmq.jms.admin.RMQConnectionFactory;

import jms.AllProductosMDB;
import jms.AllRestaurantesMDB;
import jms.NonReplyException;
import jms.RentabilidadMDB;
import tm.RotondAndesMaster;
import vos.ListaProductoI;
import vos.RentabilidadList;

public class RotondAndesDistributed extends BaseDTM{
	private static RotondAndesDistributed instance;
	private AllProductosMDB allProductosMQ;
	private AllRestaurantesMDB allRestaurantesMQ;
	private RentabilidadMDB allRentabilidadMQ;

	private RotondAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		allProductosMQ = new AllProductosMDB(factory, ctx);
		allRestaurantesMQ = new AllRestaurantesMDB(factory, ctx);
		allRentabilidadMQ = new RentabilidadMDB(factory, ctx);
		addMBD(allProductosMQ);
		addMBD(allRestaurantesMQ);
		addMBD(allRentabilidadMQ);
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
	
	public ListaProductoI getLocalProductos(String string) throws Exception
	{
		return tm.darProductosLocal(string);
	}
	
	public ListaProductoI getRemoteProductos(String string) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return allProductosMQ.getRemoteProductos(string);
	}

	public void deleteLocalRestaurante(long parseLong) throws Exception {
		tm.deleteLocalRestaurante(parseLong);
	}

	public void deleteRemoteRestaurante(long idRestaurante) throws JsonGenerationException, JsonMappingException, NoSuchAlgorithmException, JMSException, IOException, NonReplyException, InterruptedException{
		// TODO Auto-generated method stub
		allRestaurantesMQ.getRemoteRestaurantes(idRestaurante);
	}

	public RentabilidadList getRentabilidadLocal(String payload) throws Exception {
		// TODO Auto-generated method stub
		return tm.getLocalRentabilidad(payload);
	}

	public RentabilidadList getRemoteRentabilidad(String fecha) throws JsonGenerationException, JsonMappingException, NoSuchAlgorithmException, JMSException, IOException, NonReplyException, InterruptedException {
		// TODO Auto-generated method stub
		return allRentabilidadMQ.getRentabilidadList(fecha);
	}
}
