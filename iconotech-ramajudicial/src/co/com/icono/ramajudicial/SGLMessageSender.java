package co.com.icono.ramajudicial;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class SGLMessageSender {
	private ConnectionFactory factory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination destination = null;
	private MessageProducer producer = null;


	public void sendMessage(SGLMessage message) throws JMSException {
		String url = "failover://tcp://localhost:61616";
		if (RamaJudicialClient.TEST) {
			url = "failover://tcp://52.5.168.98:61616";
		}

		factory = new ActiveMQConnectionFactory(url);
		connection = factory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue("SGLNOTIFICACIONES");
		producer = session.createProducer(destination);
		ObjectMessage objmessage = session.createObjectMessage(message);
		producer.send(objmessage);
		connection.close();
	}

}
