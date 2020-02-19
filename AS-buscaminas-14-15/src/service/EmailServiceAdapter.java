package service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailServiceAdapter implements EmailService{
	
	private static final String HOST = "smtp.gmail.com";
	private static final String PORT = "587";//465
	private static final String USERNAME = "buscaminas13as@gmail.com";
	private static final String PASSWORD = "buscaminaspass";
	
	private static final String FROM_DISPLAY_NAME = "Buscamines AS 13";
	
	
	public void sendMail(String recipientName, String recipientAddress,	String subject, String messageBody) {
		
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", HOST);
	    props.put("mail.smtp.port", PORT);
	    Session session = Session.getInstance(props,
	      new javax.mail.Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(USERNAME, PASSWORD);
	        }
	      });

	    try {
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(USERNAME));
	        message.setRecipients(Message.RecipientType.TO,
	            InternetAddress.parse(recipientAddress));
	        message.setSubject(subject);
	        message.setText(messageBody); 
	        Transport.send(message);
	        System.out.println("Done");
	    } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }
	}

	public static void main(String [] args)
	   {    
		   
		   	final String username = "buscaminas13as@gmail.com";
		    final String password = "buscaminaspass";
		    Properties props = new Properties();
		    props.put("mail.smtp.auth", "true");
		    props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.smtp.host", "smtp.gmail.com");
		    props.put("mail.smtp.port", "587");
		    Session session = Session.getInstance(props,
		      new javax.mail.Authenticator() {
		        protected PasswordAuthentication getPasswordAuthentication() {
		            return new PasswordAuthentication(username, password);
		        }
		      });

		    try {
		        Message message = new MimeMessage(session);
		        message.setFrom(new InternetAddress("buscaminas13as@gmail.com"));
		        message.setRecipients(Message.RecipientType.TO,
		            InternetAddress.parse("alexmorral@gmail.com"));
		        message.setSubject("Testing Subject");
		        message.setText("Dear Mail Crawler,"+
		 "\n\n No spam to my email,please!"); 
		        Transport.send(message);
		        System.out.println("Done");
		    } catch (MessagingException e) {
		        throw new RuntimeException(e);
		    }
	   }
}
