package service;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public interface EmailService extends Service
{
	public void sendMail(String recipientName, String recipientAddress,	String subject, String messageBody);

   
}