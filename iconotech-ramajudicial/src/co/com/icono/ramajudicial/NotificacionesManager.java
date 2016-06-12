package co.com.icono.ramajudicial;

import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import javax.jms.JMSException;

public class NotificacionesManager {

	// NUEVO PROCESO: ID_PROCESO
	public static final String NOTIFICACION_00 = "NOTIFICACION-00";
	// NUEVA ACTUACIÓN DE UN PROCESO: ID_PROCESO, ID_ACTUACIÓN
	public static final String NOTIFICACION_01 = "NOTIFICACION-01";
	// CAMBIO DE UBICACIÓN DE DESPACHO DEL PROCESO: ID_ENTIDAD, ID_CIUDAD
	public static final String NOTIFICACION_02 = "NOTIFICACION-02";
	// NUEVA CIUDAD: ID_CIUDAD
	public static final String NOTIFICACION_03 = "NOTIFICACION-03";
	// NUEVA ENTIDAD: ID_CIUDAD, ID_ENTIDAD
	public static final String NOTIFICACION_04 = "NOTIFICACION-04";
	// NUEVO RECURSO ASOCIADO A UN PROCESO: ID_PROCESO
	public static final String NOTIFICACION_05 = "NOTIFICACION-05";

	

	public static void main(String[] args) throws Exception {
		String message = null;
		if (RamaJudicialClient.TEST) {
			message = "[2016/02/09 22:15:59] [NOTIFICACION-01] 75531,1207884";
		} else {
			message = args[0];
		}
		sendNotification(message);
	}
	
	public static void sendNotification(String message) throws JMSException, Exception{
		sendNotification(buildSGLMessage(message));
	}

	private static SGLMessage buildSGLMessage(String message) throws Exception {
		SGLMessage sglmessage = new SGLMessage();
		StringTokenizer st = new StringTokenizer(message, "[] ");
		SimpleDateFormat dateFormatTmp = new SimpleDateFormat("yyy/MM/dd hh:mm:ss");
		String dt = st.nextElement().toString() + " " + st.nextElement().toString();
		java.util.Date d = dateFormatTmp.parse(dt);
		String id = st.nextElement().toString();
		sglmessage.setFecha(d);
		String params = st.nextElement().toString();
		StringTokenizer stp = new StringTokenizer(params, ",");
		switch (id) {
		case NOTIFICACION_00:
			sglmessage.setId(0);
			sglmessage.setIdradicado(Long.parseLong(params));
			sglmessage.setMessage("Se ha registrado un nuevo proceso");
			break;
		case NOTIFICACION_01:
			sglmessage.setId(1);
			sglmessage.setIdradicado(Long.parseLong(stp.nextElement().toString()));
			sglmessage.setIdactuacion(Long.parseLong(stp.nextElement().toString()));
			sglmessage.setMessage("Se ha registrado una nueva actuación");
			break;
		case NOTIFICACION_02:
			sglmessage.setId(2);
			sglmessage.setEntidad(Long.parseLong(stp.nextElement().toString()));
			sglmessage.setCiudad(Long.parseLong(stp.nextElement().toString()));
			sglmessage.setMessage("Un proceso ha cambiado de despacho");
			break;
		case NOTIFICACION_03:
			sglmessage.setId(3);
			sglmessage.setCiudad(Long.parseLong(params));
			sglmessage.setMessage("Se ha registrado una nueva ciudad");
			break;
		case NOTIFICACION_04:
			sglmessage.setId(4);
			sglmessage.setEntidad(Long.parseLong(stp.nextElement().toString()));
			sglmessage.setCiudad(Long.parseLong(stp.nextElement().toString()));
			sglmessage.setMessage("Se ha registrado una nueva entidad");
			break;
		case NOTIFICACION_05:
			sglmessage.setId(5);
			sglmessage.setIdradicado(Long.parseLong(params));
			sglmessage.setMessage("Se ha registrado un nuevo recurso a un proceso");
			break;
		default:
			throw new Exception("notificación no reconocida: "+message);
		}
		return sglmessage;
	}

	public static void sendNotification(SGLMessage message) throws JMSException {
		SGLMessageSender sender = new SGLMessageSender();
        sender.sendMessage(message);
        
	}

}
