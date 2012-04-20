package com.jros;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EmailDaylogServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
    	sendEmail(req.getParameter("address"), req.getParameter("content"));
    }
	
	public static void sendEmail(String address, String content){
		//User user = UserServiceFactory.getUserService().getCurrentUser();
		String recipientAddress = address;
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		String messageBody = content;

		String messageSubject = 
			"A reminder from tickr... " +
			content;
		
		if (messageSubject.length() > 25){
			messageSubject = messageSubject.substring(0, 25);
		}
		
		try{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress ("jowensbysandifer@gmail.com", "The Ticklr Team"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientAddress));
			message.setSubject(messageSubject);
			message.setText(messageBody);
			Transport.send(message);
		}
		catch(AddressException e){
			e.printStackTrace();
		}
		catch(MessagingException e){
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
