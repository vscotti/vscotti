package com.abc.ceop.phoneapprover.service.impl;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.model.entities.Configuration.FirstProcessConfiguration;
import com.abc.ceop.phoneapprover.service.EmailSenderService;
import com.abc.ceop.phoneapprover.service.FirstProcessConfigurationService;
import com.google.common.base.Preconditions;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
	
	private final Logger logger = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

	private static final String PROTOCOL = "smtp";
	
	private final FirstProcessConfigurationService service;
	
	@Autowired
	public EmailSenderServiceImpl(FirstProcessConfigurationService service) {
		this.service = Preconditions.checkNotNull(service);
	}
	
	@Override
	public void postMail(String[] recipients, String subject, String message, String attachment) throws MessagingException {
		Transport transport = null;
		try {

			if(recipients != null &&
					recipients.length > 0) {
		    	String user = service.getConfigurationValue(FirstProcessConfiguration.EMAIL_USER);
		        String pass = service.getConfigurationValue(FirstProcessConfiguration.EMAIL_PASSWORD);
				// create some properties and get the default Session
			    Session session = Session.getDefaultInstance(setHostProperties(PROTOCOL), null);
			    session.setDebug(false);
			    Message msg = createMessage(session, recipients, subject, message, attachment);
			    transport = session.getTransport(PROTOCOL);
		        transport.connect(user, pass);
		        transport.sendMessage(msg, msg.getAllRecipients());
		        logger.info("Email enviado exitosamente.");
			} else {
				logger.info("No hay email asociados a esta campaña. Por favor agregelos a travez de la tabla EmailContacts.");
			}
	    } finally {
	    	if (transport != null) {
	    		transport.close();
	    	}
	    }
	    
	}
	
	@Override
	public void postSingleMail(String email, String subject, String message, String attachment, String fromAddress) throws MessagingException {
		Transport transport = null;
		try {
			  	String user = service.getConfigurationValue(FirstProcessConfiguration.EMAIL_USER);
		        String pass = service.getConfigurationValue(FirstProcessConfiguration.EMAIL_PASSWORD);
				// create some properties and get the default Session
			    Session session = Session.getDefaultInstance(setHostProperties(PROTOCOL), null);
			    session.setDebug(false);
			    Message msg = createMessageToSingleRecipient(session, email, subject, message, attachment, fromAddress);
			    transport = session.getTransport(PROTOCOL);
		        transport.connect(user, pass);
		        transport.sendMessage(msg, msg.getAllRecipients());
		        logger.info("Email enviado exitosamente de encuesta web enviado exitosamente.");
			
	    } finally {
	    	if (transport != null) {
	    		transport.close();
	    	}
	    }
		
	}
		
		
	private Properties setHostProperties(String protocol) {
		String host = service.getConfigurationValue(FirstProcessConfiguration.SMTP_HOST);
		String port = service.getConfigurationValue(FirstProcessConfiguration.SMTP_HOST);
		String auth = service.getConfigurationValue(FirstProcessConfiguration.SMTP_AUTH);
		String tls = service.getConfigurationValue(FirstProcessConfiguration.SMTP_TLS);
		// Set the host smtp address
		Properties props = new Properties();
		props.setProperty("mail." + protocol + ".host", host);
		props.setProperty("mail." + protocol + ".starttls.enable", tls);
		props.setProperty("mail." + protocol + ".port", port);
		props.setProperty("mail." + protocol + ".auth", auth);
		return props;
	}
	
	private Message createMessage(Session session, String[] recipients, String subject, String message, String attachment) {
		Message msg = null;
		try {
		    String addressToStr = "";
		    InternetAddress[] addressTo = new InternetAddress[recipients.length];
		    for (int i = 0; i < recipients.length; i++) {
				addressTo[i] = new InternetAddress(recipients[i].trim());
				addressToStr = addressToStr + recipients[i].trim() + ",";
			}
		    logger.info("Destinatarios: " + recipients.length + "| " + addressToStr);
		    msg = new MimeMessage(session);
		    
		    msg.setRecipients(Message.RecipientType.TO, addressTo);
		    buildMessage(subject, message, attachment, msg);
		} catch (MessagingException exception) {
			logger.error("Huvo un error tratando de crear el mensaje del email. Causa: {} / {}", exception.getClass().toString(), exception.getMessage());
		}
	    return msg;
	}

	//TODO: Agregar String fromAddress como parametro del metodo
	private Message createMessageToSingleRecipient (Session session, String recipient, String subject, String message, String attachment, String fromAddress) {
		
		Message msg = null;
		String addressToStr = "";
		
		try {
			msg = new MimeMessage(session);
			InternetAddress addressTo = new InternetAddress(recipient);
			msg.setRecipient(Message.RecipientType.TO, addressTo);

			//
			InternetAddress fromInternetAddress = new InternetAddress(fromAddress);
			msg.setFrom(fromInternetAddress); 
			//
			
			logger.info("Destinatario: " + recipient + "| " + addressToStr);
			buildMessage(subject, message, attachment, msg);
		} catch (MessagingException exception) {
			logger.error("Huvo un error tratando de crear el mensaje del email. Causa: {} / {}", exception.getClass().toString(), exception.getMessage());
		}
		return msg;
	}
	
	private void buildMessage(String subject, String message,
			String attachment, Message msg) throws MessagingException {
		// Set the Email header, Subject and Content Type 
		msg.addHeader("Alert", "Alert");
		msg.setSubject(subject);
		// create and fill the first message part
		MimeBodyPart mbp1 = new MimeBodyPart();
		mbp1.setContent(message, "text/html");
		// create the Multipart and add its parts to it
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(mbp1);
		if(attachment != null) {
		    // create the second message part
		    MimeBodyPart mbp2 = new MimeBodyPart();
		    // attach the file to the message
		    FileDataSource fds = new FileDataSource(attachment);
		    mbp2.setDataHandler(new DataHandler(fds));
		    mbp2.setFileName(fds.getName());
			mp.addBodyPart(mbp2);
		}
		// add the Multipart to the message
		msg.setContent(mp);
	}
	

	
}
